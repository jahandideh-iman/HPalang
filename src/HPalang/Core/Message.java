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
    public Queue<Statement> GetMessageBody();
    
    public MessageParameters Parameters();
    public MessageArguments Arguments();
    public void AddArgument(VariableArgument argument);
    
    public void SetPriority(int priority);
    public int Priority();
}
