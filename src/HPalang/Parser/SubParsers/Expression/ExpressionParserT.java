/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.Actor;
import HPalang.Core.Expression;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.SingleLevelParseTreeWalker;
import HPalang.Parser.SubParser;
import java.util.List;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Iman Jahandideh
 * @param <T>
 */
public abstract class ExpressionParserT<T extends RuleContext> extends SubParser<T>
{
    protected final ExpressionHolder holder;
    protected final Actor actor;
    protected Expression parsedExpression;
    
    public ExpressionParserT(ProgramDefinition model, T ctx, ExpressionHolder holder, Actor actor)
    {
        super(model, ctx);
        this.holder = holder;
        this.actor = actor;
    }

    @Override
    protected ParseTreeWalker GetParseTreeWalker()
    {
        return SingleLevelParseTreeWalker.SingleDEFAULT;
    }
    
    
    
}
