/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.ActorLocators.DelegationActorLocator;
import HPalang.Core.ActorLocators.DirectActorLocator;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.ActorLocators.SelfActorLocator;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.*;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.MessageLocators.DelegationMessageLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Guard;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.SplitPane;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelCreationUtilities
{
    public static MessageHandler AddMessageHandler(ActorType type, String handlerName, Message.MessageType messageType )
    {
        type.AddMessageHandler(handlerName, new MessageHandler(messageType));
        
        return type.FindMessageHandler(handlerName);
    }
    
    public static MessageHandler AddControlMessageHandler(ActorType type, String handlerName)
    {
        return AddMessageHandler(type, handlerName, Message.MessageType.Control);
    }
    
    public static MessageHandler AddDataMessageHandler(ActorType type, String handlerName)
    {
        return AddMessageHandler(type, handlerName, Message.MessageType.Data);
    }
    
    public static void AddPort(ActorType actorType, String portName, Variable targetVariable)
    {
        MessageHandler port = new MessageHandler(Message.MessageType.Data);
        Variable localVariable =CreateVariable(targetVariable.Type(),"local_" + portName); 
        port.Parameters().Add(new VariableParameter(localVariable));
        port.AddStatement(new AssignmentStatement(targetVariable, new VariableExpression(localVariable)));
        
        actorType.AddMessageHandler(portName,port);
    }
    
    public static void AddPort(ActorType actorType, String portName, String targetVariableName)
    {
        AddPort(actorType, portName, actorType.FindVariable(targetVariableName));
    }
    
    public static void AddVariable(ActorType actorType, Variable variale)
    {
        actorType.AddVariable(variale);
    }
    
    public static void AddIntegerVariable(ActorType actorType, String name)
    {
        AddVariable(actorType, new IntegerVariable(name));
    }
    
    public static void AddFloatVariable(ActorType actorType, String name)
    {
        AddVariable(actorType, new FloatVariable(name));
    }

    public static void SetNetworkPriority(ModelDefinition definition, Actor actor, String messageHandlerName, int priority)
    {
        MessageHandler handler = actor.Type().FindMessageHandler(messageHandlerName);
        definition.CANSpecification().SetNetworkPriority(actor, new NormalMessage(handler), priority);
    }
    
    public static void SetNetworkDelay(ModelDefinition definition, Actor sender, Actor reciever, String messageHandler, float delay)
    {
        Message message = new NormalMessage(reciever.Type().FindMessageHandler(messageHandler));
        definition.CANSpecification().SetNetworkDelay(sender, reciever, message , delay);
    }
    
    public static Statement CreateModeChangeStatement(Mode mode)
    {
        return new ModeChangeStatement(mode);
    }
   
    public static SendStatement CreateModeChangeSendStatement(Mode mode, ActorLocator actorLocator)
    {
        return ModelCreationUtilities.CreateSendStatement(
                actorLocator, 
                new MessageWithBody(
                        Statement.StatementsFrom(new ModeChangeStatement(mode)), Message.MessageType.Control
                )
        );
    }
    
    public static SendStatement CreateModeChangeSendStatement(String mode, InstanceParameter instance)
    {
        return ModelCreationUtilities.CreateModeChangeSendStatement(
                ((PhysicalActorType) instance.Type()).FindMode(mode), 
                new ParametricActorLocator(instance));
    }
    
    public static SendStatement CreateDeactiveModeRequest(InstanceParameter instance)
    {
        return ModelCreationUtilities.CreateModeChangeSendStatement(
                Mode.None(), 
                new ParametricActorLocator(instance));
    }

    public static void AddInstanceParameter(ActorType type, String parameterName, ActorType parameterType)
    {
        type.AddInstanceParameter(new InstanceParameter(parameterName, parameterType));
    }
    public static void BindInstance(Actor actor, String parameterName, Actor instance, CommunicationType communicationType)
    {
        InstanceParameter wheelControllerInstanceParam = actor.Type().FindInstanceParameter(parameterName);
        actor.BindInstance(wheelControllerInstanceParam, instance, CommunicationType.Wire);
    }

    public static void BindDelagation(Actor actor, String parameterName, Actor instance, String instanceHandlerName, CommunicationType communicationType)
    {
        BindDelagation(
                actor,
                parameterName,
                instance,
                instance.Type().FindMessageHandler(instanceHandlerName), 
                communicationType);
    }
    
    public static void BindDelagation(Actor actor, String parameterName, Actor instance, MessageHandler instanceHandler, CommunicationType communicationType)
    {
        DelegationParameter delgationParam = actor.Type().FindDelegationParameter(parameterName);

        Delegation delegation = new Delegation(
                instance,
                instanceHandler);

        actor.BindDelegation(delgationParam, delegation, communicationType);
    }
    
    public static <T extends Variable> void AddParameter(MessageHandler handler, String paramName, Class<T> typeClass)
    {
        try {
            Variable var=  typeClass.getConstructor(String.class).newInstance(paramName);
            handler.Parameters().Add(new VariableParameter(var));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException | NoSuchMethodException | InvocationTargetException ex) {
            throw new RuntimeException("Couldn't instantiate variable type.",ex);
        }
    }
    
    public static AssignmentStatement CreateResetFor(RealVariable var)
    {
        return new AssignmentStatement(var, new ConstantContinuousExpression(0));
    }
    
    public static SendStatement CreateSendStatement(ActorLocator actorLocator, MessageLocator messageLocator, Expression ... argumentExpressions )
    {
        MessageArguments arguments = new MessageArguments();
        int i = 0;
        
        for(VariableParameter parameter : messageLocator.Parameters().AsSet())
        {
            arguments.Add(new VariableArgument(parameter.Type(), argumentExpressions[i]));
            i++;
        }
        
        return new SendStatement(
                                actorLocator,
                                messageLocator,
                                arguments
        );
    }

    public static SendStatement CreateSendStatement(ActorLocator actorLocator, Message message, Expression ... arguments )
    {
        return CreateSendStatement(actorLocator, new DirectMessageLocator(message), arguments);
    }
            
    public static SendStatement CreateSendStatement(InstanceParameter instance, MessageHandler handler, Expression ... arguments)
    {
        return ModelCreationUtilities.CreateSendStatement(
                new ParametricActorLocator(instance), 
                new NormalMessage(handler), 
                arguments);
    }
    
    public static SendStatement CreateSendStatement(InstanceParameter instance, String handler, Expression ... arguments)
    {
        return CreateSendStatement(instance, 
                instance.Type().FindMessageHandler(handler), 
                arguments);
    }
    
    public static SendStatement CreateSendStatement(DelegationParameter delegationParameter, Expression ... arguments)
    {
        return CreateSendStatement(
                new DelegationActorLocator(delegationParameter), 
                new DelegationMessageLocator(delegationParameter), 
                arguments);
    }
    
    public static SendStatement CreateSelfSendStatement(ActorType type, String message, Expression ... arguments )
    {
        return CreateSendStatement(new SelfActorLocator(), MessageForm(type.FindMessageHandler(message)), arguments);
    }
    
    public static SendStatement CreateDirectSendStatement(Actor actor, String message, Expression ... arguments )
    {
        return CreateSendStatement(
                new DirectActorLocator(actor),
                MessageForm(actor.Type().FindMessageHandler(message)),
                arguments);
    }
    
    public static Message MessageForm(MessageHandler handler)
    {
        return new NormalMessage(handler);
    }
    
    public static VariableExpression VariableExpression(Variable variable)
    {
        return new VariableExpression(variable);
    }
    
    public static Variable CreateVariable(Variable.Type type, String name)
    {
        switch(type)
        {
            case floatingPoint:
                return new FloatVariable(name);
            case integer:
                return new IntegerVariable(name);
            case real:
                return new RealVariable(name);
        }
        
        throw new RuntimeException("Unknow variable type.");
        
    }
    
    public static PhysicalActorType CreatePeriodicClockType()
    {
        PhysicalActorType clockType = new PhysicalActorType("Clock");
        DelegationParameter callback = new DelegationParameter("callback");
        clockType.AddDelegationParameter(callback);
        
        RealVariable timer = new RealVariable("timer");
        
        clockType.AddVariable(timer);
        
        Mode runningMode = new Mode("Running");
        
        runningMode.SetGuard(CreateGuard(timer, "==", 0.01f));
        runningMode.SetInvarient(CreateInvarient(timer, "<=", Const(0.01f)));
                
        runningMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        
        runningMode.AddAction(CreateResetFor(timer));
        runningMode.AddAction(ModelCreationUtilities.CreateSendStatement(callback));
        
        
        clockType.AddMode(runningMode);
        clockType.SetInitialMode(runningMode);
        
        return clockType;
    }
    
    
    public static PhysicalActor CreatePreriodicClock(SoftwareActor actor, String messageHandlerName, PhysicalActorType clockType)
    {
        PhysicalActor clock = new PhysicalActor("clock", clockType, 10);
        BindDelagation(clock, "callback", actor, messageHandlerName, CommunicationType.Wire);
        
        return clock;
    }
    
    public static BinaryExpression CreateBinaryExpression(Expression e1, String operator , Expression e2)
    {
        if (operator.equals("<"))
            return new BinaryExpression(e1, new LesserOperator(), e2);
        else if (operator.equals("<="))
            return new BinaryExpression(e1, new LesserEqualOperator(), e2);
        else if (operator.equals("=="))
            return new BinaryExpression(e1, new EqualityOperator(), e2);
        else if (operator.equals(">="))
            return new BinaryExpression(e1, new GreaterEqualOperator(), e2);
        else if (operator.equals(">"))
            return new BinaryExpression(e1, new GreaterOperator(), e2);
        else if (operator.equals("+"))
            return new BinaryExpression(e1, new AddOperator(), e2);
        else if (operator.equals("-"))
            return new BinaryExpression(e1, new SubtractOperator(), e2);
        else if (operator.equals("/"))
            return new BinaryExpression(e1, new DivisionOperator(), e2);
        else if (operator.equals("*"))
            return new BinaryExpression(e1, new MultiplyOperator(), e2);
        else if (operator.equals("&&"))
            return new BinaryExpression(e1, new LogicalAndOperator(), e2);
        else if (operator.equals("||"))
            return new BinaryExpression(e1, new LogicalOrOperator(), e2);
        else 
            throw new RuntimeException("Operator is not supported yet");
    }

    public static BinaryExpression CreateBinaryExpression(Variable var, String operator , Expression e2)
    {
        return CreateBinaryExpression(new VariableExpression(var), operator, e2);
    }
    public static BinaryExpression CreateEqualityExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new EqualityOperator(), e2);
    }
        
    public static BinaryExpression CreateGreaterExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new GreaterOperator(), e2);
    }
    
    public static BinaryExpression CreateGreaterEqualExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new GreaterEqualOperator(), e2);
    }
    
        
    public static BinaryExpression CreateAddExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new AddOperator(), e2);
    }
            
    public static Expression CreateSubtractExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new SubtractOperator(), e2);
    }
                
    public static Expression CreateLesserEqualExpression(Expression e1, Expression e2)
    {
        return new BinaryExpression(e1, new LesserEqualOperator(), e2);
    }
    
    public static Guard CreateGuard(Variable var, String operator, float value)
    {
        return CreateGuard(new VariableExpression(var), operator, new ConstantContinuousExpression(value));
    }
    
    public static Guard CreateGuard(Expression expr1, String operator, Expression expr2)
    {
        if(operator.equals("=="))
            return new Guard(CreateEqualityExpression(expr1, expr2));
        
        throw new RuntimeException(String.format("Operator %s is not defined yet.", operator));
    }
    
    public static Guard CreateGuard(Variable var1, String operator, Variable var2)
    {
        return CreateGuard(new VariableExpression(var1), operator, new VariableExpression(var2));
    }
    
    public static ConstantContinuousExpression Const(float f)
    {
        return new ConstantContinuousExpression(f);
    }
    
    public static ConstantDiscreteExpression Const(int i)
    {
        return new ConstantDiscreteExpression(i);
    }
    
    public static Invarient CreateInvarient(Variable var1, String operator, Variable var2)
    {
        return new Invarient(CreateBinaryExpression(var1, operator, ExpressionFrom(var2)));
    }
        
    public static Invarient CreateInvarient(Variable var, String operator, Expression e2)
    {
        return new Invarient(CreateBinaryExpression(var, operator, e2));
    }

    public static Invarient CreateInvarient(Expression e1, String operator, Expression e2)
    {
        return new Invarient(CreateBinaryExpression(e1, operator, e2));
    }
    
    public static VariableExpression ExpressionFrom(Variable var)
    {
        return new VariableExpression(var);
    }
    
    public static NullExpression UnknowExpression(String expr)
    {
        return new NullExpression(expr);
    }
    
    public static void Reserve(SingleCommunicationRealVariablePool pool, Actor actor, String handlerName, int size)
    {
        pool.Reserve(
                actor, 
                new NormalMessage(actor.Type().FindMessageHandler(handlerName)), 
                size,
                String.format("%s__%s", actor.Name(), handlerName));
    }

}
