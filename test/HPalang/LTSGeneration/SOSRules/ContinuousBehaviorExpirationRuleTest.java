/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ModeBuilder;
import Builders.PhysicalActorStateBuilder;
import Builders.StateInfoBuilder;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.EventsState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import Mocks.ActionMonitor;
import static TestUtilities.Utilities.CreateGlobalState;
import static TestUtilities.Utilities.CreatePhysicalActor;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        
        PhysicalActorState physicalActorState = new PhysicalActorStateBuilder().
                With(pActor).
                With(mode).
                Build();

        globalState = CreateGlobalState();
        globalState.ContinuousState().AddPhysicalActorState(physicalActorState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        PhysicalActorState nextActorState = nextGlobalState.ContinuousState().FindActorState(pActor);
        nextActorState.ExecutionQueueState().Statements().Enqueue(mode.Actions());
        
        transitionCollectorChecker.ExpectTransition(new ContinuousLabel(mode.Guard()),nextGlobalState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);  
    }
    
    @Test
    public void DoesNotExpiresActivePhysicalActorsIfThereIsSoftwareAction()
    {
        SetupGlobalStateWithOneActivePhyiscalActor();
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new SoftwareLabel(), globalState)).
                Build();
           
        transitionCollectorChecker.ExpectNoTransition();
        
        rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsNetworkAction()
    {
        SetupGlobalStateWithOneActivePhyiscalActor();
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new NetworkLabel(), globalState)).
                Build();
           
        transitionCollectorChecker.ExpectNoTransition();
        
        rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
        
    }
    
    private void SetupGlobalStateWithOneActivePhyiscalActor()
    {
        Mode mode = new ModeBuilder().Build();
        PhysicalActor pActor = CreatePhysicalActor("pActor",mode);
        
        PhysicalActorState physicalActorState = new PhysicalActorStateBuilder().
                With(pActor).
                With(mode).
                Build();
        
        globalState = CreateGlobalState();
        globalState.ContinuousState().AddPhysicalActorState(physicalActorState);
    }
}
