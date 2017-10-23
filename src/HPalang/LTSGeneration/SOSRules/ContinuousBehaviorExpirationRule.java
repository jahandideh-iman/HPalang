/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.ExpressionScopeUnwrapper;
import HPalang.Core.Mode;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import static HPalang.LTSGeneration.SOSRules.Utilities.NoNetworkActions;
import static HPalang.LTSGeneration.SOSRules.Utilities.NoSoftwareActions;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorExpirationRule extends PhysicalActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(PhysicalActorState actorState, StateInfo globalStateInfo)
    {
        return !actorState.Mode().equals(Mode.None()) && 
                actorState.ExecutionQueueState().Statements().IsEmpty() &&
                NoSoftwareActions(globalStateInfo.Outs()) &&
                NoNetworkActions(globalStateInfo.Outs());
    }

    @Override
    protected void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        PhysicalActorState newActorState = newGlobalState.ContinuousState().FindActorState(actorState.PActor());
        
        Mode mode = newActorState.Mode();
        
        newActorState.ExecutionQueueState().Statements().Enqueue(mode.Actions());
        
        collector.AddTransition(new ContinuousLabel(UnscopedGuard(mode.Guard(),actorState.Actor())), newGlobalState);
    }  
    
    private Guard UnscopedGuard(Guard guard, Actor owner)
    {
        ExpressionScopeUnwrapper unwrapper = new ExpressionScopeUnwrapper();
        return new Guard(unwrapper.Unwrap(guard.Expression(), owner.Name()));
    }
}
