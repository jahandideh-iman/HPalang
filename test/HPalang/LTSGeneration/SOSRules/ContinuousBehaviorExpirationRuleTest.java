/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ModeBuilder;
import HPalang.LTSGeneration.Builders.PhysicalActorStateBuilder;
import Builders.StateInfoBuilder;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;
import static TestUtilities.CoreUtility.CreateGlobalState;
import static TestUtilities.CoreUtility.CreatePhysicalActor;
import static TestUtilities.CoreUtility.CreateTransition;
import static TestUtilities.CoreUtility.SimpleStateInfo;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorExpirationRuleTest extends SOSRuleTestFixture
{    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ContinuousBehaviorExpirationRule());
        rule = new ContinuousBehaviorExpirationRule();
    }
    
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesTheActivePhysicalActors()
    {
        Mode mode = new ModeBuilder().Build();
        PhysicalActor pActor = CreatePhysicalActor("pActor",mode);
        
        PhysicalActorState physicalActorState = new PhysicalActorStateBuilder().WithActor(pActor).WithMode(mode).
                Build();

        globalState = CreateGlobalState();
        globalState.ContinuousState().AddPhysicalActorState(physicalActorState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        PhysicalActorState nextActorState = nextGlobalState.ContinuousState().FindActorState(pActor);
        nextActorState.ExecutionQueueState().Statements().Enqueue(mode.Actions());
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);  
        
        transitionCollectorChecker.ExpectTransition(new ContinuousLabel(mode.Guard()),nextGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void DoesNotExpiresActivePhysicalActorsIfThereIsSoftwareAction()
    {
        SetupGlobalStateWithOneActivePhyiscalActor();
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(CreateTransition(globalState, new SoftwareLabel(), globalState)).
                Build();
        
        ApplyAndVerifyRuleOn(stateInfoWithSoftwareTransition);
        //rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(stateInfoWithSoftwareTransition);
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsNetworkAction()
    {
        SetupGlobalStateWithOneActivePhyiscalActor();
        
        StateInfo stateWithNetworkTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(CreateTransition(globalState, new NetworkLabel(), globalState)).
                Build();
        
        ApplyAndVerifyRuleOn(stateWithNetworkTransition);
        //rule.TryApply(stateWithNetworkTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(stateWithNetworkTransition);
    }
    
    private void SetupGlobalStateWithOneActivePhyiscalActor()
    {
        Mode mode = new ModeBuilder().Build();
        PhysicalActor pActor = CreatePhysicalActor("pActor",mode);
        
        PhysicalActorState physicalActorState = new PhysicalActorStateBuilder().WithActor(pActor).WithMode(mode).
                Build();
        
        globalState = CreateGlobalState();
        globalState.ContinuousState().AddPhysicalActorState(physicalActorState);
    }
}
