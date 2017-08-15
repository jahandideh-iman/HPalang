/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import HPalang.LTSGeneration.Trace;
import HPalang.LTSGeneration.Transition;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class TierTwoLTSGeneratorHanlder
{
    private final LTSGenerator tierTwoLTSGenerator;
    
    public TierTwoLTSGeneratorHanlder(LTSGenerator tierTwoGenerator)
    {
        this.tierTwoLTSGenerator = tierTwoGenerator;
    }
    
    // TODO: Refactor and rename this Crap!
    public List<Trace> FindTracesWhereExecutedActorStatementsAreExecuted(SoftwareActor executedActor, GlobalRunTimeState rootGlobalState)
    {
        LabeledTransitionSystem lts = tierTwoLTSGenerator.Generate(rootGlobalState);
        
        List<Trace> validTraces = new LinkedList<>();
        List<Trace> allTraces = GetTerminalTracesFrom(rootGlobalState,lts);
        
        for(Trace trace : allTraces)
        {
            GlobalRunTimeState lastState = trace.GetLastState();
            boolean valid = true;
            for (ActorRunTimeState actorState : lastState.GetActorStates()) 
            {
                if (actorState.GetActor() == executedActor) 
                {
                    if (actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == false)
                        valid = false;    
                } 
                else 
                {
                    ActorRunTimeState rootActorState = rootGlobalState.FindActorState(actorState.GetActor());
                    if (actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == false || 
                            rootActorState.FindSubState(ValuationState.class)
                                    .equals(actorState.FindSubState(ValuationState.class)) == false)
                        valid = false;
                }
            }
            if(valid)
                validTraces.add(trace);
        }
        
        return validTraces;
    }
    
    
    private List<Trace> GetTerminalTracesFrom(GlobalRunTimeState state , LabeledTransitionSystem ts)
    {
        List<Trace> traces = new LinkedList<>();
        
        
        List<Transition> outTrans =  ts.GetOutTransitionsFor(state);
        
        if(outTrans.isEmpty())
            traces.add(new Trace(state));
        else
        {
            for(Transition tr : outTrans)
            {
                List<Trace> outTraces = GetTerminalTracesFrom(tr.GetDestination(), ts);
                for(Trace outTrace : outTraces)
                {
                    Trace trace = new Trace(outTrace);
                    trace.InsertTransition(tr,0);
                    trace.InsertState(state,0);
                    traces.add(trace);
                }
            }
        }
     
        return traces;
    }
    
}
