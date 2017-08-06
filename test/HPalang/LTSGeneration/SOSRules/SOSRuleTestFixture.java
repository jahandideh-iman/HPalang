/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.State;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleTestFixture
{
    protected LTSGenerator ltsGenerator = new LTSGenerator();
    protected LabeledTransitionSystem generatedLTS;
    protected GlobalRunTimeState globalState = new GlobalRunTimeState();
    
    protected void DequeueOneStatemenet(ActorRunTimeState actorState)
    {
        actorState.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
    }
    
    public ContinuousState CreateContinuousState(PhysicalActorState actorState)
    {
        ContinuousState continuousState = new ContinuousState();
        
        continuousState.AddPhysicalActorState(actorState);
        
        return continuousState;
    }
    
    public PhysicalActorState CreatePhysicalState(String actorName, State substate)
    {
        PhysicalActorState state =  new PhysicalActorState(new PhysicalActor(actorName));
        
        state.AddSubstate(substate);
        
        return state;
    }
    
        public PhysicalActorState CreatePhysicalState(String actorName)
    {
        PhysicalActorState state =  new PhysicalActorState(new PhysicalActor(actorName));

        return state;
    }
   
}
