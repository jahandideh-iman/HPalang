/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.Core.ActorLocators.DelegationActorLocator;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.ActorType;
import HPalang.Core.CommunicationType;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.Delegation;
import HPalang.Core.DelegationParameter;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessageHandler;
import HPalang.Core.MessageLocator;
import HPalang.Core.MessageLocators.DelegationMessageLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelCreationUtilities
{
    public static void AddPort(ActorType actorType, String portName, Variable targetVariable)
    {
        MessageHandler port = new MessageHandler(Message.MessageType.Data);
        Variable localVariable =CreateVariable(targetVariable.type(),"local_" + portName); 
        port.Parameters().Add(new VariableParameter(localVariable));
        port.AddStatement(new AssignmentStatement(targetVariable, new VariableExpression(localVariable)));
        
        actorType.AddMessageHandler(portName,port);
    }
    
    public static void AddPort(ActorType actorType, String portName, String targetVariableName)
    {
        AddPort(actorType, portName, actorType.FindVariable(targetVariableName));
    }
    
    public static void SetNetworkPriority(Actor actor, String messageHandlerName, int priority)
    {
        MessageHandler handler = actor.Type().FindMessageHandler(messageHandlerName);
        actor.SetMessageHandlerPriority(handler ,priority);
    }
    
    public static void SetNetworkDelay(Actor sender, Actor reciever, String messageHandler, float delay)
    {
        sender.SetNetworkDelay(reciever,  new NormalMessage(reciever.Type().FindMessageHandler(messageHandler)), delay);
    }
    
    public static SendStatement CreateModeChangeRequest(Mode mode, ActorLocator actorLocator)
    {
        return CreateSendStateemnt(
                actorLocator, 
                new MessageWithBody(
                        Statement.StatementsFrom(new ModeChangeStatement(mode))
                )
        );
    }

    public static void BindInstance(Actor actor, String parameterName, Actor instance, CommunicationType communicationType)
    {
        InstanceParameter wheelControllerInstanceParam = actor.Type().FindInstanceParameter(parameterName);
        actor.BindInstance(wheelControllerInstanceParam, instance, CommunicationType.Wire);
    }

    public static void BindDelagation(Actor actor, String parameterName, Actor instance, String instanceHandlerName, CommunicationType communicationType)
    {
        DelegationParameter delgationParam = actor.Type().FindDelegationParameter(parameterName);

        Delegation delegation = new Delegation(
                instance,
                instance.Type().FindMessageHandler(instanceHandlerName));

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
    
    public static AssignmentStatement ResetFor(RealVariable var)
    {
        return new AssignmentStatement(var, new ConstantContinuousExpression(0));
    }
    
    public static SendStatement CreateSendStateemnt(ActorLocator actorLocator, MessageLocator messageLocator, Variable ... argumentVariables )
    {
        MessageArguments arguments = new MessageArguments();
        int i = 0;
        
        for(VariableParameter parameter : messageLocator.Parameters().AsSet())
        {
            arguments.Add(new VariableArgument(parameter, new VariableExpression(argumentVariables[i])));
            i++;
        }
        
        return new SendStatement(
                                actorLocator,
                                messageLocator,
                                arguments
        );
    }

    public static SendStatement CreateSendStateemnt(ActorLocator actorLocator, Message message, Variable ... argumentVariables )
    {
        return CreateSendStateemnt(actorLocator, new DirectMessageLocator(message), argumentVariables);
    }
        
    public static SendStatement CreateSendStatement(InstanceParameter instance, MessageHandler handler, Variable ... argumentVariables)
    {
        return CreateSendStateemnt(
                new ParametricActorLocator(instance), 
                new NormalMessage(handler), 
                argumentVariables);
    }
    
    public static SendStatement CreateSendStatement(DelegationParameter delegationParameter, Variable ... argumentVariables)
    {
        return CreateSendStateemnt(
                new DelegationActorLocator(delegationParameter), 
                new DelegationMessageLocator(delegationParameter), 
                argumentVariables);
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
}
