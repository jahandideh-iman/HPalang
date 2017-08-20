/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.DiscreteExpressions.ArithmeticExpression;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ModelDefinition;
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
public class VendingMachine
{
    public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
                
//        SoftwareActor User = new SoftwareActor("User", 10);
//        SoftwareActor Machine = new SoftwareActor("Machine", 10);
//        SoftwareActor Heater = new SoftwareActor("Heater", 10);
//        SoftwareActor Filler = new SoftwareActor("Filler", 10);
//        
//        ContinuousVariable Heater_t = new ContinuousVariable("t");
//        ContinuousVariable Filler_h = new ContinuousVariable("h");
//
//        DiscreteVariable user_OrderType = new DiscreteVariable("orderType");
//        DiscreteVariable machine_OrderType = new DiscreteVariable("orderType");
//        
////        Heater.AddContinuousVariable(Heater_t, 0);
////        Filler.AddContinuousVariable(Filler_h, 0);
//        
//        User.AddDiscreteVariable(user_OrderType, 0);
//        Machine.AddDiscreteVariable(machine_OrderType, 0);
//        
//        
//        MessageHandler hanlder_User_Order = new MessageHandler();
//        MessageHandler hanlder_User_RecieveOrder = new MessageHandler();
//        User.AddMessageHandler("Order", hanlder_User_Order);
//        User.AddMessageHandler("RecieveOrder", hanlder_User_RecieveOrder);
//        
//        MessageHandler hanlder_Machine_PrepareTea = new MessageHandler();
//        MessageHandler hanlder_Machine_PrepareCoffee = new MessageHandler();
//        MessageHandler hanlder_Machine_Heated = new MessageHandler();
//        MessageHandler hanlder_Machine_Filled = new MessageHandler();
//        Machine.AddMessageHandler("PrepareTea", hanlder_Machine_PrepareTea);
//        Machine.AddMessageHandler("PrepareCoffee", hanlder_Machine_PrepareCoffee);
//        Machine.AddMessageHandler("Heated", hanlder_Machine_Heated);
//        Machine.AddMessageHandler("Filled", hanlder_Machine_Filled);  
//        
//        MessageHandler handler_Heater_HeatUp100 = new MessageHandler();
//        MessageHandler handler_Heater_HeatUp90 = new MessageHandler();
//        Heater.AddMessageHandler("HeatUp100", handler_Heater_HeatUp100);
//        Heater.AddMessageHandler("HeatUp90", handler_Heater_HeatUp90);
//        
//        MessageHandler handler_Filler_Fill300= new MessageHandler();
//        MessageHandler handler_Filler_Fill200 = new MessageHandler();
//        Heater.AddMessageHandler("Fill300", handler_Filler_Fill300);
//        Heater.AddMessageHandler("Fill200", handler_Filler_Fill200);
//        
//        hanlder_User_Order.AddStatement(new IfStatement(
//                new ComparisonExpression(new VariableExpression(user_OrderType), ComparisonExpression.Operator.Equal, new ConstantDiscreteExpression(1))
//                , Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_PrepareCoffee)), new DiscreteAssignmentStatement(user_OrderType, new ConstantDiscreteExpression(0)))
//                , Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_PrepareTea)), new DiscreteAssignmentStatement(user_OrderType, new ConstantDiscreteExpression(1)))
//        ));
//        
//        hanlder_User_RecieveOrder.AddStatement(new DelayStatement(1.0f));
//        hanlder_User_RecieveOrder.AddStatement(new SendStatement(User,new NormalMessage(hanlder_User_Order)));
//        
//        hanlder_Machine_PrepareTea.AddStatement(new DiscreteAssignmentStatement(machine_OrderType, new ConstantDiscreteExpression(0)));
//        hanlder_Machine_PrepareTea.AddStatement(new SendStatement(Heater, new NormalMessage(handler_Heater_HeatUp100)));
//        
//        hanlder_Machine_PrepareCoffee.AddStatement(new DiscreteAssignmentStatement(machine_OrderType, new ConstantDiscreteExpression(1)));
//        hanlder_Machine_PrepareCoffee.AddStatement(new SendStatement(Heater, new NormalMessage(handler_Heater_HeatUp90)));
//        
//        hanlder_Machine_Heated.AddStatement(new IfStatement(
//                new ComparisonExpression(new VariableExpression(machine_OrderType), ComparisonExpression.Operator.Equal, new ConstantDiscreteExpression(0))
//                , Statement.StatementsFrom(new SendStatement(Filler, new NormalMessage(handler_Filler_Fill300)))
//                , Statement.StatementsFrom(new SendStatement(Filler, new NormalMessage(handler_Filler_Fill200)))
//        ));
//  
//        
//        hanlder_Machine_Filled.AddStatement(new SendStatement(User, new NormalMessage(hanlder_User_RecieveOrder)));
//        
//        
//        handler_Heater_HeatUp100.AddStatement(new ContinuousBehaviorStatement(
//                new ContinuousBehavior(
//                        "t<=100", new DifferentialEquation(Heater_t, "(120 - t)/20"), "t == 100"
//                        ,Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_Heated)))
//                )));
//        handler_Heater_HeatUp90.AddStatement(new ContinuousBehaviorStatement(
//                new ContinuousBehavior(
//                        "t<=90",  new DifferentialEquation(Heater_t, "(120 - t)/20"), "t == 90"
//                        ,Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_Heated)))
//                )));
//        
//        
//        handler_Filler_Fill300.AddStatement(new ContinuousBehaviorStatement(
//                new ContinuousBehavior(
//                        "h<=300",  new DifferentialEquation(Filler_h, "100"), "h == 300"
//                        ,Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_Filled)))
//                )));
//        handler_Filler_Fill200.AddStatement(new ContinuousBehaviorStatement(
//                new ContinuousBehavior(
//                        "h<=200", new DifferentialEquation(Filler_h, "100"), "h == 200"
//                        ,Statement.StatementsFrom(new SendStatement(Machine, new NormalMessage(hanlder_Machine_Filled)))
//                )));
//        
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(User, new NormalMessage(hanlder_User_Order)));
//        
//        definition.AddActor(User);
//        definition.AddActor(Machine);
//        definition.AddActor(Heater);              
//        definition.AddActor(Filler);
//        definition.SetMainBlock(mainBlock);

        return definition;
    }
}
