/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.Message;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends Statement
{
    private Actor receiver;
    private Message message;
    
    public SendStatement(Actor receiver, Message message)
    {
        this.receiver = receiver;
        this.message = message;
    }
    
    public Actor GetReceiver()
    {
        return receiver;
    }
    
    public Message GetMessage()
    {
        return message;
    }
}
