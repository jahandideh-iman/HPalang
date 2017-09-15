/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorLocators.SelfActorLocator;
import HPalang.Core.CommunicationType;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.ArithmeticExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.*;
import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import static HPalang.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class VendingMachine
{
    static final String Pourer__controller_param = "controller";
    static final String Pourer__target_volume_port = "targetVolumePort";
    static final String Pourer__target_volume = "targetVolume";
    static final String Pourer__volume = "volume";
    
    
    static final String Heater__controller_param = "controller";
    static final String Heater__target_temp_port = "targetTempPort";
    static final String Heater__target_temp = "targetTemp";
    static final String Heater__temp = "temp";
    
    static final String Vending_Machine__pourer_param = "pourer";
    static final String Vending_Machine__heater_param = "heater";
    static final String Vending_Machine__user_param = "user";
    static final String Vending_Machine__order_type = "orderType";
    static final String Vending_Machine__prepare_tea_handler = "prepareTea";
    static final String Vending_Machine__prepare_coffee_handler = "prepareCoffee";
    static final String Vending_Machine__drink_heated_handler = "drinkHeated";
    static final String Vending_Machine__cup_filled_handler = "cupFilled";
    
    static final String User__controller_param = "controller";
    static final String User__last_order_type = "lastOrderType";
    static final String User__order_handler = "order";
    static final String User__drink_ready_handler = "drinkReady";
    
    static public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType pourerType = new PhysicalActorType("Pourer");
        PhysicalActorType heaterType = new PhysicalActorType("Heater");
        SoftwareActorType VMCType = new SoftwareActorType("VendingMachineControllerT");
        SoftwareActorType userType = new SoftwareActorType("User");

        
        FillSkeletonForPourerType(pourerType, VMCType);
        FillSkeletonForHeaterType(heaterType, VMCType);
        FillSkeletonForVMCType(
                VMCType,
                pourerType, 
                heaterType, 
                userType);
        
        FillSkeletonForUserType(userType, VMCType);
        
        FillFleshForPourerType(pourerType);
        FillFleshForHeaterType(heaterType);
        FillFleshForVMCType(
                VMCType,
                pourerType,
                heaterType,
                userType);
        FillFleshForUserType(userType, VMCType);
        
        
        PhysicalActor pourer = new PhysicalActor("pourer", pourerType, 10);
        PhysicalActor heater = new PhysicalActor("heater", heaterType, 10);
        SoftwareActor vmc = new SoftwareActor("controller", VMCType, 10);
        SoftwareActor user = new SoftwareActor("user", userType, 10);
        
        BindInstance(pourer, Pourer__controller_param, vmc, CommunicationType.Wire);
        BindInstance(heater, Heater__controller_param, vmc, CommunicationType.Wire);
        BindInstance(vmc, Vending_Machine__pourer_param, pourer, CommunicationType.Wire);
        BindInstance(vmc, Vending_Machine__heater_param, heater, CommunicationType.Wire);
        BindInstance(vmc, Vending_Machine__user_param, user, CommunicationType.Wire);
        BindInstance(user, User__controller_param, vmc, CommunicationType.Wire);
        
        
        definition.AddActor(pourer);
        definition.AddActor(heater);
        definition.AddActor(vmc);
        definition.AddActor(user);
        
        MainBlock mainBlock = new MainBlock();
        mainBlock.AddSendStatement(CreateDirectSendStatement(user, User__order_handler));
        
        definition.SetMainBlock(mainBlock);
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

    static private void FillSkeletonForPourerType(PhysicalActorType pourerType, SoftwareActorType vendingMachineControllerType)
    {
        
        AddInstanceParameter(pourerType, Pourer__controller_param, vendingMachineControllerType);
        AddVariable(pourerType, new RealVariable(Pourer__volume));
        AddVariable(pourerType, new RealVariable(Pourer__target_volume));
        AddPort(pourerType, Pourer__target_volume_port, Pourer__target_volume);
        
        pourerType.AddMode(new Mode("On"));
    }

    static private void FillSkeletonForHeaterType(PhysicalActorType heaterType, SoftwareActorType vendingMachineControllerType)
    {
        AddInstanceParameter(heaterType, Heater__controller_param, vendingMachineControllerType);
        AddVariable(heaterType, new RealVariable(Heater__temp));
        AddVariable(heaterType, new RealVariable(Heater__target_temp));
        AddPort(heaterType, Heater__target_temp_port, Heater__target_temp);
        
        heaterType.AddMode(new Mode("On"));
    }

    static private void FillSkeletonForVMCType(SoftwareActorType VMCType, PhysicalActorType pourerType, PhysicalActorType heaterType, SoftwareActorType userType)
    {
        AddInstanceParameter(VMCType, Vending_Machine__pourer_param, pourerType);
        AddInstanceParameter(VMCType, Vending_Machine__heater_param, heaterType);
        AddInstanceParameter(VMCType, Vending_Machine__user_param, userType);
        
        AddVariable(VMCType, new IntegerVariable(Vending_Machine__order_type));
        
        AddControlMessageHandler(VMCType, Vending_Machine__prepare_tea_handler);
        AddControlMessageHandler(VMCType, Vending_Machine__prepare_coffee_handler);
        AddControlMessageHandler(VMCType, Vending_Machine__drink_heated_handler);
        AddControlMessageHandler(VMCType, Vending_Machine__cup_filled_handler);
        
    }

    static private void FillSkeletonForUserType(SoftwareActorType userType, SoftwareActorType VMCType)
    {
        AddInstanceParameter(userType, User__controller_param, VMCType);
        
        AddVariable(userType, new IntegerVariable(User__last_order_type));
        
        AddControlMessageHandler(userType, User__order_handler);
        AddControlMessageHandler(userType, User__drink_ready_handler);
    }

    static private void FillFleshForPourerType(PhysicalActorType pourerType)
    {
        Mode on = pourerType.FindMode("On");
        InstanceParameter controller = pourerType.FindInstanceParameter(Pourer__controller_param);
        RealVariable volume = (RealVariable) pourerType.FindVariable(Pourer__volume);
        Variable targetVolume = pourerType.FindVariable(Pourer__target_volume);
        
        on.SetInvarient(String.format("%s <= %s", volume.Name(), targetVolume.Name()));
        
        on.AddDifferentialEquation(new DifferentialEquation(volume, "20"));
        
        on.SetGuard(String.format("%s == %s", volume.Name(), volume.Name()));
        
        on.AddAction(CreateSendStatement(controller, Vending_Machine__cup_filled_handler));
        on.AddAction(new ModeChangeStatement(Mode.None()));
        on.AddAction(CreateResetFor(volume));
    }

    static private void FillFleshForHeaterType(PhysicalActorType heaterType)
    {
        Mode on = heaterType.FindMode("On");
        InstanceParameter controller = heaterType.FindInstanceParameter(Heater__controller_param);
        RealVariable temp = (RealVariable) heaterType.FindVariable(Heater__temp);
        Variable targetTemp = heaterType.FindVariable(Heater__target_temp);
        
        on.SetInvarient(String.format("%s <= %s", temp.Name(), targetTemp.Name()));
        
        on.AddDifferentialEquation(new DifferentialEquation(temp, "10"));
        
        on.SetGuard(String.format("%s == %s", temp.Name(), temp.Name()));
        
        on.AddAction(CreateSendStatement(controller, Vending_Machine__drink_heated_handler));
        on.AddAction(new ModeChangeStatement(Mode.None()));
        on.AddAction(CreateResetFor(temp));
    }

    static private void FillFleshForVMCType(
            SoftwareActorType VMCType,
            PhysicalActorType pourerType,
            PhysicalActorType heaterType,
            SoftwareActorType userType)
    {
        InstanceParameter heater = VMCType.FindInstanceParameter(Vending_Machine__heater_param);
        InstanceParameter pourer = VMCType.FindInstanceParameter(Vending_Machine__pourer_param);
        InstanceParameter user = VMCType.FindInstanceParameter(Vending_Machine__user_param);
        
        IntegerVariable orderType = (IntegerVariable) VMCType.FindVariable(Vending_Machine__order_type);
        
        MessageHandler prepareTea = VMCType.FindMessageHandler(Vending_Machine__prepare_tea_handler);
        MessageHandler prepareCoffee = VMCType.FindMessageHandler(Vending_Machine__prepare_coffee_handler);
        MessageHandler drinkHeated = VMCType.FindMessageHandler(Vending_Machine__drink_heated_handler);
        MessageHandler cupFilled = VMCType.FindMessageHandler(Vending_Machine__cup_filled_handler);
        
        
        prepareTea.AddStatement(CreateSendStatement(heater, Heater__target_temp_port, new ConstantContinuousExpression(100)));
        prepareTea.AddStatement(CreateModeChangeRequest("On", heater));
        prepareTea.AddStatement(new AssignmentStatement(orderType, new ConstantDiscreteExpression(1)));
        
        prepareCoffee.AddStatement(CreateSendStatement(heater, Heater__target_temp_port, new ConstantContinuousExpression(90)));
        prepareCoffee.AddStatement(CreateModeChangeRequest("On", heater));
        prepareCoffee.AddStatement(new AssignmentStatement(orderType, new ConstantDiscreteExpression(2)));
        
        drinkHeated.AddStatement(
                new IfStatement(
                        CreateEqualityExpression(VariableExpression(orderType), new ConstantDiscreteExpression(1)), 
                        Statement.StatementsFrom(CreateSendStatement(pourer, Pourer__target_volume_port, new ConstantContinuousExpression(30))), 
                        Statement.StatementsFrom(CreateSendStatement(pourer, Pourer__target_volume_port, new ConstantContinuousExpression(20)))));
        
        drinkHeated.AddStatement(CreateModeChangeRequest("On", pourer));
        
        cupFilled.AddStatement(CreateSendStatement(user, User__drink_ready_handler));   
    }

    static private void FillFleshForUserType(SoftwareActorType userType, SoftwareActorType VMCType)
    {
        InstanceParameter controller = userType.FindInstanceParameter(User__controller_param);
        
        IntegerVariable lastOrderType = (IntegerVariable) userType.FindVariable(User__last_order_type);
        
        MessageHandler order = userType.FindMessageHandler(User__order_handler);
        MessageHandler drinkReady = userType.FindMessageHandler(User__drink_ready_handler);
        
        order.AddStatement(
                new IfStatement(
                        CreateEqualityExpression(VariableExpression(lastOrderType), new ConstantDiscreteExpression(1)),
                        Statement.StatementsFrom(
                                CreateSendStatement(controller, Vending_Machine__prepare_coffee_handler),
                                new AssignmentStatement(lastOrderType, new ConstantDiscreteExpression(2))), 
                        Statement.StatementsFrom(
                                CreateSendStatement(controller, Vending_Machine__prepare_tea_handler),
                                new AssignmentStatement(lastOrderType, new ConstantDiscreteExpression(1)))));
        
        drinkReady.AddStatement(CreateSelfSendStatement(userType, User__order_handler));
    }
}
