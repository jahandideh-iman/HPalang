/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ModeBuilder;
import Builders.PhysicalActorStateBuilder;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import Mocks.EmptyStatement;
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
    }
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesTheActivePhysicalActors()
    {
        PhysicalActor pActor = new PhysicalActor("pActor");
        Mode mode = new ModeBuilder().Build();
        pActor.AddMode(mode);
        
        PhysicalActorState physicalActorState = new PhysicalActorStateBuilder().
                With(pActor).
                With(new ExecutionQueueState()).
                With(mode).
                Build();

        globalState = new GlobalRunTimeState();
        globalState.AddSubstate(CreateContinuousState(physicalActorState));
        
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        PhysicalActorState nextActorState = nextGlobalState.ContinuousState().FindActorState(pActor);
        nextActorState.ExecutionQueueState().Statements().Enqueue(mode.Actions());

        assertTrue(generatedLTS.HasTransition(globalState, new ContinuousLabel(mode.Guard()), nextGlobalState));
    }
}
