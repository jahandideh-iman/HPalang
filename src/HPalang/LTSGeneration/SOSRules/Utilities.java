/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.ActorType;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.Expression;
import HPalang.Core.MessageHandler;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.ExpressionScopeUnwrapper;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
    
    public static boolean HasPhysicalActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        return ! NoPhysicalActions(transitions);
    }
    
    public static boolean NoPhysicalActions(Collection<Transition<GlobalRunTimeState>> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof ContinuousLabel)
                return false;
        return true;
    }
        
    static public void EnqueueStatements(Queue<Statement> statements, Actor actor, GlobalRunTimeState globalState)
    {
        FindActorState(actor, globalState).ExecutionQueueState().Statements().Enqueue(statements);
    }
    
    static public ActorState FindActorState(Actor actor, GlobalRunTimeState globalState )
    {
        return globalState.FindActorState(actor);
    }
    
    static public SoftwareActorState FindActorState(SoftwareActor actor, GlobalRunTimeState globalState )
    {
        return globalState.DiscreteState().FindActorState(actor);
    }
    
    static public BinaryExpression EqualityExpression(Expression oprand1, Expression oprand2)
    {
        return new BinaryExpression(oprand1, new EqualityOperator(), oprand2);
    }
    
    static public Expression PartivalValuation(Expression expression, Actor actor, GlobalRunTimeState globalState)
    {
        return expression.PartiallyEvaluate(FindActorState(actor, globalState).ValuationState().Valuation());
    }
    
    static public Reset UnWrapResetScope(Reset reset, Actor actor, ValuationContainer valuation)
    {
        Variable unWrappedVariable = UnWrapVariableScope(reset.Variable(), actor);
        Expression unWrappedExpression = UnWrapExpressionScope(reset.Expression(), actor, valuation);
        return new Reset(unWrappedVariable, unWrappedExpression);
    }
    
    static public Variable UnWrapVariableScope(Variable variable, Actor actor)
    {
        ExpressionScopeUnwrapper scopeUnwrapper = new ExpressionScopeUnwrapper();
        String actorName = actor.Name();
        
        Variable unWrappedVariable = scopeUnwrapper.Unwrap(
                variable, 
                actorName,
                ActorVariablesPlusParameters(actor.Type()));
        
        return unWrappedVariable;
    }
    
    static public Expression UnWrapExpressionScope(Expression expression, Actor actor, ValuationContainer valuation)
    {
        ExpressionScopeUnwrapper scopeUnwrapper = new ExpressionScopeUnwrapper();
        String actorName = actor.Name();
        
        Expression unWrappedExpression = scopeUnwrapper.Unwrap(
                                    expression.PartiallyEvaluate(valuation),
                                    actorName,
                                    ActorVariablesPlusParameters(actor.Type())
                                    );
        return unWrappedExpression;
    }
    
    static public Collection<Variable> ActorVariablesPlusParameters(ActorType actorType)
    {
        List<Variable> allVariables = new LinkedList<>();
        allVariables.addAll(actorType.Variables());
        
        for(MessageHandler m : actorType.MessageHandlers())
            for(VariableParameter p : m.Parameters().AsList())
                allVariables.add(p.Variable());
        
        return allVariables;
    }
}
