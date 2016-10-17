/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Statements.SendStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class MainBlock
{
    private List<SendStatement> sendStatements = new ArrayList<>();
    
    public void AddSendStatement(SendStatement sendStatement)
    {
        sendStatements.add(sendStatement);
    }

    public List<SendStatement> GetSendStatements()
    {
        return sendStatements;
    }
    
}
