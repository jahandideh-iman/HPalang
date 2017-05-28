/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.Actor;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.Expression;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangParser;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionParser extends ExpressionParserT<HPalangParser.ExprContext>
{
    private LogicalExpression.Operator lastOperator;
    List<ExpressionHolder> expressionHolders = new LinkedList<>();
    List<LogicalExpression.Operator> operators = new LinkedList<>();

    public ExpressionParser(ProgramDefinition model, HPalangParser.ExprContext ctx, ExpressionHolder holder, Actor actor)
    {
        super(model, ctx, holder, actor);
    }

    @Override
    public void enterExpr(HPalangParser.ExprContext ctx)
    {
        if(this.ctx != ctx)
            return;
             
        lastOperator = LogicalExpression.Operator.Invalid;
    }

    @Override
    public void exitExpr(HPalangParser.ExprContext ctx)
    {
        if (this.ctx != ctx) 
            return;
        
        holder.TrySetExpression(parsedExpression);
    }

    @Override
    public void enterConjunction(HPalangParser.ConjunctionContext ctx)
    {
        lastOperator = LogicalExpression.Operator.AND;
    }

    @Override
    public void enterDisjuncition(HPalangParser.DisjuncitionContext ctx)
    {
        lastOperator = LogicalExpression.Operator.OR;
    }

    @Override
    public void enterExpr0(HPalangParser.Expr0Context ctx)
    {
        ExpressionHolder c = new ExpressionHolder();
        new Expression0Pasrser(model, ctx, c, actor).Parse();

        UpdateExpressionWith(c.Expression());
    }

    private void UpdateExpressionWith(Expression expression)
    {
        if (lastOperator == LogicalExpression.Operator.Invalid) 
            this.parsedExpression = expression;
         else 
            this.parsedExpression = new LogicalExpression(
                    (DiscreteExpression) parsedExpression,
                    lastOperator,
                    (DiscreteExpression) expression);   
    }
}
