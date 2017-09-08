/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorLocator;
import HPalang.Core.ActorLocators.DelegationActorLocator;
import HPalang.Core.ActorType;
import HPalang.Core.CommunicationType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DelegationParameter;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.*;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import static HPalang.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class BrakeByWireModel
{
    public static final float arbitrartDelay = 13.0f;
    public static final String Wheel__controller_instance = "controller";
    public static final String Wheel__rpm_delegation = "wheel_rpm_delegation";
    public static final String Wheel__torque_port = "torque_port";
    
    public static final String Wheel_Controller__wheel_instance = "wheel";
    public static final String Wheel_Controller__wheel_rmp_port = "wheel_rpm_port";
    public static final String Wheel_Controller__apply_torque_handler = "applyTorque";

    public static final String Global_Brake_Controller__wheel_rpm_FR_port = "wheel_rpm_FR_port";
    public static final String Global_Brake_Controller__wheel_rpm_FL_port = "wheel_rpm_FL_port";
    public static final String Global_Brake_Controller__wheel_rpm_RR_port = "wheel_rpm_RR_port";
    public static final String Global_Brake_Controller__wheel_rpm_RL_port = "wheel_rpm_RL_port";
    
    public static final String Global_Brake_Controller__brake_percent_port = "brake_percent_port";
    public static final String Global_Brake_Controller__control_handler = "control";
    public static final String Global_Brake_Controller__wheel_controller_FR_Instance = "wheel_controller_FR";
    public static final String Global_Brake_Controller__wheel_controller_FL_Instance = "wheel_controller_FL";
    public static final String Global_Brake_Controller__wheel_controller_RR_Instance = "wheel_controller_RR";
    public static final String Global_Brake_Controller__wheel_controller_RL_Instance = "wheel_controller_RL";
    
    public static final String Brake__controller_instance = "controller";
    
    public static final String Clock__callback = "callback";
    
   
    public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType wheelType = new PhysicalActorType("Wheel");
        SoftwareActorType wheelControllerType = new SoftwareActorType("WheelController");
        PhysicalActorType brakeType = new PhysicalActorType("Brake");
        SoftwareActorType globalBrakeControllerType = new SoftwareActorType("GlobalBrakeController");
        PhysicalActorType clockType = new PhysicalActorType("Clock");
        
        
        FillSkeletonForWheelType(wheelType, wheelControllerType);
        FillSkeletonForWheelControllerType(wheelControllerType, wheelType);
        FillSkeletonForBrakeType(brakeType, globalBrakeControllerType);
        FillSkeletonForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType);
        FillClockType(clockType);
        
        FillFleshForWheelType(wheelType, wheelControllerType.FindMessageHandler("wheel_rpm_port"));
        FillFleshForWheelControllerType(wheelControllerType, wheelType.FindMode("Break"), wheelType.FindMode("NoBrake"), wheelType.FindMessageHandler(Wheel__torque_port));
        FillFleshForBrakeType(brakeType, globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__brake_percent_port));
        FillFleshForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType.FindMessageHandler(Wheel_Controller__apply_torque_handler));
        
        PhysicalActor brake_pedal = new PhysicalActor("brake", brakeType);
        SoftwareActor global_brake_controller = new SoftwareActor("global_brake_controller", globalBrakeControllerType);
        
        SoftwareActor wheel_controller_FR = new SoftwareActor("wheel_controller_FR",wheelControllerType);     
        SoftwareActor wheel_controller_FL = new SoftwareActor("wheel_controller_FL",wheelControllerType);
        SoftwareActor wheel_controller_RR = new SoftwareActor("wheel_controller_RR", wheelControllerType);
        SoftwareActor wheel_controller_RL = new SoftwareActor("wheel_controller_RL", wheelControllerType);

        PhysicalActor wheel_FR = new PhysicalActor("wheel_FR", wheelType);       
        PhysicalActor wheel_FL = new PhysicalActor("wheel_FL", wheelType);
        PhysicalActor wheel_RR = new PhysicalActor("wheel_RR", wheelType);
        PhysicalActor wheel_RL = new PhysicalActor("wheel_RL", wheelType);
        
        PhysicalActor clock = new PhysicalActor("clock", clockType);
        
        FillWheelActor(wheel_FR,wheel_controller_FR,global_brake_controller,Global_Brake_Controller__wheel_rpm_FR_port);
        FillWheelActor(wheel_FL,wheel_controller_FL,global_brake_controller,Global_Brake_Controller__wheel_rpm_FL_port);
        FillWheelActor(wheel_RR,wheel_controller_RR,global_brake_controller,Global_Brake_Controller__wheel_rpm_RR_port);
        FillWheelActor(wheel_RL,wheel_controller_RL,global_brake_controller,Global_Brake_Controller__wheel_rpm_RL_port);
        
        FillWheelControllerActor(wheel_controller_FR, wheel_FR);
        FillWheelControllerActor(wheel_controller_FL, wheel_FL);
        FillWheelControllerActor(wheel_controller_RR, wheel_RR);
        FillWheelControllerActor(wheel_controller_RL, wheel_RL);
        
        FillGlobalBrakeController(
                global_brake_controller,
                wheel_controller_FR,
                wheel_controller_FL,
                wheel_controller_RR,
                wheel_controller_RL);
        
        FillBrake(brake_pedal, global_brake_controller);
        
        FillClock(clock, global_brake_controller, Global_Brake_Controller__control_handler);

        definition.AddType(wheelType);
        definition.AddType(wheelControllerType);
        definition.AddType(brakeType);
        definition.AddType(globalBrakeControllerType);
        
        definition.AddActor(brake_pedal);
        definition.AddActor(global_brake_controller);
        
        definition.AddActor(wheel_controller_FR);
        definition.AddActor(wheel_controller_FL);
        definition.AddActor(wheel_controller_RR);
        definition.AddActor(wheel_controller_RL);
        
        definition.AddActor(wheel_FR);
        definition.AddActor(wheel_FL);
        definition.AddActor(wheel_RR);
        definition.AddActor(wheel_RL);
        
        definition.AddActor(clock);
        
        definition.SetMainBlock(new MainBlock());
        
        SetNetworkPriority(wheel_FR, Wheel__torque_port, 100);
        SetNetworkPriority(wheel_FL, Wheel__torque_port, 101);
        SetNetworkPriority(wheel_RR, Wheel__torque_port, 102);
        SetNetworkPriority(wheel_RL, Wheel__torque_port, 103);

        //SetNetworkPriority(wheel_FR, ?!!!, 100); Mode Change
        
        SetNetworkPriority(wheel_controller_FR, Wheel__torque_port, 200);
        SetNetworkPriority(wheel_controller_FL, Wheel__torque_port, 201);
        SetNetworkPriority(wheel_controller_RR, Wheel__torque_port, 202);
        SetNetworkPriority(wheel_controller_RL, Wheel__torque_port, 203);
        
        SetNetworkPriority(wheel_controller_FR, Wheel_Controller__apply_torque_handler, 300);
        SetNetworkPriority(wheel_controller_FL, Wheel_Controller__apply_torque_handler, 301);
        SetNetworkPriority(wheel_controller_RR, Wheel_Controller__apply_torque_handler, 302);
        SetNetworkPriority(wheel_controller_RL, Wheel_Controller__apply_torque_handler, 303);
        
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__wheel_rpm_FR_port, 340);
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__wheel_rpm_FL_port, 401);
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__wheel_rpm_RR_port, 402);
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__wheel_rpm_RL_port, 403);
        
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__brake_percent_port, 503);
        SetNetworkPriority(global_brake_controller, Global_Brake_Controller__control_handler, 603);
        
