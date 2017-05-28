/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.MessageHandler;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import java.io.ByteArrayInputStream;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ParserTestBase
{
    Parser parser;
    ByteArrayInputStream input;
    
    ProgramDefinition model;
    
    
    @Before
    public void Setup()
    {
        parser = new Parser();
        input = null;
        model = null;
    }
    
    
    protected ByteArrayInputStream CreateInput(String input)
    {
        return new ByteArrayInputStream(input.getBytes());
    }
    
    protected <T extends Statement> T GetFirstStatement(MessageHandler handler)
    {
        return (T) handler.GetBody().element();
    }
    
    protected <T extends Statement> T GetStatement(int statement,MessageHandler handler)
    {
        int index = 0;
        for(Statement s : handler.GetBody())
        {
            if(index == statement)
                return (T)s;
            index++;
        }
        return null;
    }
}
