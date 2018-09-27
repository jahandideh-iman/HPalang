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
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class TwoWheelsModel
{
    public static final boolean ADD_TIME_PROPERTY_MONITOR = false;
    public static final boolean ADD_SAFETY_PROPERTY_MONITOR = true;
    
    public static final float arbitrartDelay = 13.0f;
    public static final String Wheel__torque_port = "trqprt";
    public static final String Wheel__speed = "spd";
    public static final String Wheel__torque = "trq";
    public static final String Wheel__timer = "t";
    public static final String Wheel__brake_mode = "Brake";
    public static final float Wheel__period_const = 0.05f;
    



    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType wheelType = new PhysicalActorType("Wheel");
        
        FillSkeletonForWheelType(wheelType);

        FillFleshForWheelType(wheelType);
      

        PhysicalActor wheel_FR = new PhysicalActor("wFR", wheelType,1);       
        PhysicalActor wheel_FL = new PhysicalActor("wFL", wheelType,1); 
        
        definition.AddActor(wheel_FR);
        definition.AddActor(wheel_FL);

        
        definition.SetMainBlock(new MainBlock());
         
        SingleCommunicationRealVariablePool globalPool = new SingleCommunicationRealVariablePool();
         
        Reserve(globalPool,wheel_FR, Wheel__torque_port,1);
        Reserve(globalPool,wheel_FL, Wheel__torque_port,1);

        definition.SetInitialGlobalVariablePool(globalPool);
        

        return definition;
    }
    

    private static void FillSkeletonForWheelType(PhysicalActorType wheelType)
    {
               
        wheelType.AddVariable(new RealVariable(Wheel__timer));
        wheelType.AddVariable(new RealVariable(Wheel__speed));
        wheelType.AddVariable(new FloatVariable(Wheel__torque));

        wheelType.AddMode(new Mode(Wheel__brake_mode));
        
        wheelType.SetInitialMode(wheelType.FindMode(Wheel__brake_mode));
        
        AddPort(wheelType, Wheel__torque_port, wheelType.FindVariable(Wheel__torque));
    }
    
    private static void FillFleshForWheelType(PhysicalActorType wheelType)
    {

        RealVariable timer = (RealVariable) wheelType.FindVariable(Wheel__timer); 
        RealVariable speed = (RealVariable) wheelType.FindVariable(Wheel__speed);
        FloatVariable torque = (FloatVariable) wheelType.FindVariable(Wheel__torque);

        
        Mode brakeMode = wheelType.FindMode(Wheel__brake_mode);
        
 
        
        brakeMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(Wheel__period_const))));
        brakeMode.SetGuard(CreateGuard(timer, "==", Wheel__period_const));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(speed, CreateBinaryExpression(Const(-0.1f),"-", ExpressionFrom(torque))));
        
        brakeMode.AddAction(CreateResetFor(timer));
        //brakeMode.AddAction(CreateModeChangeStatement(brakeMode));
        brakeMode.AddAction(new IfStatement(
            CreateBinaryExpression(speed, ">=", Const(0f)),
            Statement.StatementsFrom(CreateModeChangeStatement(brakeMode)), 
            Statement.EmptyStatements()));
    }
    
}