//        SetNetworkDelay(wheel_FR, wheel_controller_FR, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_FL, wheel_controller_FL, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_RR, wheel_controller_RR, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
//        SetNetworkDelay(wheel_RL, wheel_controller_RL, Wheel_Controller__wheel_rmp_port, arbitrartDelay);
        
        SetNetworkDelay(wheel_FR, global_brake_controller, Global_Brake_Controller__wheel_rpm_FR_port, 2.0f);
        SetNetworkDelay(wheel_FL, global_brake_controller, Global_Brake_Controller__wheel_rpm_FL_port, 2.0f);
        SetNetworkDelay(wheel_RR, global_brake_controller, Global_Brake_Controller__wheel_rpm_RR_port, 2.0f);
        SetNetworkDelay(wheel_RL, global_brake_controller, Global_Brake_Controller__wheel_rpm_RL_port, 2.0f);
        
        SetNetworkDelay(brake_pedal, global_brake_controller, Global_Brake_Controller__brake_percent_port, 3.0f);
        
        SetNetworkDelay(global_brake_controller, wheel_controller_FR, Wheel_Controller__apply_torque_handler, 4.0f);
        SetNetworkDelay(global_brake_controller, wheel_controller_FL, Wheel_Controller__apply_torque_handler, 4.0f);
        SetNetworkDelay(global_brake_controller, wheel_controller_RR, Wheel_Controller__apply_torque_handler, 4.0f);
        SetNetworkDelay(global_brake_controller, wheel_controller_RL, Wheel_Controller__apply_torque_handler, 4.0f);
        
        return definition;
    }

    private void FillSkeletonForWheelType(PhysicalActorType wheelType, ActorType wheelControllerType)
    {
        wheelType.AddInstanceParameter(new InstanceParameter(Wheel__controller_instance, wheelControllerType));
        wheelType.AddDelegationParameter(new DelegationParameter(Wheel__rpm_delegation)); // parameters
                
        wheelType.AddVariable(new RealVariable("timer"));
        wheelType.AddVariable(new RealVariable("rpm"));
        wheelType.AddVariable(new RealVariable("torque"));

        wheelType.AddMode(new Mode("NoBrake"));
        wheelType.AddMode(new Mode("Break"));
        
        wheelType.SetInitialMode(wheelType.FindMode("NoBrake"));
        
        AddPort(wheelType, Wheel__torque_port, wheelType.FindVariable("torque"));
    }
    
    private void FillFleshForWheelType(PhysicalActorType wheelType, MessageHandler wheel_rpm_port)
    {
        InstanceParameter controllerInstance = wheelType.FindInstanceParameter(Wheel__controller_instance); 
        
        DelegationParameter wheel_rpm_delegation = wheelType.FindDelegationParameter(Wheel__rpm_delegation);
        
        RealVariable timer = (RealVariable) wheelType.FindVariable("timer"); 
        RealVariable rpm = (RealVariable) wheelType.FindVariable("rpm");
        RealVariable torque = (RealVariable) wheelType.FindVariable("torque");

        
        Mode noBrakeMode = wheelType.FindMode("NoBrake");
        Mode brakeMode = wheelType.FindMode("Break");
        
        noBrakeMode.SetInvarient("timer <= 0.01");
        noBrakeMode.SetGuard("timer == 0.01");
        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, "?!!!"));
        
        noBrakeMode.AddAction(ResetFor(timer));
        noBrakeMode.AddAction(CreateSendStatement(controllerInstance, wheel_rpm_port, rpm));
        noBrakeMode.AddAction(CreateSendStatement(wheel_rpm_delegation, rpm));
        
        brakeMode.SetInvarient("timer <= 0.01");
        brakeMode.SetGuard("timer == 0.01");
        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, "?!!!"));
        
        brakeMode.AddAction(ResetFor(timer));
        brakeMode.AddAction(CreateSendStatement(controllerInstance, wheel_rpm_port, rpm));
        brakeMode.AddAction(CreateSendStatement(wheel_rpm_delegation, rpm));
    }
    
    private void FillSkeletonForWheelControllerType(SoftwareActorType wheelControllerType, ActorType wheelType)
    {       
        wheelControllerType.AddInstanceParameter(new InstanceParameter(Wheel_Controller__wheel_instance, wheelType));

        wheelControllerType.AddVariable(new FloatVariable("wheel_rpm"));
        wheelControllerType.AddVariable(new FloatVariable("slip_rate"));
        
        MessageHandler applyTorque = new MessageHandler();
        
        AddParameter(applyTorque, "requested_torque", FloatVariable.class);
        AddParameter(applyTorque, "vehicle_speed", FloatVariable.class);
        wheelControllerType.AddMessageHandler(Wheel_Controller__apply_torque_handler, applyTorque);
        
        AddPort(wheelControllerType, Wheel_Controller__wheel_rmp_port, "wheel_rpm");        
    }
    
    private void FillFleshForWheelControllerType(SoftwareActorType wheelControllerType, Mode brakeMode, Mode noBrakeMode, MessageHandler wheel_torque_port)
    {
        InstanceParameter wheel = wheelControllerType.FindInstanceParameter(Wheel_Controller__wheel_instance);
        FloatVariable wheel_rpm = (FloatVariable) wheelControllerType.FindVariable("wheel_rpm");
        FloatVariable slip_rate = (FloatVariable) wheelControllerType.FindVariable("slip_rate");
        
        MessageHandler applyTorque = wheelControllerType.FindMessageHandler(Wheel_Controller__apply_torque_handler);
        FloatVariable requested_torque = (FloatVariable) applyTorque.FindVariableParameter("requested_torque").Variable();
        FloatVariable vehicle_speed = (FloatVariable) applyTorque.FindVariableParameter("vehicle_speed").Variable();
        
        applyTorque.AddStatement(new AssignmentStatement(slip_rate, new VariableExpression(vehicle_speed)));
        applyTorque.AddStatement(CreateSendStatement(wheel, wheel_torque_port , requested_torque));
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
                Statement.StatementsFrom(CreateModeChangeRequest(noBrakeMode, new ParametricActorLocator(wheel))), 
                Statement.StatementsFrom(CreateModeChangeRequest(brakeMode, new ParametricActorLocator(wheel)))
        ));
        
        AddPort(wheelControllerType, Wheel_Controller__wheel_rmp_port, wheel_rpm);
    }

    private void FillSkeletonForBrakeType(PhysicalActorType brakeType, SoftwareActorType globalBrakeControllerType)
    {
        brakeType.AddInstanceParameter(new InstanceParameter(Brake__controller_instance, globalBrakeControllerType));
        
        
        brakeType.AddVariable(new RealVariable("brake_percent"));        
        brakeType.AddVariable(new RealVariable("timer"));
        
        brakeType.AddMode(new Mode("Braking"));
        brakeType.SetInitialMode(brakeType.FindMode("Braking"));
    }
    
    private void FillFleshForBrakeType(PhysicalActorType brakeType, MessageHandler brakePercentPort)
    {
        InstanceParameter controller = brakeType.FindInstanceParameter(Brake__controller_instance);
        
        RealVariable brake_percent = (RealVariable) brakeType.FindVariable("brake_percent");
        RealVariable timer = (RealVariable) brakeType.FindVariable("timer");
        
        Mode brakingMode = brakeType.FindMode("Braking");
        brakingMode.SetGuard("timer <= 0.01");
        brakingMode.SetInvarient("timer == 0.01");
        
        brakingMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        brakingMode.AddDifferentialEquation(new DifferentialEquation(brake_percent, "?!!!"));
        brakingMode.AddAction(ResetFor(timer)); 
        brakingMode.AddAction(CreateSendStatement(controller, brakePercentPort, brake_percent));
    }

    private void FillSkeletonForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, SoftwareActorType wheelControllerType)
    {
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_RR_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_RL_Instance, wheelControllerType));
        
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_FR"));
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_FL"));
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_RR"));
        globalBrakeControllerType.AddVariable(new FloatVariable("wheel_rpm_RL"));
        globalBrakeControllerType.AddVariable(new FloatVariable("brake_percent"));
        globalBrakeControllerType.AddVariable(new FloatVariable("estimated_speed"));
        globalBrakeControllerType.AddVariable(new FloatVariable("global_torque"));
        
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FR_port, globalBrakeControllerType.FindVariable("wheel_rpm_FR"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FL_port, globalBrakeControllerType.FindVariable("wheel_rpm_FL"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_RR_port, globalBrakeControllerType.FindVariable("wheel_rpm_RR"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_RL_port, globalBrakeControllerType.FindVariable("wheel_rpm_RL"));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__brake_percent_port, globalBrakeControllerType.FindVariable("brake_percent"));
        
        globalBrakeControllerType.AddMessageHandler(Global_Brake_Controller__control_handler, new MessageHandler());
    }

    private void FillFleshForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, MessageHandler wheelControllerApply)
    {
        InstanceParameter wheel_controller_FR = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance);
        InstanceParameter wheel_controller_FL = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance);
        InstanceParameter wheel_controller_RR = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RR_Instance);
        InstanceParameter wheel_controller_RL = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RL_Instance);
        
        FloatVariable global_torque = (FloatVariable) globalBrakeControllerType.FindVariable("global_torque");
        FloatVariable wheel_rpm_FR =  (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_FR");
        FloatVariable wheel_rpm_FL = (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_FL");
        FloatVariable wheel_rpm_RR = (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_RR");
        FloatVariable wheel_rpm_RL = (FloatVariable) globalBrakeControllerType.FindVariable("wheel_rpm_RL");
        FloatVariable brake_percent = (FloatVariable) globalBrakeControllerType.FindVariable("brake_percent");
        FloatVariable estimated_speed = (FloatVariable) globalBrakeControllerType.FindVariable("estimated_speed");
        
        MessageHandler control = globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__control_handler);
        
        control.AddStatement(new AssignmentStatement(estimated_speed, new VariableExpression(estimated_speed))); //?!!
        control.AddStatement(new AssignmentStatement(global_torque, new VariableExpression(brake_percent))); //?!!
        
        control.AddStatement(CreateSendStatement(wheel_controller_FR, wheelControllerApply, global_torque, estimated_speed));
        control.AddStatement(CreateSendStatement(wheel_controller_FL, wheelControllerApply, global_torque, estimated_speed));
        control.AddStatement(CreateSendStatement(wheel_controller_RR, wheelControllerApply, global_torque, estimated_speed));
        control.AddStatement(CreateSendStatement(wheel_controller_RL, wheelControllerApply, global_torque, estimated_speed));
    }
    
    private void FillClockType(PhysicalActorType clockType)
    {
        DelegationParameter callback = new DelegationParameter(Clock__callback);
        clockType.AddDelegationParameter(callback);
        
        RealVariable timer = new RealVariable("timer");
        
        clockType.AddVariable(timer);
        
        Mode runningMode = new Mode("Running");
        
        runningMode.SetGuard("timer <= 0.01");
        runningMode.SetInvarient("timer == 0.01");
                
        runningMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        
        runningMode.AddAction(ResetFor(timer));
        runningMode.AddAction(CreateSendStatement(callback));
        
        
        clockType.AddMode(runningMode);
        clockType.SetInitialMode(runningMode);
    }
        
    private void FillWheelActor(
            PhysicalActor wheel,
            SoftwareActor wheelController,
            SoftwareActor global_brake_controller,
            String delegationHandlerName)
    {
        
        BindInstance(wheel,Wheel__controller_instance, wheelController, CommunicationType.Wire );
        
        BindDelagation(wheel, Wheel__rpm_delegation, global_brake_controller, delegationHandlerName, CommunicationType.CAN);
    }
    
    private void FillWheelControllerActor(SoftwareActor wheel_controller, PhysicalActor wheel)
    {
        BindInstance(wheel_controller, Wheel_Controller__wheel_instance, wheel, CommunicationType.Wire);        
    }

    private void FillGlobalBrakeController(
            SoftwareActor global_brake_controller,
            SoftwareActor wheel_controller_FR,
            SoftwareActor wheel_controller_FL, 
            SoftwareActor wheel_controller_RR,
            SoftwareActor wheel_controller_RL)
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
        
        BindInstance(
                global_brake_controller,
                Global_Brake_Controller__wheel_controller_RR_Instance,
                wheel_controller_RR,
                CommunicationType.CAN);
        
        BindInstance(
                global_brake_controller,
                Global_Brake_Controller__wheel_controller_RL_Instance,
                wheel_controller_RL,
                CommunicationType.CAN);
        
    }

    private void FillBrake(PhysicalActor brake, SoftwareActor global_brake_controller)
    {
        BindInstance(brake, Brake__controller_instance, global_brake_controller, CommunicationType.CAN);
    }

    private void FillClock(PhysicalActor clock, SoftwareActor global_brake_controller, String controlHandler)
    {
        BindDelagation(clock, Clock__callback, global_brake_controller, controlHandler, CommunicationType.Wire);
    }
}
