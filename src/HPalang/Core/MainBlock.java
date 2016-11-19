/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Statements.SendStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MainBlock
{
    private Queue<SendStatement> sendStatements = new LinkedList<>();
    
    public void AddSendStatement(SendStatement sendStatement)
    {
        sendStatements.add(sendStatement);
    }

    public Queue<SendStatement> GetSendStatements()
    {
        return sendStatements;
    }
    
}
