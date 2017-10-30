/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.CommunicationType;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessageHandler;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class SimplePriodicModel
{
    static public final String  First_Actor__handler = "Handler"; 
    static public final String  First_Actor__actor_param = "target"; 

    
    
    static public final String  Second_Actor__handler = "Handler"; 

    static public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        SoftwareActorType firstActorType = new SoftwareActorType("FirstActor");
        SoftwareActorType secondActorType = new SoftwareActorType("SecondActorType");
        
        FillSkeletonFirstActorType(firstActorType, secondActorType);
        FillSkeletonSecondActorType(secondActorType);
        
        FillFleshFirstActorType(firstActorType, secondActorType);
        FillFleshSecondActorType(secondActorType);
        
        PhysicalActorType clockType = CreatePeriodicClockType();
        
        SoftwareActor firstActor = new SoftwareActor("firstActor", firstActorType, 10);
        SoftwareActor secondActor = new SoftwareActor("secondActor", secondActorType, 10);
        
        BindInstance(firstActor, First_Actor__actor_param, secondActor, CommunicationType.Wire);
        
        
        PhysicalActor clock = CreatePreriodicClock(firstActor, First_Actor__handler, clockType);
        
        
        definition.AddActor(firstActor);
        definition.AddActor(secondActor);
        definition.AddActor(clock);
        
        
        definition.SetMainBlock(new MainBlock());
        return definition;
        
    }

    private static void FillSkeletonFirstActorType(SoftwareActorType firstActorType, SoftwareActorType secondActorType)
    {
        InstanceParameter instanceParameter = new InstanceParameter(First_Actor__actor_param, secondActorType);
        
        firstActorType.AddInstanceParameter(instanceParameter);
        
        MessageHandler messageHandler = new MessageHandler(Message.MessageType.Control);
        firstActorType.AddMessageHandler(First_Actor__handler, messageHandler);
    }

    private static void FillSkeletonSecondActorType(SoftwareActorType secondActorType)
    {
        MessageHandler messageHandler = new MessageHandler(Message.MessageType.Control);
        messageHandler.Parameters().Add(new VariableParameter(new FloatVariable("input")));
        secondActorType.AddMessageHandler(Second_Actor__handler, messageHandler);
    }

    private static void FillFleshFirstActorType(SoftwareActorType firstActorType, SoftwareActorType secondActorType)
    {
        MessageHandler handler = firstActorType.FindMessageHandler(First_Actor__handler);
        InstanceParameter parameter = firstActorType.FindInstanceParameter(First_Actor__actor_param);
        MessageHandler targetMessage = secondActorType.FindMessageHandler(Second_Actor__handler);
        
        handler.AddStatement(new SendStatement(
                new ParametricActorLocator(parameter), 
                new DirectMessageLocator(new NormalMessage(targetMessage)),
                MessageArguments.From(new VariableArgument(Variable.Type.floatingPoint, new ConstantContinuousExpression(2)))));
    }

    private static void FillFleshSecondActorType(SoftwareActorType secondActorType)
    {
        MessageHandler handler = secondActorType.FindMessageHandler(Second_Actor__handler);
        
        handler.AddStatement(
                new IfStatement(
                        new BinaryExpression(new ConstantContinuousExpression(2), new EqualityOperator(), new VariableExpression(handler.Parameters().Find("input").Variable())), 
                        Statement.EmptyStatements(), 
                        Statement.EmptyStatements()));
    }


}
