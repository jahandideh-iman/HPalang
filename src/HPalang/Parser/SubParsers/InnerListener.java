/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Parser.antlr.HPalangBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author Iman Jahandideh
 * @param <T>
 */
public abstract class InnerListener<T extends ParserRuleContext> extends HPalangBaseListener
{
    public InnerListener(T ctx)
    {
        
    }
}
