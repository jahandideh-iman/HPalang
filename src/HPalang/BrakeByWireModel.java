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
import HPalang.Core.InstanceParameter;
import HPalang.Core.MessageHandler;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class BrakeByWireModel
{
    public ProgramDefinition Create()
    {
        ProgramDefinition definition = new ProgramDefinition();
                
        PhysicalActor wheel_FR = CreateWheel("wheel_FR",null);
        PhysicalActor wheel_FL = CreateWheel("wheel_FL",null);
        PhysicalActor wheel_RR = CreateWheel("wheel_RR",null);
        PhysicalActor wheel_RL = CreateWheel("wheel_RL",null);
        
        SoftwareActor wheelController_FR = CreateWheelController("WheelController_FR");
        SoftwareActor wheelController_FL = CreateWheelController("wheelController_FL");
        SoftwareActor wheelController_RR = CreateWheelController("wheelController_RR");
        SoftwareActor wheelController_RL = CreateWheelController("wheelController_RL");
        
        PhysicalActor brake = CreateBrake();
        
        SoftwareActor globalBrakeController = CreateGlobalBrakeController();

        return definition;
    }
    
    private PhysicalActor CreateWheel(String name, SoftwareActorType controllerType)
    {
        PhysicalActor wheel = new PhysicalActor(name);
        
        InstanceParameter contollerInstance = new InstanceParameter("controller", controllerType);
        
        ContinuousVariable timer = new ContinuousVariable("timer");
        ContinuousVariable rpm = new ContinuousVariable("rpm");
        ContinuousVariable torque = new ContinuousVariable("torque");
        
        wheel.AddVariable(timer);      
        wheel.AddVariable(rpm);
        wheel.AddVariable(torque);

        Mode noBrakeMode = new Mode(
                "timer <= 0.01", 
                Mode.EquationsFrom(DifferentialEquation.Empty()) ,
                "timer == 0.01",
                Statement.EmptyStatements());
        
        Mode breakMode = new Mode(
                "timer <= 0.01", 
                Mode.EquationsFrom(DifferentialEquation.Empty()) ,
                "timer == 0.01",
                Statement.EmptyStatements());
        
        wheel.AddMode(noBrakeMode);
        wheel.AddMode(breakMode);
        
        return wheel;
    }

    private SoftwareActor CreateWheelController(String name)
    {
        SoftwareActor wheelController = new SoftwareActor(name, 5);
        
        DiscreteVariable wheel_rpm = new DiscreteVariable("wheel_rpm");
        DiscreteVariable vehicle_speed = new DiscreteVariable("vehicle_speed");
        DiscreteVariable requested_torque = new DiscreteVariable("requested_torque");
        
        wheelController.AddDiscreteVariable(wheel_rpm, 0);
        wheelController.AddDiscreteVariable(vehicle_speed, 0);
        wheelController.AddDiscreteVariable(requested_torque, 0);
        
        MessageHandler control = new MessageHandler();
        //control.AddStatement(statement);
        
        wheelController.AddMessageHandler("control", control);
        
        return wheelController;
    }

    private PhysicalActor CreateBrake()
    {
        PhysicalActor brake = new PhysicalActor("brake");
        
        ContinuousVariable brake_percent = new ContinuousVariable("brake_percent");
        ContinuousVariable timer = new ContinuousVariable("timer");

        brake.AddVariable(brake_percent);        
        brake.AddVariable(timer);
        
        Mode braking = new Mode(
                "timer <= 0.01", 
                Mode.EquationsFrom(DifferentialEquation.Empty()) ,
                "timer == 0.01",
                Statement.EmptyStatements());
        
        brake.AddMode(braking);

        return brake;
    }

    private SoftwareActor CreateGlobalBrakeController()
    {
        SoftwareActor globalBrakeController = new SoftwareActor("GlobalBrakeController", 5);
        
        DiscreteVariable global_torque = new DiscreteVariable("global_torque");
        DiscreteVariable wheel_rpm_FR = new DiscreteVariable("wheel_rpm_FR");
        DiscreteVariable wheel_rpm_FL = new DiscreteVariable("wheel_rpm_FL");
        DiscreteVariable wheel_rpm_RR = new DiscreteVariable("wheel_rpm_RR");
        DiscreteVariable wheel_rpm_RL = new DiscreteVariable("wheel_rpm_RL");
        DiscreteVariable estimated_speed = new DiscreteVariable("estimated_speed");

        globalBrakeController.AddDiscreteVariable(global_torque, 0);
        globalBrakeController.AddDiscreteVariable(wheel_rpm_FR, 0);
        globalBrakeController.AddDiscreteVariable(wheel_rpm_FL, 0);
        globalBrakeController.AddDiscreteVariable(wheel_rpm_RR, 0);
        globalBrakeController.AddDiscreteVariable(wheel_rpm_RL, 0);
        globalBrakeController.AddDiscreteVariable(estimated_speed, 0);
        
        MessageHandler controlHandler = new MessageHandler();
        
        globalBrakeController.AddMessageHandler("control", controlHandler);
        return globalBrakeController;
    }
}
