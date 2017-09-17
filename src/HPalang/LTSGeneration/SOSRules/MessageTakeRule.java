/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Message;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.SOSRules.TierOne.TierTwoLTSGeneratorHanlder;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.Trace;
import HPalang.LTSGeneration.Transition;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class MessageTakeRule extends SoftwareActorLevelRule
{
    protected final TierTwoLTSGeneratorHanlder tierTwoHanlder;
    
    public MessageTakeRule(LTSGenerator tierTwoGenerator)
    {
        this.tierTwoHanlder = new TierTwoLTSGeneratorHanlder(tierTwoGenerator);
    }
    
    protected abstract boolean InternalIsRuleSatisfied(SoftwareActorState actorState);
    protected abstract Message DequeuMessage(SoftwareActorState actorState);
    
    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        return InternalIsRuleSatisfied(actorState) == true 
                && actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == true
                && AllActorsHaveNoPendingStatement(globalState) == true;
    }
    
    private boolean AllActorsHaveNoPendingStatement(GlobalRunTimeState globalState)
    {
        for(SoftwareActorState actorState : globalState.DiscreteState().ActorStates())
            if(actorState.ExecutionQueueState().Statements().IsEmpty() == false)
                return false;
        return true;
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.SActor());
        
        newActorState.ExecutionQueueState().Statements().Enqueue(DequeuMessage(newActorState).GetMessageBody());
        
        List<Trace> traces = tierTwoHanlder.FindTracesWhereExecutedActorStatementsAreExecuted(actorState.SActor(), newGlobalState);

        for(Trace trace : traces)
        {
            SoftwareLabel label = GetLabelFor(trace);
            collector.AddTransition(label, trace.GetLastState());
        }
    }
    
    private SoftwareLabel GetLabelFor(Trace trace)
    {
        Map<Variable, Reset> resets = new HashMap<>();
        
        for(Transition tr : trace.GetTransitions())
        {
            for(Reset re : (Set<Reset>)tr.GetLabel().Resets())
                resets.put(re.Variable(), re);
        }
        
        return new SoftwareLabel(new LinkedHashSet<>(resets.values()));
    }
   
}
