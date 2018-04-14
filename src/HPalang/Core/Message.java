/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public interface Message
{
    public enum MessageType { Invalid, Data, Control};
    
    public Queue<Statement> GetMessageBody();
    public MessageParameters Parameters();
    public MessageType MessageType();
}
