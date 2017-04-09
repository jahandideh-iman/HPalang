/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Core.MessageHandler;
import HPalang.Core.Statements.SendStatement;
import HPalang.Parser.antlr.HPalangParser;
import HPalang.Parser.antlr.HPalangParser.Method_defContext;

/**
 *
 * @author Iman Jahandideh
 */
public class MethodListener extends InnerListener<Method_defContext>
{
    private final MessageHandler handler;
    public MethodListener(Method_defContext ctx)
    {
        super(ctx);
        handler = new MessageHandler();
    }

    @Override
    public void enterSend(HPalangParser.SendContext ctx)
    {
        //handler.AddStatement(new SendStatement(receiver, message));
    }

    
    
    
}
