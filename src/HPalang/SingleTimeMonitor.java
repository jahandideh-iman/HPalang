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
public class SingleTimeMonitor
{

 
    
    public static final String Time_Monitor__time = "time";
    public static final String Time_Monitor__rate = "rate";
    public static final String Time_Monitor__stop = "stop";    
    public static final String Time_Monitor__start = "start";
    public static final String Time_Monitor__monitoring = "Monitoring";


   
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
       
        
        definition.SetMainBlock(new MainBlock());
         

        definition.SetEventSystemVariablePoolSize(1);
        
        SingleCommunicationRealVariablePool globalPool = new SingleCommunicationRealVariablePool();
         

        definition.SetInitialGlobalVariablePool(globalPool);
        
        if(true)
        {
            PhysicalActorType timeMonitorType = new PhysicalActorType("TimeMonitor");
            FillTimeMonitorType(timeMonitorType);
            
            PhysicalActor timeMonitor = new PhysicalActor("timeMonitor", timeMonitorType, 1);
            
            //InstanceParameter brakeParam = new InstanceParameter("timeMonitor", timeMonitorType);
            //brakeType.AddInstanceParameter(brakeParam);
            //Mode brakeIncreasingMode = brakeType.FindMode(Brake__increasing_brake);
           // Mode brakeConstantMode = brakeType.FindMode(Brake__constant_brake);
            
            //brakeIncreasingMode.AddAction(
                   // CreateSendStatement(brakeParam, timeMonitorType.FindMessageHandler(Time_Monitor__start)));
            //brakeConstantMode.AddAction(
                    //CreateSendStatement(brakeParam, timeMonitorType.FindMessageHandler(Time_Monitor__start)));
            
            //InstanceParameter wheelParam = new InstanceParameter("timeMonitor", timeMonitorType);
            //wheelType.AddInstanceParameter(wheelParam);
            //MessageHandler wheelSetTorqueHandler = wheelType.FindMessageHandler(Wheel__torque_port);
            //wheelSetTorqueHandler.AddStatement(CreateSendStatement(wheelParam, timeMonitorType.FindMessageHandler(Time_Monitor__stop)));
            
            //brake_pedal.BindInstance(brakeParam, timeMonitor, CommunicationType.Wire);
            //wheel_FR.BindInstance(wheelParam, timeMonitor, CommunicationType.Wire);
            
            definition.AddActor(timeMonitor);
            
        }
        
        return definition;
    }
    

    private static void FillTimeMonitorType(PhysicalActorType timeMonitorType)
    {
        RealVariable time = new RealVariable(Time_Monitor__time);
        FloatVariable rate = new FloatVariable(Time_Monitor__rate);
        
        timeMonitorType.AddVariable(time);
        timeMonitorType.AddVariable(rate);
        
        MessageHandler startHandler =  new MessageHandler(Message.MessageType.Control);
        MessageHandler stopHandler = new MessageHandler(Message.MessageType.Control);
        
        startHandler.AddStatement(new AssignmentStatement(rate, Const(1f)));
        startHandler.AddStatement(new AssignmentStatement(time, Const(0f)));
        
        stopHandler.AddStatement(new AssignmentStatement(rate, Const(0f)));
        
        timeMonitorType.AddMessageHandler(Time_Monitor__start, startHandler);
        timeMonitorType.AddMessageHandler(Time_Monitor__stop, stopHandler);
        
        Mode monitoringMode = new Mode(Time_Monitor__monitoring);
        
        monitoringMode.SetGuard(new Guard(new FalseConst()));
        monitoringMode.SetInvarient(new Invarient(new TrueConst()));
        monitoringMode.AddDifferentialEquation(new DifferentialEquation(time, VariableExpression(rate)));
        
        timeMonitorType.AddMode(monitoringMode);
        timeMonitorType.SetInitialMode(monitoringMode);
        
    }
}
