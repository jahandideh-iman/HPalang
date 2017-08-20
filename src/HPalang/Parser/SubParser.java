/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.ModelDefinition;
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
    protected ModelDefinition model;
    
    protected CTX ctx; 
    
    public SubParser(ModelDefinition model, CTX ctx)
    {
        this.model = model;
        this.ctx = ctx;
    }
    
    final public void Parse()
    {
        GetParseTreeWalker().walk(this, ctx);
    }
    
    protected ParseTreeWalker GetParseTreeWalker()
    {
        return ParseTreeWalker.DEFAULT;
    }
}
