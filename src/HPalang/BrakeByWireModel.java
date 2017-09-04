/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.Delegation;
import HPalang.Core.DelegationParameter;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.ParametricActorLocator;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.Core.Statements.RequestModeChangeStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.Variable.Type;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class BrakeByWireModel
{
    public static final String Wheel__controller_instance = "controller";
    public static final String Wheel__rpm_delegation = "wheel_rpm_delegation";
    
    public static final String Wheel_Controller__wheel_instance = "wheel";
    
    public static final String Global_Brake_Controller__wheel_rpm_FR_port = "wheel_rpm_FR_port";
    public static final String Global_Brake_Controller__wheel_rpm_FL_port = "wheel_rpm_FL_port";
    public static final String Global_Brake_Controller__wheel_rpm_RR_port = "wheel_rpm_RR_port";
    public static final String Global_Brake_Controller__wheel_rpm_RL_port = "wheel_rpm_RL_port";
    public static final String Global_Brake_Controller__wheel_controller_FR_Instance = "wheel_controller_FR";
    public static final String Global_Brake_Controller__wheel_controller_FL_Instance = "wheel_controller_FL";
    public static final String Global_Brake_Controller__wheel_controller_RR_Instance = "wheel_controller_RR";
    public static final String Global_Brake_Controller__wheel_controller_RL_Instance = "wheel_controller_RL";
    
    public static final String Brake__controller_instance = "controller";
    
    public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType wheelType = new PhysicalActorType("Wheel");
        SoftwareActorType wheelControllerType = new SoftwareActorType("WheelController");
        PhysicalActorType brakeType = new PhysicalActorType("Brake");
        SoftwareActorType globalBrakeControllerType = new SoftwareActorType("GlobalBrakeController");
        
        
        FillSkeletonForWheelType(wheelType, wheelControllerType);
        FillSkeletonForWheelControllerType(wheelControllerType, wheelType);
        FillSkeletonForBrakeType(brakeType, globalBrakeControllerType);
        FillSkeletonForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType);
        
        FillFleshForWheelType(wheelType, wheelControllerType.FindMessageHandler("wheel_rpm_port"));
        FillFleshForWheelControllerType(wheelControllerType, wheelType.FindMode("Break"), wheelType.FindMode("NoBrake"));
        FillFleshForBrakeType(brakeType, globalBrakeControllerType.FindMessageHandler("brake_percent_port"));
        FillFleshForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType.FindMessageHandler("applyTorque"));
        
        PhysicalActor brake = new PhysicalActor("brake", brakeType);
        SoftwareActor global_brake_controller = new SoftwareActor("global_brake_controller", globalBrakeControllerType);
        
        SoftwareActor wheel_controller_FR = new SoftwareActor("wheel_controller_FR",wheelControllerType);     
        SoftwareActor wheel_controller_FL = new SoftwareActor("wheel_controller_FL",wheelControllerType);
        SoftwareActor wheel_controller_RR = new SoftwareActor("wheel_controller_RR", wheelControllerType);
        SoftwareActor wheel_controller_RL = new SoftwareActor("wheel_controller_RL", wheelControllerType);

        PhysicalActor wheel_FR = new PhysicalActor("wheel_FR", wheelType);       
        PhysicalActor wheel_FL = new PhysicalActor("wheel_FL", wheelType);
        PhysicalActor wheel_RR = new PhysicalActor("wheel_RR", wheelType);
        PhysicalActor wheel_RL = new PhysicalActor("wheel_RL", wheelType);
        
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
        
        FillBrake(brake, global_brake_controller);

        definition.AddType(wheelType);
        definition.AddType(wheelControllerType);
        definition.AddType(brakeType);
        definition.AddType(globalBrakeControllerType);
        
        definition.AddActor(brake);
        definition.AddActor(global_brake_controller);
        
        definition.AddActor(wheel_controller_FR);
        definition.AddActor(wheel_controller_FL);
        definition.AddActor(wheel_controller_RR);
        definition.AddActor(wheel_controller_RL);
        
        definition.AddActor(wheel_FR);
        definition.AddActor(wheel_FL);
        definition.AddActor(wheel_RR);
        definition.AddActor(wheel_RL);
        
        return definition;
    }

    private void FillSkeletonForWheelType(PhysicalActorType wheelType, ActorType wheelControllerType)
    {
        wheelType.AddInstanceParameter(new InstanceParameter(Wheel__controller_instance, wheelControllerType));
        
        wheelType.AddDelegationParameter(new DelegationParameter(Wheel__rpm_delegation)); // parameters
                
        wheelType.AddVariable(new ContinuousVariable("timer"));
        wheelType.AddVariable(new ContinuousVariable("rpm"));
        wheelType.AddVariable(new ContinuousVariable("torque"));

        wheelType.AddMode(new Mode("NoBrake"));
        wheelType.AddMode(new Mode("Break"));
        
        wheelType.SetInitialMode(wheelType.FindMode("NoBrake"));
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
        noBrakeMode.AddAction(new SendStatement(
                                new ParametricActorLocator(controllerInstance),
                                new NormalMessage(wheel_rpm_port))); // Add parameter
        noBrakeMode.AddAction(new SendStatement(
                                new ParametricActorLocator(wheel_rpm_delegation.InstanceParameter()),
                                new NormalMessage(wheel_rpm_delegation.MessageHandler()))); // Add parameter
        
        brakeMode.SetInvarient("timer <= 0.01");
        brakeMode.SetGuard("timer == 0.01");
        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, "?!!!"));
        brakeMode.AddAction(new SendStatement(
                                new ParametricActorLocator(controllerInstance),
                                new NormalMessage(wheel_rpm_port))); // Add parameter
        noBrakeMode.AddAction(new SendStatement(
                                new ParametricActorLocator(wheel_rpm_delegation.InstanceParameter()),
                                new NormalMessage(wheel_rpm_delegation.MessageHandler()))); // Add parameter
    }
    
    private void FillSkeletonForWheelControllerType(SoftwareActorType wheelControllerType, ActorType wheelType)
    {       
        wheelControllerType.AddInstanceParameter(new InstanceParameter(Wheel_Controller__wheel_instance, wheelType));

        wheelControllerType.AddVariable(new FloatVariable("wheel_rpm"));
        wheelControllerType.AddVariable(new FloatVariable("slip_rate"));
        
        MessageHandler applyTorque = new MessageHandler();
        applyTorque.AddParameter(new VariableParameter(new IntegerVariable("requested_torque")));
        applyTorque.AddParameter(new VariableParameter(new IntegerVariable("vehicle_speed")));
        wheelControllerType.AddMessageHandler("applyTorque", applyTorque);
        
        MessageHandler wheel_rpm_port = new MessageHandler();
        wheel_rpm_port.AddParameter(new VariableParameter(new IntegerVariable("port_wheel_rpm")));
        wheelControllerType.AddMessageHandler("wheel_rpm_port",wheel_rpm_port);
        
    }
    
    private void FillFleshForWheelControllerType(SoftwareActorType wheelControllerType, Mode brakeMode, Mode noBrakeMode)
    {
        InstanceParameter wheel = wheelControllerType.FindInstanceParameter(Wheel_Controller__wheel_instance);
        FloatVariable wheel_rpm = (FloatVariable) wheelControllerType.FindVariable("wheel_rpm");
        FloatVariable slip_rate = (FloatVariable) wheelControllerType.FindVariable("slip_rate");
        
        MessageHandler applyTorque = wheelControllerType.FindMessageHandler("applyTorque");
        IntegerVariable requested_torque = (IntegerVariable) applyTorque.FindVariableParameter("requested_torque").Variable();
        IntegerVariable vehicle_speed = (IntegerVariable) applyTorque.FindVariableParameter("vehicle_speed").Variable();
        
        applyTorque.AddStatement(new AssignmentStatement(slip_rate, new VariableExpression(vehicle_speed)));
        applyTorque.AddStatement(new IfStatement(
                 new LogicalExpression(
                        new ComparisonExpression(
                                new VariableExpression(slip_rate),
                                ComparisonExpression.Operator.Greater,
                                new ConstantDiscreteExpression(2)),
                        LogicalExpression.Operator.OR,
                        new ComparisonExpression(
                                new VariableExpression(requested_torque),
                                ComparisonExpression.Operator.Equal,
                                new ConstantDiscreteExpression(2))),
                Statement.StatementsFrom(new RequestModeChangeStatement(new ParametricActorLocator(wheel), noBrakeMode )), 
                Statement.StatementsFrom(new RequestModeChangeStatement(new ParametricActorLocator(wheel), brakeMode ))
        ));
        
        MessageHandler wheel_rpm_port = wheelControllerType.FindMessageHandler("wheel_rpm_port");
        IntegerVariable port_wheel_rpm = (IntegerVariable) applyTorque.FindVariableParameter("port_wheel_rpm").Variable();
        wheel_rpm_port.AddStatement(new AssignmentStatement(wheel_rpm, new VariableExpression(port_wheel_rpm)));
    }

    private void FillSkeletonForBrakeType(PhysicalActorType brakeType, SoftwareActorType globalBrakeControllerType)
    {
        brakeType.AddInstanceParameter(new InstanceParameter(Brake__controller_instance, globalBrakeControllerType));
        
        brakeType.AddVariable(new ContinuousVariable("brake_percent"));        
        brakeType.AddVariable(new ContinuousVariable("timer"));
        
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
        brakingMode.AddAction(new SendStatement(
                                new ParametricActorLocator(controller),
                                new NormalMessage(brakePercentPort))); // AddParameter
    }

    private void FillSkeletonForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, SoftwareActorType wheelControllerType)
    {
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_RR_Instance, wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter(Global_Brake_Controller__wheel_controller_RL_Instance, wheelControllerType));
        
        globalBrakeControllerType.AddVariable(new IntegerVariable("wheel_rpm_FR"));
        globalBrakeControllerType.AddVariable(new IntegerVariable("wheel_rpm_FL"));
        globalBrakeControllerType.AddVariable(new IntegerVariable("wheel_rpm_RR"));
        globalBrakeControllerType.AddVariable(new IntegerVariable("wheel_rpm_RL"));
        globalBrakeControllerType.AddVariable(new IntegerVariable("brake_percent"));
        globalBrakeControllerType.AddVariable(new IntegerVariable("estimated_speed"));
        
        CreatePort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FR_port, globalBrakeControllerType.FindVariable("wheel_rpm_FR"));
        CreatePort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FL_port, globalBrakeControllerType.FindVariable("wheel_rpm_FL"));
        CreatePort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_RR_port, globalBrakeControllerType.FindVariable("wheel_rpm_RR"));
        CreatePort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_RL_port, globalBrakeControllerType.FindVariable("wheel_rpm_RL"));
        CreatePort(globalBrakeControllerType, "brake_percent_port", globalBrakeControllerType.FindVariable("brake_percent"));
        
        globalBrakeControllerType.AddMessageHandler("Control" , new MessageHandler());
    }

    private void FillFleshForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, MessageHandler wheelControllerApply)
    {
        InstanceParameter wheel_controller_FR = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance);
        InstanceParameter wheel_controller_FL = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance);
        InstanceParameter wheel_controller_RR = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RR_Instance);
        InstanceParameter wheel_controller_RL = globalBrakeControllerType.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RL_Instance);
        
        IntegerVariable global_torque = new IntegerVariable("global_torque");
        IntegerVariable wheel_rpm_FR = new IntegerVariable("wheel_rpm_FR");
        IntegerVariable wheel_rpm_FL = new IntegerVariable("wheel_rpm_FL");
        IntegerVariable wheel_rpm_RR = new IntegerVariable("wheel_rpm_RR");
        IntegerVariable wheel_rpm_RL = new IntegerVariable("wheel_rpm_RL");
        IntegerVariable brake_percent = new IntegerVariable("brake_percent");
        IntegerVariable estimated_speed = new IntegerVariable("estimated_speed");
        
        MessageHandler control = globalBrakeControllerType.FindMessageHandler("Control");
        
        control.AddStatement(new DiscreteAssignmentStatement(estimated_speed, new VariableExpression(estimated_speed))); //?!!
        control.AddStatement(new DiscreteAssignmentStatement(global_torque, new VariableExpression(brake_percent))); //?!!
        
        control.AddStatement(new SendStatement(new ParametricActorLocator(wheel_controller_FR), new NormalMessage(wheelControllerApply ))); // Put Parameters 
        control.AddStatement(new SendStatement(new ParametricActorLocator(wheel_controller_FL), new NormalMessage(wheelControllerApply ))); // Put Parameters 
        control.AddStatement(new SendStatement(new ParametricActorLocator(wheel_controller_RR), new NormalMessage(wheelControllerApply ))); // Put Parameters 
        control.AddStatement(new SendStatement(new ParametricActorLocator(wheel_controller_RL), new NormalMessage(wheelControllerApply ))); // Put Parameters 
        
    }
    
    private void CreatePort(SoftwareActorType actorType, String portName, Variable globalVariable)
    {
        MessageHandler port = new MessageHandler();
        IntegerVariable localVariable =new IntegerVariable("local_" + portName); 
        port.AddParameter(new VariableParameter(localVariable));
        port.AddStatement(new AssignmentStatement(globalVariable, new VariableExpression(localVariable)));
        
        actorType.AddMessageHandler(portName,port);
    }

        
    private void FillWheelActor(
            PhysicalActor wheel,
            SoftwareActor wheelController,
            SoftwareActor global_brake_controller,
            String delegationName)
    {
        wheel.BindInstance(wheel.Type().FindInstanceParameter(Wheel__controller_instance),wheelController);
            wheel.BindDelegation(wheel.Type().FindDelegationParameter(Wheel__controller_instance),
                    new Delegation(
                            global_brake_controller,
                            global_brake_controller.Type().FindMessageHandler(delegationName)));
    }
    
    private void FillWheelControllerActor(SoftwareActor wheel_controller, PhysicalActor wheel)
    {
        wheel_controller.BindInstance(wheel_controller.Type().FindInstanceParameter(Wheel_Controller__wheel_instance), wheel);
        
    }

    private void FillGlobalBrakeController(
            SoftwareActor global_brake_controller,
            SoftwareActor wheel_controller_FR,
            SoftwareActor wheel_controller_FL, 
            SoftwareActor wheel_controller_RR,
            SoftwareActor wheel_controller_RL)
    {
        SoftwareActorType type = global_brake_controller.Type();
        global_brake_controller.BindInstance(
                type.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FR_Instance)
                , wheel_controller_FR);
        global_brake_controller.BindInstance(
                type.FindInstanceParameter(Global_Brake_Controller__wheel_controller_FL_Instance),
                 wheel_controller_FL);
        global_brake_controller.BindInstance(
                type.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RR_Instance),
                 wheel_controller_RR);
        global_brake_controller.BindInstance(
                type.FindInstanceParameter(Global_Brake_Controller__wheel_controller_RL_Instance),
                 wheel_controller_RL);
    }

    private void FillBrake(PhysicalActor brake, SoftwareActor global_brake_controller)
    {
        brake.BindInstance(brake.Type().FindInstanceParameter(Brake__controller_instance), global_brake_controller);
    }
}
