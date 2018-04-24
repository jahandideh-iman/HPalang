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
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MessageHandler;
import HPalang.Core.Mode;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Variable;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.RealVariable;
import static HPalang.Core.ModelCreationUtilities.*;
import HPalang.Core.SingleCommunicationRealVariablePool;
import HPalang.LTSGeneration.Labels.Guard;

/**
 *
 * @author Iman Jahandideh
 */
public class SingleBrakeAndBrakeControllerAndClock
{

    
    public static final String Global_Brake_Controller__wheel_rpm_FR_port = "wheel_speed_FR_port";

    
    public static final String Global_Brake_Controller__brake_percent_port = "brake_percent_port";
    public static final String Global_Brake_Controller__control_handler = "control";
    public static final String Global_Brake_Controller__wheel_controller_FR_Instance = "wheel_controller_FR";
    public static final String Global_Brake_Controller__wheel_speed_FR = "wheel_speed_FR";
    public static final String Global_Brake_Controller__brake_percent = "brake_percent";
    public static final String Global_Brake_Controller__estimated_speed = "estimated_speed";
    public static final String Global_Brake_Controller__global_torque = "global_torque";

    
    public static final String Brake__controller_instance = "controller";
    public static final String Brake__brake_percent = "brake_percent";
    public static final String Brake__timer = "timer";
    public static final String Brake__increasing_brake = "IncreasingBrake";
    public static final String Brake__constant_brake = "ConstantBrake";
    public static final float Brake__period_const = 0.1f;
    public static final float Brake__brake_rate_const = 1f;
    
    public static final String Clock__callback = "callback";
    public static final float Clock__period_const = 0.1f;
    
    public static final float CAN__dealy_const = 0.01f;
    



   
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType brakeType = new PhysicalActorType("Brake");
        SoftwareActorType globalBrakeControllerType = new SoftwareActorType("GlobalBrakeController");
        PhysicalActorType clockType = new PhysicalActorType("Clock");
        
        
        FillSkeletonForBrakeType(brakeType, globalBrakeControllerType);
        FillSkeletonForGlobalBrakeControllerType(globalBrakeControllerType);
        FillClockType(clockType);
        
