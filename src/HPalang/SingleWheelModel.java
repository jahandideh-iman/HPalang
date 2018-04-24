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
public class SingleWheelModel
{
    public static final boolean ADD_TIME_PROPERTY_MONITOR = false;
    public static final float arbitrartDelay = 13.0f;
    public static final String Wheel__speed = "speed";
    public static final String Wheel__timer = "timer";
    public static final String Wheel__brake_mode = "Brake";
    public static final float Wheel__period_const = 0.05f;
    

   
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType wheelType = new PhysicalActorType("Wheel");

        
        
        FillSkeletonForWheelType(wheelType);
        FillFleshForWheelType(wheelType);


        PhysicalActor wheel_FR = new PhysicalActor("wheel_FR", wheelType,1);       



        definition.AddActor(wheel_FR);
        

        
        definition.SetMainBlock(new MainBlock());
         

        definition.SetEventSystemVariablePoolSize(0);
        
        SingleCommunicationRealVariablePool globalPool = new SingleCommunicationRealVariablePool();
         

        definition.SetInitialGlobalVariablePool(globalPool);
        

        
        return definition;
    }
    

    private static void FillSkeletonForWheelType(PhysicalActorType wheelType)
    {      
        wheelType.AddVariable(new RealVariable(Wheel__timer));
        wheelType.AddVariable(new RealVariable(Wheel__speed));

        wheelType.AddMode(new Mode(Wheel__brake_mode));
        
        wheelType.SetInitialMode(wheelType.FindMode(Wheel__brake_mode));
        
    }
    
    private static void FillFleshForWheelType(PhysicalActorType wheelType)
    {
       
        RealVariable timer = (RealVariable) wheelType.FindVariable(Wheel__timer); 
        RealVariable speed = (RealVariable) wheelType.FindVariable(Wheel__speed);
        Mode brakeMode = wheelType.FindMode(Wheel__brake_mode);
        brakeMode.SetInvarient(new Invarient(CreateBinaryExpression(timer, "<=", Const(Wheel__period_const))));
        brakeMode.SetGuard(CreateGuard(timer, "==", Wheel__period_const));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        //brakeMode.AddDifferentialEquation(new DifferentialEquation(speed, CreateBinaryExpression(Const(-0.1f),"-", ExpressionFrom(torque))));
        brakeMode.AddDifferentialEquation(new DifferentialEquation(speed, Const(-0.1f)));
        
        brakeMode.AddAction(CreateResetFor(timer));
        brakeMode.AddAction(new IfStatement(
                CreateBinaryExpression(speed, ">=", Const(0f)),
                Statement.StatementsFrom(CreateModeChangeStatement(brakeMode)), 
                Statement.EmptyStatements()));
    }
 
}
