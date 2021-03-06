/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.MultiplyOperator;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.MainBlock;
import HPalang.Core.Mode;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Variables.RealVariable;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class BouncingBallModel
{
    static final String Ball__y = "y";
    static final String Ball__v = "v";
    static final String Ball__falling_mode = "Falling";
            
    static public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType ballType = new PhysicalActorType("Ball");
        
        FillSkeletonForBallType(ballType);
        FillFleshForBallType(ballType);
        
        PhysicalActor ball1 = new PhysicalActor("ball1", ballType, 10);
        PhysicalActor ball2 = new PhysicalActor("ball2", ballType, 10);
        
        definition.AddActor(ball1);
        definition.AddActor(ball2);
        
        definition.SetMainBlock(new MainBlock());

        return definition;
    }

    private static void FillSkeletonForBallType(PhysicalActorType ballType)
    {
        AddVariable(ballType, new RealVariable(Ball__v));
        AddVariable(ballType, new RealVariable(Ball__y));
        
        ballType.AddMode(new Mode(Ball__falling_mode));
        
        ballType.SetInitialMode(ballType.FindMode(Ball__falling_mode));
    }

    private static void FillFleshForBallType(PhysicalActorType ballType)
    {
        RealVariable v = (RealVariable) ballType.FindVariable(Ball__v);
        RealVariable y = (RealVariable) ballType.FindVariable(Ball__y);
        Mode falling = ballType.FindMode(Ball__falling_mode);
        
        falling.SetInvarient(new Invarient(CreateBinaryExpression(y, ">=", Const(0f))));
        falling.AddDifferentialEquation(new DifferentialEquation(v, Const(-9.8f)));
        falling.AddDifferentialEquation(new DifferentialEquation(y, ExpressionFrom(v)));
        
        falling.SetGuard(CreateGuard(y, "==", 0));
        falling.AddAction(
                new AssignmentStatement(
                        v, 
                        new BinaryExpression(new VariableExpression(v), new MultiplyOperator() , new ConstantContinuousExpression(0.5f))));
    }
}