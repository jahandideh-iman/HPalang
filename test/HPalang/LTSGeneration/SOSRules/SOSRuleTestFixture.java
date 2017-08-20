/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.State;
import HPalang.LTSGeneration.StateInfo;
import Mocks.TransitionCollectorMock;
import static TestUtilities.Utilities.CreateGlobalState;
import java.util.Collections;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleTestFixture
{
    protected LTSGenerator ltsGenerator = new LTSGenerator();
    protected LabeledTransitionSystem generatedLTS;
    protected GlobalRunTimeState globalState = CreateGlobalState();
    
    SOSRule rule;
    TransitionCollectorMock transitionCollectorChecker = new TransitionCollectorMock();
    
    protected void DequeueOneStatemenet(SoftwareActorState actorState)
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
        
    public StateInfo SingleStateInfo(GlobalRunTimeState globalState)
    {
        return new StateInfo(globalState, Collections.EMPTY_LIST , Collections.EMPTY_LIST);
    }
   
}
