/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends Statement
{
    private Actor receiver;
    private String messageID;
    
    public SendStatement(Actor receiver, String messageID)
    {
        this.receiver = receiver;
        this.messageID = messageID;
    }
    
    public Actor GetReceiver()
    {
        return receiver;
    }
    
    public String GetMessageID()
    {
        return messageID;
    }
}
