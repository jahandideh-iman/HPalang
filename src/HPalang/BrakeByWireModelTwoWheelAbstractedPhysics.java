/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorType;
import HPalang.Core.CommunicationType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DelegationParameter;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.*;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MessageHandler;
import HPalang.Core.Mode;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.SoftwareActor;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Variable;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.RealVariable;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class BrakeByWireModelTwoWheelAbstractedPhysics
{
    public static final float arbitrartDelay = 13.0f;
    public static final String Wheel__controller_instance = "controller";
    public static final String Wheel__rpm_delegation = "wheel_rpm_delegation";
    public static final String Wheel__torque_port = "torque_port";
    public static final String Wheel__break_hanlder = "break";   
    public static final String Wheel__no_break_hanlder = "noBreak";

    
    public static final String Wheel_Controller__wheel_instance = "wheel";
    public static final String Wheel_Controller__wheel_rmp_port = "wheel_rpm_port";
    public static final String Wheel_Controller__apply_torque_handler = "applyTorque";

    public static final String Global_Brake_Controller__wheel_rpm_FR_port = "wheel_rpm_FR_port";
    public static final String Global_Brake_Controller__wheel_rpm_FL_port = "wheel_rpm_FL_port";

    
    public static final String Global_Brake_Controller__brake_percent_port = "brake_percent_port";
    public static final String Global_Brake_Controller__control_handler = "control";
    public static final String Global_Brake_Controller__wheel_controller_FR_Instance = "wheel_controller_FR";
    public static final String Global_Brake_Controller__wheel_controller_FL_Instance = "wheel_controller_FL";

    
    public static final String Brake__controller_instance = "controller";
    
    public static final String Clock__callback = "callback";
    
   
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        SoftwareActorType wheelType = new SoftwareActorType("Wheel");
        SoftwareActorType wheelControllerType = new SoftwareActorType("WheelController");
        SoftwareActorType brakeType = new SoftwareActorType("Brake");
        SoftwareActorType globalBrakeControllerType = new SoftwareActorType("GlobalBrakeController");
        SoftwareActorType clockType = new SoftwareActorType("Clock");
        
        
        FillSkeletonForWheelType(wheelType, wheelControllerType);
        FillSkeletonForWheelControllerType(wheelControllerType, wheelType);
        FillSkeletonForBrakeType(brakeType, globalBrakeControllerType);
        FillSkeletonForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType);
        FillClockType(clockType);
        
        FillFleshForWheelType(wheelType, wheelControllerType.FindMessageHandler("wheel_rpm_port"));
        FillFleshForWheelControllerType(
                wheelControllerType,
                wheelType.FindMessageHandler(Wheel__break_hanlder),
                wheelType.FindMessageHandler(Wheel__no_break_hanlder),
                wheelType.FindMessageHandler(Wheel__torque_port));
        FillFleshForBrakeType(brakeType, globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__brake_percent_port));
        FillFleshForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType.FindMessageHandler(Wheel_Controller__apply_torque_handler));
        
        SoftwareActor brake_pedal = new SoftwareActor("brake", brakeType, 2);
        SoftwareActor global_brake_controller = new SoftwareActor("global_brake_controller", globalBrakeControllerType, 2);
        
        SoftwareActor wheel_controller_FR = new SoftwareActor("wheel_controller_FR",wheelControllerType,2);     
        SoftwareActor wheel_controller_FL = new SoftwareActor("wheel_controller_FL",wheelControllerType, 2);


        SoftwareActor wheel_FR = new SoftwareActor("wheel_FR", wheelType,2);       
        SoftwareActor wheel_FL = new SoftwareActor("wheel_FL", wheelType,2);

        
        SoftwareActor clock = new SoftwareActor("clock", clockType,0);
        
        FillWheelActor(wheel_FR,wheel_controller_FR,global_brake_controller,Global_Brake_Controller__wheel_rpm_FR_port);
        FillWheelActor(wheel_FL,wheel_controller_FL,global_brake_controller,Global_Brake_Controller__wheel_rpm_FL_port);
  
        FillWheelControllerActor(wheel_controller_FR, wheel_FR);
        FillWheelControllerActor(wheel_controller_FL, wheel_FL);
 
        FillGlobalBrakeController(
                global_brake_controller,
                wheel_controller_FR,
                wheel_controller_FL);
        
        FillBrake(brake_pedal, global_brake_controller);
        
        FillClock(clock, global_brake_controller, Global_Brake_Controller__control_handler);

        definition.AddType(wheelType);
        definition.AddType(wheelControllerType);
        definition.AddType(brakeType);
        definition.AddType(globalBrakeControllerType);
        definition.AddType(clockType);
        
        definition.AddActor(brake_pedal);
        definition.AddActor(global_brake_controller);
        
        definition.AddActor(wheel_controller_FR);
        definition.AddActor(wheel_controller_FL);
        
        definition.AddActor(wheel_FR);
        definition.AddActor(wheel_FL);
        
        definition.AddActor(clock);
        
        definition.SetMainBlock(new MainBlock());
        
        SetNetworkPriority(definition, wheel_FR, Wheel__torque_port, 100);
        SetNetworkPriority(definition, wheel_FL, Wheel__torque_port, 101);

        //SetNetworkPriority(wheel_FR, ?!!!, 100); Mode Change
        
        SetNetworkPriority(definition, wheel_controller_FR, Wheel__torque_port, 200);
        SetNetworkPriority(definition, wheel_controller_FL, Wheel__torque_port, 201);
        
        SetNetworkPriority(definition, wheel_controller_FR, Wheel_Controller__apply_torque_handler, 300);
        SetNetworkPriority(definition, wheel_controller_FL, Wheel_Controller__apply_torque_handler, 301);
        
        SetNetworkPriority(definition, global_brake_controller, Global_Brake_Controller__wheel_rpm_FR_port, 340);
        SetNetworkPriority(definition, global_brake_controller, Global_Brake_Controller__wheel_rpm_FL_port, 401);
        
        SetNetworkPriority(definition, global_brake_controller, Global_Brake_Controller__brake_percent_port, 503);
        SetNetworkPriority(definition, global_brake_controller, Global_Brake_Controller__control_handler, 603);
        
