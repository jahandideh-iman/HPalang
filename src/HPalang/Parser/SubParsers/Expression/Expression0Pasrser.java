/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.SoftwareActor;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserOperator;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.Expression;
import HPalang.Core.ModelDefinition;
import HPalang.Parser.antlr.HPalangParser;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class Expression0Pasrser extends BinaryExpressionParser<HPalangParser.Expr0Context>
{   
   
    public Expression0Pasrser(ModelDefinition model, HPalangParser.Expr0Context ctx, ExpressionHolder holder, SoftwareActor actor)
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
        operators.add(new LesserOperator());
    }

    @Override
    public void enterLesserEqual(HPalangParser.LesserEqualContext ctx)
    {
        operators.add(new LesserEqualOperator());
    }

    @Override
    public void enterEquality(HPalangParser.EqualityContext ctx)
    {
        operators.add(new EqualityOperator());
    }

    @Override
    public void enterGreaterEqual(HPalangParser.GreaterEqualContext ctx)
    {
        operators.add(new GreaterEqualOperator());
    }
    
    @Override
    public void enterGreater(HPalangParser.GreaterContext ctx)
    {
        operators.add(new GreaterOperator());
    }

    @Override
    public void enterExpr1(HPalangParser.Expr1Context ctx)
    {
        ExpressionHolder c = new ExpressionHolder();
        new Expression1Pasrser(model, ctx, c, actor).Parse();

        expressionHolders.add(c);
    }
}
