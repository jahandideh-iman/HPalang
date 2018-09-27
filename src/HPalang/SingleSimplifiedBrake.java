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
public class SingleSimplifiedBrake
{



    
    public static final String Brake__brake_percent = "brk";
    public static final String Brake__max_brake_percent = "mxbrk";
    public static final String Brake__timer = "t";
    public static final String Brake__braking = "braking";
    public static final String Brake__rate = "r";
    public static final float Brake__period_const = 0.05f;
    public static final float Brake__brake_rate_const = 1f;
    


    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType brakeType = new PhysicalActorType("Brake");

        FillSkeletonForBrakeType(brakeType);

        
       
        FillFleshForBrakeType(brakeType);
      
        
        PhysicalActor brake_pedal = new PhysicalActor("brake", brakeType, 1);
        


        
        definition.AddActor(brake_pedal);

        
        definition.SetMainBlock(new MainBlock());
         


        SingleCommunicationRealVariablePool globalPool = new SingleCommunicationRealVariablePool();

        
        definition.SetInitialGlobalVariablePool(globalPool);
        
        
      
        
        return definition;
    }
    

    private static void FillSkeletonForBrakeType(PhysicalActorType brakeType)
    {

          
        brakeType.AddVariable(new RealVariable(Brake__brake_percent));  
        brakeType.AddVariable(new FloatVariable(Brake__max_brake_percent));
        brakeType.AddVariable(new FloatVariable(Brake__rate));
        brakeType.AddVariable(new RealVariable(Brake__timer));
        
        brakeType.AddMode(new Mode(Brake__braking));    

        brakeType.SetInitialMode(brakeType.FindMode(Brake__braking));
    }
    
    private static void FillFleshForBrakeType(PhysicalActorType brakeType)
    {

        RealVariable brake_percent = (RealVariable) brakeType.FindVariable(Brake__brake_percent);
        FloatVariable max_brake_percent = (FloatVariable) brakeType.FindVariable(Brake__max_brake_percent);
        FloatVariable rate = (FloatVariable) brakeType.FindVariable(Brake__rate);
        RealVariable timer = (RealVariable) brakeType.FindVariable(Brake__timer);
        
        Mode braking = brakeType.FindMode(Brake__braking);
                
        braking.SetInvarient(new Invarient(
                CreateBinaryExpression(timer, "<=", Const(Brake__period_const))));
        
        braking.SetGuard(new Guard(
                CreateBinaryExpression(timer, "==", Const(Brake__period_const))));
        
        braking.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        braking.AddDifferentialEquation(new DifferentialEquation(brake_percent, VariableExpression(rate)));
        
        braking.AddAction(CreateModeChangeStatement(braking));
        braking.AddAction(new IfStatement(
                CreateBinaryExpression(brake_percent,">",VariableExpression(max_brake_percent)),
                Statement.StatementsFrom(new AssignmentStatement(rate, Const(0f))), 
                Statement.EmptyStatements()
        ));
        braking.AddAction(CreateResetFor(timer)); 
        
        
        
    }
}
