/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.Messages.MessageWithBody;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import TestUtilities.Utilities;
import Mocks.EmptyStatement;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class FIFOMessageTakeRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new FIFOMessageTakeRule());
    }
    
    @Test
    public void TakesAMessageIfIsNotSuspendedAndExecutionQueueIsEmpty()
    {
        Message message = new MessageWithBody(StatementsFrom(new EmptyStatement("s1"), new EmptyStatement("s2")));
        
        SoftwareActorState actorState =  Utilities.CreateSoftwareActorState("actor");
        actorState.SetSuspended(false);
        actorState.ExecutionQueueState().Statements().Clear();
        actorState.MessageQueueState().Messages().Enqueue(message);
                
        globalState.DiscreteState().AddSoftwareActorState(actorState);
        
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        SoftwareActorState nextActorState = nextGlobalState.DiscreteState().FindActorState(actorState.Actor());
        nextActorState.MessageQueueState().Messages().Clear();
        nextActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), nextGlobalState));
        assertThat(generatedLTS.GetStates().size(), is(IsEqual.equalTo(2)));
    }
    
}
