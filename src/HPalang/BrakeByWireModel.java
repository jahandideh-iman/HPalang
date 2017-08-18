/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.InstanceParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.ParametricActorLocator;
import HPalang.Core.ParametricReceiverLocator;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableParameter;

/**
 *
 * @author Iman Jahandideh
 */
public class BrakeByWireModel
{
    public ProgramDefinition Create()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
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
        FillFleshForGlobalBrakeControllerType(globalBrakeControllerType, wheelControllerType);
        //FillWheelControllerType(wheelControllerType);
        //FillWheelType(wheelType, wheelControllerType, wheelControllerType.FindMessageHandler("wheel_rpm"));
        

//        wheelControllerType.AddInstanceParameter("wheel", wheelType);
//        
//        brakeType.AddInstanceParameter("controller", globalBrakeControllerType);
//        
//        globalBrakeControllerType.AddInstanceParameter("wheel_FR", wheelControllerType);
//        globalBrakeControllerType.AddInstanceParameter("wheel_FL", wheelControllerType);
//        globalBrakeControllerType.AddInstanceParameter("wheel_RR", wheelControllerType);
//        globalBrakeControllerType.AddInstanceParameter("wheel_RL", wheelControllerType);
//        
//        
//        PhysicalActor wheel_FR = CreateWheel("wheel_FR",null);
//        PhysicalActor wheel_FL = CreateWheel("wheel_FL",null);
//        PhysicalActor wheel_RR = CreateWheel("wheel_RR",null);
//        PhysicalActor wheel_RL = CreateWheel("wheel_RL",null);
        
