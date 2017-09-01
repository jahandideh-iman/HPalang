/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Label;
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
import static TestUtilities.CoreUtility.CreateGlobalState;
import java.util.Collections;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleTestFixture
{
    @Deprecated //Use transitionCollectorChecker
    protected LTSGenerator ltsGenerator = new LTSGenerator();
    protected LabeledTransitionSystem generatedLTS;
    protected GlobalRunTimeState globalState = CreateGlobalState();
    
    SOSRule rule;
    TransitionCollectorChecker transitionCollectorChecker = new TransitionCollectorChecker();
    


    public GlobalRunTimeState CollectedGlobalState()
    {
        return transitionCollectorChecker.collectedGlobalState;
    }
    
    public Label CollectedLabel()
    {
        return transitionCollectorChecker.collectedLabel;
    }
}
