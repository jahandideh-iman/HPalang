/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.Expressions.Visitors.BinaryExpressionVisitor;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class BinaryExpression extends DiscreteExpressionT<BinaryExpression>
{  
    private final Expression operand1;
    private final BinaryOperator operator;
    private final Expression operand2;

    public BinaryExpression(Expression operand1, BinaryOperator operator,Expression operand2)
    {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }
    
    public BinaryOperator Operator()
    {
        return operator;
    }
    
    public Expression Operand1()
    {
        return operand1;
    }
    
    public Expression Operand2()
    {
        return operand2;
    }
    
    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return operator.Evaluate(operand1, operand2, valuations);
    }
        
    @Override
    protected boolean InternalEquals(BinaryExpression other)
    {
        return this.operand1.equals(other.operand1)
                && this.operator.equals(other.operator)
                && this.operand2.equals(other.operand2);
    }

    @Override
    protected int InternalHashCode()
    {
        return operand1.hashCode() + operator.hashCode()+ operand2.hashCode();
    }   

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return operand1.IsComputable(valuations) && operand2.IsComputable(valuations);
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        return new BinaryExpression(operand1.PartiallyEvaluate(valuations), operator, operand2.PartiallyEvaluate(valuations));
    }

    @Override
    public String toString()
    {
        return String.format("(%s %s %s)", operand1.toString(), operator.toString(), operand2.toString());
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof BinaryExpressionVisitor)
            ((BinaryExpressionVisitor) visitor).Visit(this);
    }
}