        return definition;
    }
    
    private void FillSkeletonForWheelType(PhysicalActorType wheelType, ActorType wheelControllerType)
    {
        wheelType.AddInstanceParameter(new InstanceParameter("controller", wheelControllerType));
                
        wheelType.AddVariable(new ContinuousVariable("timer"));
        wheelType.AddVariable(new ContinuousVariable("rpm"));
        wheelType.AddVariable(new ContinuousVariable("torque"));

        wheelType.AddMode(new Mode("NoBrake"));
        wheelType.AddMode(new Mode("Break"));
        
        wheelType.SetInitialMode(wheelType.FindMode("NoBrake"));
    }
    
    private void FillFleshForWheelType(PhysicalActorType wheelType, MessageHandler wheel_rpm_port)
    {
        InstanceParameter controllerInstance = wheelType.FindInstanceParameter("controller"); 
        
        ContinuousVariable timer = wheelType.FindVariable("timer"); 
        ContinuousVariable rpm = wheelType.FindVariable("rpm");
        ContinuousVariable torque = wheelType.FindVariable("torque");

        
        Mode noBrakeMode = wheelType.FindMode("NoBrake");
        Mode brakeMode = wheelType.FindMode("Break");
        
        noBrakeMode.SetInvarient("timer <= 0.01");
        noBrakeMode.SetGuard("timer == 0.01");
        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        noBrakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, "?!!!"));
        noBrakeMode.AddAction(new SendStatement(
                                new ParametricReceiverLocator(controllerInstance),
                                new NormalMessage(wheel_rpm_port))); // Add parameter
        
        brakeMode.SetInvarient("timer <= 0.01");
        brakeMode.SetGuard("timer == 0.01");
        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(rpm, "?!!!"));
        brakeMode.AddAction(new SendStatement(
                                new ParametricReceiverLocator(controllerInstance),
                                new NormalMessage(wheel_rpm_port))); // Add parameter
    }
    
    private void FillSkeletonForWheelControllerType(SoftwareActorType wheelControllerType, ActorType wheelType)
    {       
        wheelControllerType.AddInstanceParameter(new InstanceParameter("wheel", wheelType));

        wheelControllerType.AddVariable(new DiscreteVariable("wheel_rpm"));
        wheelControllerType.AddVariable(new DiscreteVariable("slip_rate"));
        
        MessageHandler applyTorque = new MessageHandler();
        applyTorque.AddParameter(new VariableParameter(new DiscreteVariable("requested_torque")));
        applyTorque.AddParameter(new VariableParameter(new DiscreteVariable("vehicle_speed")));
        wheelControllerType.AddMessageHandler("applyTorque", applyTorque);
        
        MessageHandler wheel_rpm_port = new MessageHandler();
        wheel_rpm_port.AddParameter(new VariableParameter(new DiscreteVariable("port_wheel_rpm")));
        wheelControllerType.AddMessageHandler("wheel_rpm_port",wheel_rpm_port);
        
    }
    
    private void FillFleshForWheelControllerType(SoftwareActorType wheelControllerType, Mode brakeMode, Mode noBrakeMode)
    {
        InstanceParameter wheel = wheelControllerType.FindInstanceParameter("wheel");
        DiscreteVariable wheel_rpm = wheelControllerType.FindVariable("wheel_rpm");
        DiscreteVariable slip_rate = wheelControllerType.FindVariable("slip_rate");
        
        MessageHandler applyTorque = wheelControllerType.FindMessageHandler("applyTorque");
        DiscreteVariable requested_torque = (DiscreteVariable) applyTorque.FindVariableParameter("requested_torque").Variable();
        DiscreteVariable vehicle_speed = (DiscreteVariable) applyTorque.FindVariableParameter("vehicle_speed").Variable();
        
        applyTorque.AddStatement(new DiscreteAssignmentStatement(slip_rate, new VariableExpression(vehicle_speed)));
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
                Statement.StatementsFrom(new ModeChangeStatement(new ParametricActorLocator(wheel), noBrakeMode )), 
                Statement.StatementsFrom(new ModeChangeStatement(new ParametricActorLocator(wheel), brakeMode ))
        ));
        
        MessageHandler wheel_rpm_port = wheelControllerType.FindMessageHandler("wheel_rpm_port");
        DiscreteVariable port_wheel_rpm = (DiscreteVariable) applyTorque.FindVariableParameter("port_wheel_rpm").Variable();
        wheel_rpm_port.AddStatement(new DiscreteAssignmentStatement(wheel_rpm, new VariableExpression(port_wheel_rpm)));
    }

    private void FillSkeletonForBrakeType(PhysicalActorType brakeType, SoftwareActorType globalBrakeControllerType)
    {
        brakeType.AddInstanceParameter(new InstanceParameter("controller", globalBrakeControllerType));
        
        brakeType.AddVariable(new ContinuousVariable("brake_percent"));        
        brakeType.AddVariable(new ContinuousVariable("timer"));
        
        brakeType.AddMode(new Mode("Braking"));
        brakeType.SetInitialMode(brakeType.FindMode("Braking"));
    }
    
    private void FillFleshForBrakeType(PhysicalActorType brakeType, MessageHandler brakePercentPort)
    {
        InstanceParameter controller = brakeType.FindInstanceParameter("controller");
        
        ContinuousVariable brake_percent = brakeType.FindVariable("brake_percent");
        ContinuousVariable timer = brakeType.FindVariable("timer");
        
        Mode brakingMode = brakeType.FindMode("Braking");
        brakingMode.SetGuard("timer <= 0.01");
        brakingMode.SetInvarient("timer == 0.01");
        
        brakingMode.AddDifferentialEquation(new DifferentialEquation(timer, "1"));
        brakingMode.AddDifferentialEquation(new DifferentialEquation(brake_percent, "?!!!"));
        brakingMode.AddAction(new SendStatement(
                                new ParametricReceiverLocator(controller),
                                new NormalMessage(brakePercentPort))); // AddParameter
    }

    private void FillSkeletonForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, SoftwareActorType wheelControllerType)
    {
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter("wheel_controller_FR", wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter("wheel_controller_FL", wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter("wheel_controller_RR", wheelControllerType));
        globalBrakeControllerType.AddInstanceParameter(new InstanceParameter("wheel_controller_RL", wheelControllerType));
        
        globalBrakeControllerType.AddVariable(new DiscreteVariable("wheel_rpm_FR"));
        globalBrakeControllerType.AddVariable(new DiscreteVariable("wheel_rpm_FL"));
        globalBrakeControllerType.AddVariable(new DiscreteVariable("wheel_rpm_RR"));
        globalBrakeControllerType.AddVariable(new DiscreteVariable("wheel_rpm_RL"));
        globalBrakeControllerType.AddVariable(new DiscreteVariable("brake_percent"));
        globalBrakeControllerType.AddVariable(new DiscreteVariable("estimated_speed"));
        
        CreatePort(globalBrakeControllerType, "wheel_rpm_FR_port", globalBrakeControllerType.FindVariable("wheel_rpm_FR"));
        CreatePort(globalBrakeControllerType, "wheel_rpm_FL_port", globalBrakeControllerType.FindVariable("wheel_rpm_FL"));
        CreatePort(globalBrakeControllerType, "wheel_rpm_RR_port", globalBrakeControllerType.FindVariable("wheel_rpm_RR"));
        CreatePort(globalBrakeControllerType, "wheel_rpm_RL_port", globalBrakeControllerType.FindVariable("wheel_rpm_RL"));
        
        globalBrakeControllerType.AddMessageHandler("Control" , new MessageHandler());
    }

    private void FillFleshForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType, SoftwareActorType wheelControllerType)
    {
        DiscreteVariable global_torque = new DiscreteVariable("global_torque");
        DiscreteVariable wheel_rpm_FR = new DiscreteVariable("wheel_rpm_FR");
        DiscreteVariable wheel_rpm_FL = new DiscreteVariable("wheel_rpm_FL");
        DiscreteVariable wheel_rpm_RR = new DiscreteVariable("wheel_rpm_RR");
        DiscreteVariable wheel_rpm_RL = new DiscreteVariable("wheel_rpm_RL");
        DiscreteVariable estimated_speed = new DiscreteVariable("estimated_speed");
        
        MessageHandler control = globalBrakeControllerType.FindMessageHandler("Control");
        
        
        
    }

    
    private void CreatePort(SoftwareActorType actorType, String portName, DiscreteVariable globalVariable)
    {
        MessageHandler port = new MessageHandler();
        DiscreteVariable localVariable =new DiscreteVariable("local_" + portName); 
        port.AddParameter(new VariableParameter(localVariable));
        port.AddStatement(new DiscreteAssignmentStatement(globalVariable, new VariableExpression(localVariable)));
        
        actorType.AddMessageHandler(portName,port);
    }


}
