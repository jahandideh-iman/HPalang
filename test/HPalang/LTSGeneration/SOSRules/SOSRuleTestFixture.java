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
import HPalang.LTSGeneration.TransitionCollector;
import Mocks.TransitionCollectorChecker;
import Mocks.TransitionCollectorMock;
import TestUtilities.CoreUtility;
import static TestUtilities.CoreUtility.CreateGlobalState;
import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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
    
    protected SOSRule rule;
    protected TransitionCollectorChecker transitionCollectorChecker = new TransitionCollectorChecker();
    


    public GlobalRunTimeState CollectedGlobalState()
    {
        return transitionCollectorChecker.CollectedGlobalStateAt(0);
    }
    
    public Label CollectedLabel()
    {
        return transitionCollectorChecker.CollectedLabelAt(0);
    }
    
    public void VerifyEqualOutputForMultipleApply(StateInfo stateInfo)
    {
        GlobalRunTimeState originalCopy =  stateInfo.State().DeepCopy();
        
        rule.TryApply(stateInfo, new TransitionCollectorMock());
        
        assertThat(stateInfo.State(), equalTo(originalCopy));

    }
    
    public void ApplyAndVerifyRuleOn(GlobalRunTimeState globalState, TransitionCollector collector)
    {
        ApplyAndVerifyRuleOn(CoreUtility.SimpleStateInfo(globalState), collector);
    }
    
    public void ApplyAndVerifyRuleOn(StateInfo stateInfo, TransitionCollector collector)
    {
        GlobalRunTimeState orignalState = stateInfo.State().DeepCopy();
        
        rule.TryApply(stateInfo, collector);
        
        assertThat(" The initial global state is modified. ",stateInfo.State(), equalTo(orignalState));
    }
    
    public void ApplyRuleOn(GlobalRunTimeState globalState)
    {
        ApplyAndVerifyRuleOn(CoreUtility.SimpleStateInfo(globalState));
    }
    
    public void ApplyAndVerifyRuleOn(StateInfo stateInfo)
    {
        ApplyAndVerifyRuleOn(stateInfo, transitionCollectorChecker);
    }
   
}
