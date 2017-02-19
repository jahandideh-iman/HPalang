/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
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
    public List<GlobalRunTimeState> FindTracesWhereExecutedActorStatementsAreExecuted(Actor executedActor, GlobalRunTimeState rootGlobalState)
    {
        LabeledTransitionSystem lts = tierTwoLTSGenerator.Generate(rootGlobalState);
        List<GlobalRunTimeState> states = new LinkedList<>();
        
        for(GlobalRunTimeState state : lts.GetStates())
        {
            boolean valid = true;
            for(ActorRunTimeState actorState : state.GetActorStates())
            {
                if(actorState.GetActor() == executedActor)
                {
                    if(actorState.StatementQueue().IsEmpty() == false)
                        valid = false;
                }
                else
                {
                    ActorRunTimeState rootActorState = rootGlobalState.FindActorState(actorState.GetActor());
                    if(actorState.StatementQueue().IsEmpty() == false || rootActorState.ValuationEqual(actorState) == false)
                        valid = false;
                }
                    
            }
            
            if(valid)
                states.add(state);
        }
        
        return states;
    }
    
}