        FillFleshForBrakeType(brakeType, globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__brake_percent_port));
        FillFleshForGlobalBrakeControllerType(globalBrakeControllerType);
        
        PhysicalActor brake_pedal = new PhysicalActor("brake", brakeType, 1);
        SoftwareActor global_brake_controller = new SoftwareActor("global_brake_controller", globalBrakeControllerType, 1);

        
        PhysicalActor clock = new PhysicalActor("clock", clockType,1);
        

        
        FillBrake(brake_pedal, global_brake_controller);
        
        FillClock(clock, global_brake_controller, Global_Brake_Controller__control_handler);

        definition.AddType(brakeType);
        definition.AddType(globalBrakeControllerType);
        definition.AddType(clockType);
        
        definition.AddActor(brake_pedal);
        definition.AddActor(global_brake_controller);
        
   
        
        definition.AddActor(clock);
        
        definition.SetMainBlock(new MainBlock());
         
        
        definition.SetEventSystemVariablePoolSize(0);
        
        SingleCommunicationRealVariablePool globalPool = new SingleCommunicationRealVariablePool();
         

        Reserve(globalPool, global_brake_controller, Global_Brake_Controller__brake_percent_port, 1);
        
        definition.SetInitialGlobalVariablePool(globalPool);
        
        return definition;
    }
    

    private static void FillSkeletonForBrakeType(PhysicalActorType brakeType, SoftwareActorType globalBrakeControllerType)
    {
        brakeType.AddInstanceParameter(new InstanceParameter(Brake__controller_instance, globalBrakeControllerType));
        
        
        brakeType.AddVariable(new RealVariable(Brake__brake_percent));        
        brakeType.AddVariable(new RealVariable(Brake__timer));
        
        brakeType.AddMode(new Mode(Brake__increasing_brake));    
        brakeType.AddMode(new Mode(Brake__constant_brake));

        brakeType.SetInitialMode(brakeType.FindMode(Brake__increasing_brake));
    }
    
    private static void FillFleshForBrakeType(PhysicalActorType brakeType, MessageHandler brakePercentPort)
    {
        InstanceParameter controller = brakeType.FindInstanceParameter(Brake__controller_instance);
        
        RealVariable brake_percent = (RealVariable) brakeType.FindVariable(Brake__brake_percent);
        RealVariable timer = (RealVariable) brakeType.FindVariable(Brake__timer);
        
        Mode increasingBrakeMode = brakeType.FindMode(Brake__increasing_brake);
        Mode constantBrakeMode = brakeType.FindMode(Brake__constant_brake);
                
        increasingBrakeMode.SetInvarient(new Invarient(
                CreateBinaryExpression(
                        CreateBinaryExpression(timer, "<=", Const(Brake__period_const)),
                        "&&",
                        CreateBinaryExpression(brake_percent, "<=", Const(100f)) )));
        
        increasingBrakeMode.SetGuard(new Guard(CreateBinaryExpression(
                        CreateBinaryExpression(timer, "==", Const(Brake__period_const)),
                        "||",
                        CreateBinaryExpression(brake_percent, "==", Const(100f)))));
        
        increasingBrakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        increasingBrakeMode.AddDifferentialEquation(new DifferentialEquation(brake_percent, Const(Brake__brake_rate_const)));
        increasingBrakeMode.AddAction(CreateResetFor(timer)); 
        increasingBrakeMode.AddAction(CreateSendStatement(controller, brakePercentPort, VariableExpression(brake_percent)));
        increasingBrakeMode.AddAction(new IfStatement(
                CreateBinaryExpression(brake_percent,"<",Const(100)),
                Statement.StatementsFrom(CreateModeChangeStatement(increasingBrakeMode)), 
                Statement.StatementsFrom(CreateModeChangeStatement(constantBrakeMode))
        ));
        

        constantBrakeMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(Brake__period_const))));
        constantBrakeMode.SetGuard(CreateGuard(timer, "==", Brake__period_const));
        
        constantBrakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        constantBrakeMode.AddDifferentialEquation(new DifferentialEquation(brake_percent, Const(0)));
        constantBrakeMode.AddAction(CreateResetFor(timer)); 
        constantBrakeMode.AddAction(CreateSendStatement(controller, brakePercentPort, VariableExpression(brake_percent)));
        constantBrakeMode.AddAction(CreateModeChangeStatement(constantBrakeMode));
    }

    private static void FillSkeletonForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType)
    {
        
        globalBrakeControllerType.AddVariable(new FloatVariable(Global_Brake_Controller__wheel_speed_FR));
        globalBrakeControllerType.AddVariable(new FloatVariable(Global_Brake_Controller__brake_percent));
        globalBrakeControllerType.AddVariable(new FloatVariable(Global_Brake_Controller__estimated_speed));
        globalBrakeControllerType.AddVariable(new FloatVariable(Global_Brake_Controller__global_torque));
        
        AddPort(globalBrakeControllerType, Global_Brake_Controller__wheel_rpm_FR_port, globalBrakeControllerType.FindVariable(Global_Brake_Controller__wheel_speed_FR));
        AddPort(globalBrakeControllerType, Global_Brake_Controller__brake_percent_port, globalBrakeControllerType.FindVariable(Global_Brake_Controller__brake_percent));
        
        globalBrakeControllerType.AddMessageHandler(Global_Brake_Controller__control_handler, new MessageHandler(Message.MessageType.Control));
    }

    private static void FillFleshForGlobalBrakeControllerType(SoftwareActorType globalBrakeControllerType)
    {

        FloatVariable global_torque = (FloatVariable) globalBrakeControllerType.FindVariable(Global_Brake_Controller__global_torque);
        FloatVariable wheel_speed_FR =  (FloatVariable) globalBrakeControllerType.FindVariable(Global_Brake_Controller__wheel_speed_FR);

        FloatVariable brake_percent = (FloatVariable) globalBrakeControllerType.FindVariable(Global_Brake_Controller__brake_percent);
        FloatVariable estimated_speed = (FloatVariable) globalBrakeControllerType.FindVariable(Global_Brake_Controller__estimated_speed);
        
        MessageHandler control = globalBrakeControllerType.FindMessageHandler(Global_Brake_Controller__control_handler);
        
        control.AddStatement(new AssignmentStatement(
                estimated_speed, VariableExpression(wheel_speed_FR))); 
        
        control.AddStatement(new AssignmentStatement(global_torque, new VariableExpression(brake_percent)));
        
        
        //control.AddStatement(CreateSendStatement(wheel_controller_FR, wheelControllerApply, VariableExpression(global_torque), VariableExpression(estimated_speed)));
    }
    
    private static void FillClockType(PhysicalActorType clockType)
    {
        DelegationParameter callback = new DelegationParameter(Clock__callback);
        clockType.AddDelegationParameter(callback);
        
        RealVariable timer = new RealVariable("timer");
        
        clockType.AddVariable(timer);
        
        Mode runningMode = new Mode("Running");
        
        runningMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(Clock__period_const))));
        runningMode.SetGuard(CreateGuard(timer, "==", Clock__period_const));
                
        runningMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        
        runningMode.AddAction(CreateResetFor(timer));
        runningMode.AddAction(CreateSendStatement(callback));
        runningMode.AddAction(CreateModeChangeStatement(runningMode));
        
        
        clockType.AddMode(runningMode);
        clockType.SetInitialMode(runningMode);
    }
        




    private static void FillBrake(PhysicalActor brake, SoftwareActor global_brake_controller)
    {
        BindInstance(brake, Brake__controller_instance, global_brake_controller, CommunicationType.Wire);
    }

    private static void FillClock(PhysicalActor clock, SoftwareActor global_brake_controller, String controlHandler)
    {
        BindDelagation(clock, Clock__callback, global_brake_controller, controlHandler, CommunicationType.Wire);
    }

}
