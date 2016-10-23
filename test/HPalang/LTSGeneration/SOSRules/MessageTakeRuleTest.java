/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.Message;
import HPalang.LTSGeneration.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Statements.Statement;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTakeRuleTest
{
    @Test
    public void ForEachActorStateIfThereIsNoStatementAndThereAreMessagesThenEnqueuesOneMessageAndAddsItsBodyToActorStatements()
    {
        GlobalRunTimeState globalState = new GlobalRunTimeState();
        
        ActorRunTimeState actor1State = new ActorRunTimeState(new Actor("actor1",1));
        ActorRunTimeState actor2State = new ActorRunTimeState(new Actor("actor2",1));
        
        globalState.AddActorRunTimeState(actor1State);
        globalState.AddActorRunTimeState(actor2State);
        
        actor1State.EnqueueMessage(new EmptyMessage());
        actor2State.EnqueueMessage(new EmptyMessage());
        
        
        MessageTakeRule rule = new MessageTakeRule();
        
        LTSGenerator ltsGenerator = new LTSGenerator();
        
        ltsGenerator.AddSOSRule(rule);
        
        LabeledTransitionSystem lts = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState actor1MessageTake = globalState.Clone();
        actor1MessageTake.FindActorState(actor1State.GetActor()).DequeueNextMessage();
        
        
        assertTrue(lts.HasState(actor1MessageTake));
    }
    
    private class EmptyMessage implements Message
    {
        @Override
        public Queue<Statement> GetMessageBody()
        {
            return new LinkedList<>();
        }
    }
    
}
