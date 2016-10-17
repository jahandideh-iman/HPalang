/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Statements.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageHandler
{
    private List<Statement> statements = new ArrayList<>();
    
    public void AddStatement(Statement statement)
    {
        statements.add(statement);
    }
}
