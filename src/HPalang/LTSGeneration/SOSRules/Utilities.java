/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.Expression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.Core.TransitionSystem.Transition;
import java.util.Collection;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class Utilities
{
    public static boolean HasSoftwareActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        return ! NoSoftwareActions(transitions);
    }
    public static boolean NoSoftwareActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof SoftwareLabel)
                return false;
        return true;
    }

    public static boolean HasNetworkActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        return ! NoNetworkActions(transitions);
    }
        
    public static boolean NoNetworkActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof NetworkLabel)
                return false;
        return true;
    }
    
    static public void EnqueueStatements(Queue<Statement> statements, SoftwareActor actor, GlobalRunTimeState globalState)
    {
        FindActorState(actor, globalState).ExecutionQueueState().Statements().Enqueue(statements);
    }
    
    static public SoftwareActorState FindActorState(SoftwareActor actor, GlobalRunTimeState globalState )
    {
        return globalState.DiscreteState().FindActorState(actor);
    }
    
    static public BinaryExpression EqualityExpression(Expression oprand1, Expression oprand2)
    {
        return new BinaryExpression(oprand1, new EqualityOperator(), oprand2);
    }
    
    static public Expression PartivalValuation(Expression expression, SoftwareActor actor, GlobalRunTimeState globalState)
    {
        return expression.PartiallyEvaluate(FindActorState(actor, globalState).ValuationState().Valuation());
    }
}
