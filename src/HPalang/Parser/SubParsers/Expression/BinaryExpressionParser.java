/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.SoftwareActor;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperator;
import HPalang.Core.Expression;
import HPalang.Core.ModelDefinition;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.RuleContext;

/**
 *
 * @author Iman Jahandideh
 */
public class BinaryExpressionParser<T extends RuleContext> extends ExpressionParserT<T>
{
    List<ExpressionHolder> expressionHolders = new LinkedList<>();
    List<BinaryOperator> operators = new LinkedList<>();

    public BinaryExpressionParser(ModelDefinition model, T ctx, ExpressionHolder holder, SoftwareActor actor)
    {
        super(model, ctx, holder, actor);
    }
    
        
    protected Expression ProcessExpression()
    {
        Expression expr = expressionHolders.get(0).Expression();
        
        for(int i = 0 ; i< operators.size(); i++)
            expr = new BinaryExpression(
                    (DiscreteExpression) expr,
                    operators.get(i),
                    (DiscreteExpression)expressionHolders.get(i+1).Expression());
        
        return expr;
    }
}