//        SetNetworkDelay(wheel_FR, wheel_controller_FR, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_FL, wheel_controller_FL, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_RR, wheel_controller_RR, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_RL, wheel_controller_RL, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
        
        SetNetworkDelay(definition, wheel_FR, global_brake_controller, Global_Brake_Controller__wheel_rpm_FR_port, 2.0f);
        SetNetworkDelay(definition, wheel_FL, global_brake_controller, Global_Brake_Controller__wheel_rpm_FL_port, 2.0f);

        
        SetNetworkDelay(definition, brake_pedal, global_brake_controller, Global_Brake_Controller__brake_percent_port, 3.0f);
        
        SetNetworkDelay(definition, global_brake_controller, wheel_controller_FR, Wheel_Controller__apply_torque_handler, 4.0f);
        SetNetworkDelay(definition, global_brake_controller, wheel_controller_FL, Wheel_Controller__apply_torque_handler, 4.0f);

        definition.SetEventSystemVariablePoolSize(8);
        definition.SetGlobalVariablePoolSize(10);
        
        
        return definition;
    }

    private static void FillSkeletonForWheelType(SoftwareActorType wheelType, ActorType wheelControllerType)
    {
        wheelType.AddMessageHandler(Wheel__break_hanlder, new MessageHandler(Message.MessageType.Control));
        wheelType.AddMessageHandler(Wheel__no_break_hanlder, new MessageHandler(Message.MessageType.Control));
//        wheelType.AddInstanceParameter(new InstanceParameter(Wheel__controller_instance, wheelControllerType));
//        wheelType.AddDelegationParameter(
//                new DelegationParameter(
//                        Wheel__rpm_delegation, 
//                        DelegationParameter.TypesFrom(Variable.Type.floatingPoint)
//                )
//        ); 
//                
//        wheelType.AddVariable(new RealVariable("timer"));
//        wheelType.AddVariable(new RealVariable("rpm"));
        wheelType.AddVariable(new RealVariable("torque"));
//
//        wheelType.AddMode(new Mode("NoBrake"));
//        wheelType.AddMode(new Mode("Break"));
//        
//        wheelType.SetInitialMode(wheelType.FindMode("NoBrake"));
//        
        AddPort(wheelType, Wheel__torque_port, wheelType.FindVariable("torque"));
    }
    
    private static void FillFleshForWheelType(SoftwareActorType wheelType, MessageHandler wheel_rpm_port)
    {
//        InstanceParameter controllerInstance = wheelType.FindInstanceParameter(Wheel__controller_instance); 
//        
//        DelegationParameter wheel_rpm_delegation = wheelType.FindDelegationParameter(Wheel__rpm_delegation);
//        
//        RealVariable timer = (RealVariable) wheelType.FindVariable("timer"); 
//        RealVariable rpm = (RealVariable) wheelType.FindVariable("rpm");
//        RealVariable torque = (RealVariable) wheelType.FindVariable("torque");
//
//        
//        Mode noBrakeMode = wheelType.FindMode("NoBrake");
//        Mode brakeMode = wheelType.FindMode("Break");
//        
//        noBrakeMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(0.01f))));
//        noBrakeMode.SetGuard(CreateGuard(timer, "==", 0.01f));
//        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
//        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, UnknowExpression("?!!!")));
//        
//        noBrakeMode.AddAction(CreateResetFor(timer));
//        noBrakeMode.AddAction(CreateSendStatement(controllerInstance, wheel_rpm_port, VariableExpression(rpm)));
//        noBrakeMode.AddAction(CreateSendStatement(wheel_rpm_delegation, VariableExpression(rpm)));
//        
//        brakeMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(0.01f))));
//        brakeMode.SetGuard(CreateGuard(timer, "==", 0.01f));
//        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
//        brakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, UnknowExpression("?!!!")));
//        
//        brakeMode.AddAction(CreateResetFor(timer));
//        brakeMode.AddAction(CreateSendStatement(controllerInstance, wheel_rpm_port, VariableExpression(rpm)));
//        brakeMode.AddAction(CreateSendStatement(wheel_rpm_delegation, VariableExpression(rpm)));
    }
    
    private static void FillSkeletonForWheelControllerType(SoftwareActorType wheelControllerType, ActorType wheelType)
    {       
        wheelControllerType.AddInstanceParameter(new InstanceParameter(Wheel_Controller__wheel_instance, wheelType));

        wheelControllerType.AddVariable(new FloatVariable("wheel_rpm"));
        wheelControllerType.AddVariable(new FloatVariable("slip_rate"));
        
        MessageHandler applyTorque = new MessageHandler(Message.MessageType.Control);
        
        AddParameter(applyTorque, "requested_torque", FloatVariable.class);
        AddParameter(applyTorque, "vehicle_speed", FloatVariable.class);
        wheelControllerType.AddMessageHandler(Wheel_Controller__apply_torque_handler, applyTorque);
        
        AddPort(wheelControllerType, Wheel_Controller__wheel_rmp_port, "wheel_rpm");        
    }
    
    private static void FillFleshForWheelControllerType(
            SoftwareActorType wheelControllerType, 
            MessageHandler brakeMode, 
            MessageHandler noBrakeMode, 
            MessageHandler wheel_torque_port)
    {
        InstanceParameter wheel = wheelControllerType.FindInstanceParameter(Wheel_Controller__wheel_instance);
        FloatVariable wheel_rpm = (FloatVariable) wheelControllerType.FindVariable("wheel_rpm");
        FloatVariable slip_rate = (FloatVariable) wheelControllerType.FindVariable("slip_rate");
        
        MessageHandler applyTorque = wheelControllerType.FindMessageHandler(Wheel_Controller__apply_torque_handler);
        FloatVariable requested_torque = (FloatVariable) applyTorque.FindVariableParameter("requested_torque").Variable();
        FloatVariable vehicle_speed = (FloatVariable) applyTorque.FindVariableParameter("vehicle_speed").Variable();
        
        applyTorque.AddStatement(new AssignmentStatement(slip_rate, new VariableExpression(vehicle_speed)));
        applyTorque.AddStatement(CreateSendStatement(wheel, wheel_torque_port , VariableExpression(requested_torque)));
        applyTorque.AddStatement(new IfStatement(
                 new BinaryExpression(
                        new BinaryExpression(
                                new VariableExpression(slip_rate),
                                new GreaterOperator(),
                                new ConstantDiscreteExpression(2)),
                        new LogicalOrOperator(),
                        new BinaryExpression(
                                new VariableExpression(requested_torque),
                                new EqualityOperator(),
                                new ConstantDiscreteExpression(2))),
                Statement.StatementsFrom(CreateSendStatement(wheel, noBrakeMode)), 
                Statement.StatementsFrom(CreateSendStatement(wheel,brakeMode))
        ));
        
        AddPort(wheelControllerType, Wheel_Controller__wheel_rmp_port, wheel_rpm);
    }

    private static void FillSkeletonForBrakeType(SoftwareActorType brakeType, SoftwareActorType globalBrakeControllerType)
    {
//        brakeType.AddInstanceParameter(new InstanceParameter(Brake__controller_instance, globalBrakeControllerType));
//        
//        
//        brakeType.AddVariable(new RealVariable("brake_percent"));        
//        brakeType.AddVariable(new RealVariable("timer"));
//        
//        brakeType.AddMode(new Mode("Braking"));
//        brakeType.SetInitialMode(brakeType.FindMode("Braking"));
    }
    
    private static void FillFleshForBrakeType(SoftwareActorType brakeType, MessageHandler brakePercentPort)
    {
//        InstanceParameter controller = brakeType.FindInstanceParameter(Brake__controller_instance);
//        
//        RealVariable brake_percent = (RealVariable) brakeType.FindVariable("brake_percent");
//        RealVariable timer = (RealVariable) brakeType.FindVariable("timer");
//        
//        Mode brakingMode = brakeType.FindMode("Braking");
//        brakingMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(0.01f))));
//        brakingMode.SetGuard(CreateGuard(timer, "==", 0.01f));
//        
//        brakingMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
//        brakingMode.AddDifferentialEquation(new DifferentialEquation(brake_percent, UnknowExpression("?!!!")));
//        brakingMode.AddAction(CreateResetFor(timer)); 
//        brakingMode.AddAction(CreateSendStatement(controller, brakePercentPort, VariableExpression(brake_percent)));
    }

    private static void FillSkeletonForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, SoftwareActorType wheelControllerType)
    {
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance, wheelControllerType));
        
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_FR"));
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_FL"));
        globalBrakeControllerType.AddVariable(new FloatVariable("brake_percent"));
        globalBrakeControllerType.AddVariable(new FloatVariable("estimated_speed"));
        globalBrakeControllerType.AddVariable(new FloatVariable("global_torque"));
        
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FR_port, globalBrakeControllerType.FindVariable("wheel_rpm_FR"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FL_port, globalBrakeControllerType.FindVariable("wheel_rpm_FL"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__brake_percent_port, globalBrakeControllerType.FindVariable("brake_percent"));
        
        globalBrakeControllerType.AddMessageHandler(Global_Brake_Controller__control_handler, new MessageHandler(Message.MessageType.Control));
    }

    private static void FillFleshForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, MessageHandler wheelControllerApply)
    {
        InstanceParameter wheel_controller_FR = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance);
        InstanceParameter wheel_controller_FL = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance);

        
        FloatVariable global_torque = (FloatVariable) globalBrakeControllerType.FindVariable("global_torque");
        FloatVariable wheel_rpm_FR =  (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_FR");
        FloatVariable wheel_rpm_FL = (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_FL");

        FloatVariable brake_percent = (FloatVariable) globalBrakeControllerType.FindVariable("brake_percent");
        FloatVariable estimated_speed = (FloatVariable) globalBrakeControllerType.FindVariable("estimated_speed");
        
        MessageHandler control = globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__control_handler);
        
        control.AddStatement(new AssignmentStatement(estimated_speed, new VariableExpression(estimated_speed))); //?!!
        control.AddStatement(new AssignmentStatement(global_torque, new VariableExpression(brake_percent))); //?!!
        
        control.AddStatement(CreateSendStatement(wheel_controller_FR, wheelControllerApply, VariableExpression(global_torque), VariableExpression(estimated_speed)));
        control.AddStatement(CreateSendStatement(wheel_controller_FL, wheelControllerApply, VariableExpression(global_torque), VariableExpression(estimated_speed)));
    }
    
    private static void FillClockType(SoftwareActorType clockType)
    {
//        DelegationParameter callback = new DelegationParameter(Clock__callback);
//        clockType.AddDelegationParameter(callback);
//        
//        RealVariable timer = new RealVariable("timer");
//        
//        clockType.AddVariable(timer);
//        
//        Mode runningMode = new Mode("Running");
//        
//        runningMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(0.01f))));
//        runningMode.SetGuard(CreateGuard(timer, "==", 0.01f));
//                
//        runningMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
//        
//        runningMode.AddAction(CreateResetFor(timer));
//        runningMode.AddAction(CreateSendStatement(callback));
//        
//        
//        clockType.AddMode(runningMode);
//        clockType.SetInitialMode(runningMode);
    }
        
    private static void FillWheelActor(
            SoftwareActor wheel,
            SoftwareActor wheelController,
            SoftwareActor global_brake_controller,
            String delegationHandlerName)
    {
        
        BindInstance(wheel,Wheel__controller_instance, wheelController, CommunicationType.Wire );
        
        BindDelagation(wheel, Wheel__rpm_delegation, global_brake_controller, delegationHandlerName, CommunicationType.CAN);
    }
    
    private static void FillWheelControllerActor(SoftwareActor wheel_controller, SoftwareActor wheel)
    {
        BindInstance(wheel_controller, Wheel_Controller__wheel_instance, wheel, CommunicationType.Wire);        
    }

    private static void FillGlobalBrakeController(
            SoftwareActor global_brake_controller,
            SoftwareActor wheel_controller_FR,
            SoftwareActor wheel_controller_FL)
    {
        SoftwareActorType type = global_brake_controller.Type();
        
        BindInstance(
                global_brake_controller,
                Global_Brake_Controller__wheel_controller_FR_Instance,
                wheel_controller_FR,
                CommunicationType.CAN);
        
        BindInstance(
                global_brake_controller, 
                Global_Brake_Controller__wheel_controller_FL_Instance, 
                wheel_controller_FL, 
                CommunicationType.CAN);
        
        
    }

    private static void FillBrake(SoftwareActor brake, SoftwareActor global_brake_controller)
    {
        BindInstance(brake, Brake__controller_instance, global_brake_controller, CommunicationType.CAN);
    }

    private static void FillClock(SoftwareActor clock, SoftwareActor global_brake_controller, String controlHandler)
    {
        BindDelagation(clock, Clock__callback, global_brake_controller, controlHandler, CommunicationType.Wire);
    }
}
