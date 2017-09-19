/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.MultiplyOperator;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Mode;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import static HPalang.ModelCreationUtilities.*;

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
        
        PhysicalActor ball = new PhysicalActor("ball", ballType, 10);
        
        definition.AddActor(ball);
        
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
        
        falling.SetInvarient(String.format("%s >= 0", y.Name()));
        falling.AddDifferentialEquation(new DifferentialEquation(v, "-9.8"));
        falling.AddDifferentialEquation(new DifferentialEquation(y, v.Name()));
        
        falling.SetGuard(CreateGuard(y, "==", 0));
        falling.AddAction(
                new AssignmentStatement(
                        v, 
                        new BinaryExpression(new VariableExpression(v), new MultiplyOperator() , new ConstantContinuousExpression(0.5f))));
    }
}