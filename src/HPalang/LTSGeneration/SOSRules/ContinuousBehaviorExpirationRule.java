/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Mode;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import java.util.Collection;

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

    private boolean NoSoftwareActions(Collection<Transition> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof SoftwareLabel)
                return false;
        return true;
    }

    private boolean NoNetworkActions(Collection<Transition> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof NetworkLabel)
                return false;
        return true;
    }
    
    @Override
    protected void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        PhysicalActorState newActorState = newGlobalState.ContinuousState().FindActorState(actorState.Actor());
        
        Mode mode = newActorState.Mode();
        
        newActorState.ExecutionQueueState().Statements().Enqueue(mode.Actions());
        
        generator.AddTransition(new ContinuousLabel(mode.Guard()), newGlobalState);
    }   
}
