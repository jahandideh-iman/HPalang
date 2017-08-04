/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

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
   
}
