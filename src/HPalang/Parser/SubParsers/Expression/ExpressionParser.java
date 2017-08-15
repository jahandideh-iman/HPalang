/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.SoftwareActor;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalOrOperator;
import HPalang.Core.Expression;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionParser extends BinaryExpressionParser<HPalangParser.ExprContext>
{

    public ExpressionParser(ProgramDefinition model, HPalangParser.ExprContext ctx, ExpressionHolder holder, SoftwareActor actor)
    {
        super(model, ctx, holder, actor);
    }

    @Override
    public void enterExpr(HPalangParser.ExprContext ctx)
    {
        if(this.ctx != ctx)
            return;  
    }

    @Override
    public void exitExpr(HPalangParser.ExprContext ctx)
    {
        if (this.ctx != ctx) 
            return;
        Expression expr= ProcessExpression();
        holder.TrySetExpression(expr);
    }

    @Override
    public void enterConjunction(HPalangParser.ConjunctionContext ctx)
    {
        operators.add(new LogicalAndOperator());
    }

    @Override
    public void enterDisjuncition(HPalangParser.DisjuncitionContext ctx)
    {
        operators.add(new LogicalOrOperator());
    }

    @Override
    public void enterExpr0(HPalangParser.Expr0Context ctx)
    {
        ExpressionHolder c = new ExpressionHolder();
        new Expression0Pasrser(model, ctx, c, actor).Parse();
        expressionHolders.add(c);
    }
}
