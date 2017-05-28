/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.Actor;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.Expression;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangParser;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class Expression0Pasrser extends ExpressionParserT<HPalangParser.Expr0Context>
{   
    List<ExpressionHolder> expressionHolders = new LinkedList<>();
    List<ComparisonExpression.Operator> operators = new LinkedList<>();
    
    public Expression0Pasrser(ProgramDefinition model, HPalangParser.Expr0Context ctx, ExpressionHolder holder, Actor actor)
    {
        super(model, ctx, holder, actor);
    }

    @Override
    public void enterExpr0(HPalangParser.Expr0Context ctx)
    {
        if(this.ctx != ctx)
            return;
        //lastOperator = ComparisonExpression.Operator.Invalid;
    }

    @Override
    public void exitExpr0(HPalangParser.Expr0Context ctx)
    {
        if(this.ctx != ctx)
            return;
        Expression expr= ProcessExpression();
        holder.TrySetExpression(expr);
    }

    @Override
    public void enterLesser(HPalangParser.LesserContext ctx)
    {
        operators.add(ComparisonExpression.Operator.Lesser);
    }

    @Override
    public void enterLesserEqual(HPalangParser.LesserEqualContext ctx)
    {
        operators.add(ComparisonExpression.Operator.LesserEqual);
    }

    @Override
    public void enterEquality(HPalangParser.EqualityContext ctx)
    {
        operators.add(ComparisonExpression.Operator.Equal);
    }

    @Override
    public void enterGreaterEqual(HPalangParser.GreaterEqualContext ctx)
    {
        operators.add(ComparisonExpression.Operator.GreaterEqual);
    }
    
    @Override
    public void enterGreater(HPalangParser.GreaterContext ctx)
    {
        operators.add(ComparisonExpression.Operator.Greater);
    }

    @Override
    public void enterExpr1(HPalangParser.Expr1Context ctx)
    {
        ExpressionHolder c = new ExpressionHolder();
        new Expression1Pasrser(model, ctx, c, actor).Parse();

        expressionHolders.add(c);
        UpdateExpressionWith(c.Expression());
    }

    private Expression ProcessExpression()
    {
        Expression expr = expressionHolders.get(0).Expression();
        
        for(int i = 0 ; i< operators.size(); i++)
            expr = new ComparisonExpression(
                    (DiscreteExpression) expr,
                    operators.get(i),
                    (DiscreteExpression)expressionHolders.get(i+1).Expression());
        
        return expr;
    }
    
    
    private void UpdateExpressionWith(Expression expression)
    {
        parsedExpression = expression;
//        if (lastOperator == ComparisonExpression.Operator.Invalid) 
//            this.lastExpr = expression;
//         else 
//            this.lastExpr = new LogicalExpression(
//                    (DiscreteExpression) expression,
//                    lastOperator,
//                    (DiscreteExpression) lastExpr);   
    }
  
}
