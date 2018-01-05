/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.StateInfoBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.*;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.Event.SendPacketAndResetNetworkAction;
import HPalang.LTSGeneration.StateInfo;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import Mocks.EmptyMessage;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Iman Jahandideh
 */
public class CANScheduleRuleTest extends SOSRuleTestFixture
{
    SoftwareActor receiver = CreateSofwareActor("receiver");
    SoftwareActor sender = CreateSofwareActor("sender");
    
    Message lowPriorityMessage;
    Message highPriorityMessage;
    
    MessagePacket lowPriorityPacket;
    MessagePacket highPriorityPacket;
    
    int lowPriority = 0;
    int highPriority = 1;
    
    float lowPriorityDelay = 1f;
    float highPriorityDelay = 2f;
    
    @Before
    public void Setup()
    {
        rule = new CANScheduleRule();
        
        lowPriorityMessage = new EmptyMessage("lowPriority", lowPriority);
        highPriorityMessage = new EmptyMessage("highPriority", highPriority);
        
        lowPriorityPacket = MessagePacket(sender, receiver, lowPriorityMessage, MessageArguments.Empty());
        highPriorityPacket = MessagePacket(sender, receiver, highPriorityMessage, MessageArguments.Empty());

        sender.SetNetworkDelay(receiver,lowPriorityMessage, lowPriorityDelay);
        sender.SetNetworkDelay(receiver,highPriorityMessage, highPriorityDelay);
        
        PutMessagePacketInNetworkState(lowPriorityPacket, globalState);
        PutMessagePacketInNetworkState(highPriorityPacket, globalState);
        ResetEventStatePool(globalState);
        SetNetworkStateIdle(true, globalState);
        
    }
    
    @Test
    public void IsNotAppliedWhenNetworkStateIsNotIdle()
    {
        SetNetworkStateIdle(false, globalState);
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void IsNotAppliedWhenThereIsSoftwareAction()
    {
        StateInfo stateInfoWithSoftwareAction = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(SelfTransition(globalState, new SoftwareLabel())).
                Build();
        
        ApplyAndVerifyRuleOn(stateInfoWithSoftwareAction);
        //rule.TryApply(stateInfoWithSoftwareAction, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(stateInfoWithSoftwareAction);
    }
    
    @Test
    public void RegistersTheHighestPriorityMessageInTheEventsState()
    {
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        EventsState generatedEventState = CollectedGlobalState().EventsState();
        
        Event expectedEvent = CreateEventFor(highPriorityDelay, NetworkAction(highPriorityPacket), globalState);
        
        assertThat(generatedEventState.Events(), hasItem(expectedEvent));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void MakeNetworkStateNotIdleWhenApplied()
    {
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        NetworkState generatedNetworkState =  CollectedGlobalState().NetworkState();
        
        assertThat(generatedNetworkState.IsIdle(), is(false));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void Inegration()
    {
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);

        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        DebufferFromNetworkState(highPriorityPacket, nextGlobalState);
        SetNetworkStateIdle(false, nextGlobalState);
        RegisterEvent(highPriorityDelay, new SendPacketAndResetNetworkAction(highPriorityPacket), nextGlobalState);

        transitionCollectorChecker.ExpectTransition(new NetworkLabel(), nextGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    
    public SendPacketAndResetNetworkAction NetworkAction(MessagePacket packet)
    {
        return new SendPacketAndResetNetworkAction(packet);
    }
}