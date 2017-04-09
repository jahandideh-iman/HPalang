/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangBaseListener;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Iman Jahandideh
 * @param <CTX>
 */
public abstract class SubParser<CTX extends RuleContext> extends HPalangBaseListener
{
    protected ProgramDefinition model;
    
    protected CTX ctx; 
    
    public SubParser(ProgramDefinition model, CTX ctx)
    {
        this.model = model;
        this.ctx = ctx;
    }
    
    public void Parse()
    {
        ParseTreeWalker.DEFAULT.walk(this, ctx);
    }
}
