/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.Actor;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.DiscreteExpressions.ArithmeticExpression;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;

/**
 *
 * @author Iman Jahandideh
 */
public class DrawBridge
{
    public ProgramDefinition Create()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        DiscreteVariable Bridge_cars = new DiscreteVariable("cars");
        DiscreteVariable Bridge_bridgeStatus =  new DiscreteVariable("bridgeStatus");
        
        Actor Bridge = new Actor("Bridge",10); 
        Bridge.AddDiscreteVariable(Bridge_cars, 0 );
        Bridge.AddDiscreteVariable(Bridge_bridgeStatus , 0 );
        
        MessageHandler handler_Bridge_EnqueueCar = new MessageHandler();
        MessageHandler handler_Bridge_StartLowering = new MessageHandler();
        MessageHandler handler_Bridge_StartRaising = new MessageHandler();
        MessageHandler handler_Bridge_PassACar = new MessageHandler();
        
        Bridge.AddMessageHandler("EnqueueCar", handler_Bridge_EnqueueCar);
        Bridge.AddMessageHandler("PassCar", handler_Bridge_PassACar);
        Bridge.AddMessageHandler("StartLowering", handler_Bridge_StartLowering);
        Bridge.AddMessageHandler("StartRaising", handler_Bridge_StartRaising);
        
        Actor CarDispatcher1 = CreateCarDispatcher("CarDispatcher1", 1.0f, Bridge, handler_Bridge_EnqueueCar);
        Actor CarDispatcher2 = CreateCarDispatcher("CarDispatcher2", 1.0f, Bridge, handler_Bridge_EnqueueCar);
        

        handler_Bridge_EnqueueCar.AddStatement(new DiscreteAssignmentStatement(Bridge_cars , 
                new ArithmeticExpression(new VariableExpression(Bridge_cars), ArithmeticExpression.Operator.Add ,new ConstantDiscreteExpression(1))));
        handler_Bridge_EnqueueCar.AddStatement(new IfStatement(
                new ComparisonExpression(new VariableExpression(Bridge_bridgeStatus ), ComparisonExpression.Operator.Equal, new ConstantDiscreteExpression(0))
                , Statement.StatementsFrom(new SendStatement(Bridge,  new NormalMessage(handler_Bridge_StartLowering)))
                , Statement.EmptyStatements()));
       
        handler_Bridge_StartLowering.AddStatement(new IfStatement(
                new ComparisonExpression(new VariableExpression(Bridge_bridgeStatus), ComparisonExpression.Operator.Equal, new ConstantDiscreteExpression(0)),
                Statement.StatementsFrom(
                        new DiscreteAssignmentStatement(Bridge_bridgeStatus, new ConstantDiscreteExpression(1))
                        ,new ContinuousBehaviorStatement(
                                new ContinuousBehavior("degree>=0", "degree'=-10", "degree==0",
                                        Statement.StatementsFrom(
                                                new DiscreteAssignmentStatement(Bridge_bridgeStatus, new ConstantDiscreteExpression(2)),
                                                 new SendStatement(Bridge, new NormalMessage(handler_Bridge_PassACar)))))),
                 Statement.EmptyStatements()));

        handler_Bridge_PassACar.AddStatement
        (new ContinuousBehaviorStatement(
                new ContinuousBehavior("timer<=d", "timer'=1", "timer==d",
                Statement.StatementsFrom(
                        new DiscreteAssignmentStatement(Bridge_cars,new ArithmeticExpression(new VariableExpression(Bridge_cars), ArithmeticExpression.Operator.Subtract ,new ConstantDiscreteExpression(1)))
                        ,new IfStatement(
                                new ComparisonExpression(new VariableExpression(Bridge_cars), ComparisonExpression.Operator.LesserEqual, new ConstantDiscreteExpression(0))
                                , Statement.StatementsFrom(new SendStatement(Bridge,  new NormalMessage(handler_Bridge_StartRaising)))
                                , Statement.StatementsFrom(new SendStatement(Bridge,  new NormalMessage(handler_Bridge_PassACar))))
                ))));

        handler_Bridge_StartRaising.AddStatement(new DiscreteAssignmentStatement(Bridge_bridgeStatus, new ConstantDiscreteExpression(1)));
        handler_Bridge_StartRaising.AddStatement(new ContinuousBehaviorStatement(
                new ContinuousBehavior("degree<=90", "degree'=degree/10+5", "degree==90",
                        Statement.StatementsFrom(new DiscreteAssignmentStatement(Bridge_bridgeStatus, new ConstantDiscreteExpression(0)),
                                new IfStatement(
                                        new ComparisonExpression(new VariableExpression(Bridge_cars), ComparisonExpression.Operator.Greater, new ConstantDiscreteExpression(0)),
                                         Statement.StatementsFrom(new SendStatement(Bridge, new NormalMessage(handler_Bridge_StartLowering))),
                                         Statement.EmptyStatements())))));

        
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(CarDispatcher1, new NormalMessage(CarDispatcher1.GetMessageHandler("Dispatch"))));
        //mainBlock.AddSendStatement(new SendStatement(CarDispatcher2, new NormalMessage(CarDispatcher1.GetMessageHandler("Dispatch"))));
        //mainBlock.AddSendStatement(new SendStatement(Bridge, new NormalMessage(handler_Bridge_EnqueueCar)));
        //mainBlock.AddSendStatement(new SendStatement(Bridge, new NormalMessage(handler_Bridge_EnqueueCar)));
        
        definition.AddActor(CarDispatcher1);
        //definition.AddActor(CarDispatcher2);
        definition.AddActor(Bridge);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
    
    private Actor CreateCarDispatcher(String id, float delay, Actor bridge, MessageHandler handler_Bridge_EnqueueCar)
    {
        Actor CarDispatcher = new Actor(id,1);
        DiscreteVariable totalCars = new DiscreteVariable("totalCars");
        
        CarDispatcher.AddDiscreteVariable(totalCars,2 );
                
        MessageHandler handler_Dispatch = new MessageHandler();
        
        CarDispatcher.AddMessageHandler("Dispatch", handler_Dispatch);

        handler_Dispatch.AddStatement(new DiscreteAssignmentStatement(totalCars , 
                new ArithmeticExpression(new VariableExpression(totalCars), ArithmeticExpression.Operator.Subtract ,new ConstantDiscreteExpression(1))));
        handler_Dispatch.AddStatement(new SendStatement(bridge, new NormalMessage(handler_Bridge_EnqueueCar)));
        handler_Dispatch.AddStatement(new IfStatement(
                new ComparisonExpression(new VariableExpression(totalCars), ComparisonExpression.Operator.Greater, new ConstantDiscreteExpression(0))
                , Statement.StatementsFrom(new DelayStatement(delay), new SendStatement(CarDispatcher,  new NormalMessage(handler_Dispatch)))
                , Statement.EmptyStatements()));
        return CarDispatcher;
    }
}
