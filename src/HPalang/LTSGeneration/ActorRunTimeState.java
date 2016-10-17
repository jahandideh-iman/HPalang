/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Actor;
import HPalang.Statements.SendStatement;
import HPalang.Statements.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState 
{
    class Message
    {
        private String messageID;
        
        public Message(String messageID)
        {
            this.messageID = messageID;
        }
    }
    class DiscreteState
    {
        private Queue<Message> messages = new LinkedList<>();
        private Queue<Statement> statemens = new LinkedList<>();
        
        public void EnqueueMessage(Message message)
        {
            messages.add(message);
        }
    }
    
    class ContinuousState
    {
        public List<ContinuousBehavior> continuousBehaviors = new ArrayList<>();
    }
    
    private Actor actor;
    
    private DiscreteState discreteState = new DiscreteState();
    private ContinuousState continuousState = new ContinuousState();
    
    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;
    }
    
    void AddSendMessage(SendStatement sendStatement)
    {
        discreteState.EnqueueMessage(new Message(sendStatement.GetMessageID()));
    }
    
    public Actor GetActor()
    {
       return actor;
    }
}
