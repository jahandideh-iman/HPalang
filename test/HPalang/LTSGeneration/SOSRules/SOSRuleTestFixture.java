/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
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
import Mocks.TransitionCollectorChecker;
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
    TransitionCollectorChecker transitionCollectorChecker = new TransitionCollectorChecker();
    
    public void ClearStatementsFor(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        globalState.DiscreteState().FindActorState(actor).ExecutionQueueState().Statements().Clear();
    }
    
    protected void DequeueOneStatemenet(SoftwareActorState actorState)
    {
        actorState.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
    }

}
