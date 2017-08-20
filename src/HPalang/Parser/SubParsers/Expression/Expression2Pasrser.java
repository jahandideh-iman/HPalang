/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.ModelDefinition;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class Expression2Pasrser extends ExpressionParserT<HPalangParser.Expr2Context>
{

    public Expression2Pasrser(ModelDefinition model, HPalangParser.Expr2Context ctx, ExpressionHolder holder, SoftwareActor actor)
    {
        super(model, ctx, holder, actor);
    }

    @Override
    public void enterExpr2(HPalangParser.Expr2Context ctx)
    {
        if(this.ctx != ctx)
            return;
    }
    
    @Override
    public void exitExpr2(HPalangParser.Expr2Context ctx)
    {
        if(this.ctx != ctx)
            return;
        holder.TrySetExpression(parsedExpression);
    }
    
    @Override
    public void enterExpr3(HPalangParser.Expr3Context ctx)
    {
        ExpressionHolder c = new ExpressionHolder();
        new Expression3Pasrser(model, ctx, c, actor).Parse();

        UpdateExpressionWith(c.Expression());
    }

    private void UpdateExpressionWith(Expression expression)
    {
        parsedExpression = expression;
//        if (lastOperator == LogicalExpression.Operator.Invalid) 
//            this.lastExpr = expression;
//         else 
//            this.lastExpr = new LogicalExpression(
//                    (DiscreteExpression) expression,
//                    lastOperator,
//                    (DiscreteExpression) lastExpr);   
    }
 
    
}
