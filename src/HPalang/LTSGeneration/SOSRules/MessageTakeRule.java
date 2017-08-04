/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Message;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.SOSRules.TierOne.TierTwoLTSGeneratorHanlder;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.Trace;
import HPalang.LTSGeneration.Transition;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class MessageTakeRule extends ActorLevelRule
{
    protected final TierTwoLTSGeneratorHanlder tierTwoHanlder;
    
    public MessageTakeRule(LTSGenerator tierTwoGenerator)
    {
        this.tierTwoHanlder = new TierTwoLTSGeneratorHanlder(tierTwoGenerator);
    }
    
    protected abstract boolean InternalIsRuleSatisfied(ActorRunTimeState actorState);
    protected abstract Message DequeuMessage(ActorRunTimeState actorState);
    
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return InternalIsRuleSatisfied(actorState) == true 
                && actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == true
                && AllActorsHaveNoPendingStatement(globalState) == true;
    }
    
    private boolean AllActorsHaveNoPendingStatement(GlobalRunTimeState globalState)
    {
        for(ActorRunTimeState actorState : globalState.GetActorStates())
            if(actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == false)
                return false;
        return true;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        newActorState.FindSubState(ExecutionQueueState.class).Statements().Enqueue(DequeuMessage(newActorState).GetMessageBody());
        
        List<Trace> traces = tierTwoHanlder.FindTracesWhereExecutedActorStatementsAreExecuted(actorState.GetActor(), newGlobalState);

        for(Trace trace : traces)
        {
            SoftwareLabel label = GetLabelFor(trace);
            generator.AddTransition(label, trace.GetLastState());
        }
    }
    
    private SoftwareLabel GetLabelFor(Trace trace)
    {
        Map<ContinuousVariable, Reset> resets = new HashMap<>();
        
        for(Transition tr : trace.GetTransitions())
        {
            for(Reset re : (Set<Reset>)tr.GetLabel().GetResets())
                resets.put(re.Variable(), re);
        }
        
        return new SoftwareLabel(new LinkedHashSet<>(resets.values()));
    }
   
}
