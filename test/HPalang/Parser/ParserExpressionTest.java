/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.Actor;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.ArithmeticExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalOrOperator;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.Expression;
import HPalang.Parser.SubParsers.Expression.ExpressionHolder;
import HPalang.Parser.SubParsers.Expression.ExpressionParser;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Iman Jahandideh
 */
public class ParserExpressionTest extends ParserTestBase
{
    @Test
    public void ParsesLogicalExpression() throws IOException
    {
        input = CreateInput("true && false || false");

        Actor a = new Actor("actor", 0);
        ExpressionHolder holder = new ExpressionHolder();

        new ExpressionParser(
                null,
                parser.GetExprContext(input),
                holder,
                a
        ).Parse();

        Expression expected = Or(And(True(), False()), False());

        assertThat(holder.Expression(), is(equalTo(expected)));
    }
    @Test
    public void ParsesComparisionLogicalExpression() throws IOException
    {
        input = CreateInput("1 < 2 && 3 <= 4 && 5==6 && 7>=8 && 9>10 ");

        Actor a = new Actor("actor", 0);
        ExpressionHolder holder = new ExpressionHolder();

        new ExpressionParser(
                null,
                parser.GetExprContext(input),
                holder,
                a
        ).Parse();

        Expression expected = 
                And(
                        And(
                                And(
                                        And(
                                                L(Const(1), Const(2)),
                                                LE(Const(3),Const(4))),
                                        EQ(Const(5), Const(6))),
                        GE(Const(7),Const(8))),
                G(Const(9), Const(10)));
                
        assertThat(holder.Expression(), is(equalTo(expected)));
    }
    @Test
    public void ParsesNestedLogicalExpression() throws IOException
    {
        input = CreateInput("true && (false || false)");

        Actor a = new Actor("actor", 0);
        ExpressionHolder holder = new ExpressionHolder();

        new ExpressionParser(
                null,
                parser.GetExprContext(input),
                holder,
                a
        ).Parse();

        Expression expected = And(True(), Or(False(),False()));

        assertThat(holder.Expression(), is(equalTo(expected)));
    }
    
    
    @Test
    public void ParsesArithmeticExpression() throws IOException
    {
        input = CreateInput("1 + 2 - 3 ");
        
        Actor a = new Actor("actor", 0);
        ExpressionHolder holder = new ExpressionHolder();

        new ExpressionParser(
                null,
                parser.GetExprContext(input),
                holder,
                a
        ).Parse();
       
        
        Expression expected = Sub(Add(Const(1),Const(2)), Const(3));
       
        assertThat(holder.Expression(), is(equalTo(expected)));
    }
    
    private ConstantDiscreteExpression Const(int c)
    {
        return new ConstantDiscreteExpression(c);
    }
    
    private ArithmeticExpression Add(DiscreteExpression op1, DiscreteExpression op2)
    {
        return new ArithmeticExpression(op1, ArithmeticExpression.Operator.Add, op2);
    }
    
    private ArithmeticExpression Sub(DiscreteExpression op1, DiscreteExpression op2)
    {
        return new ArithmeticExpression(op1, ArithmeticExpression.Operator.Subtract, op2);
    }
    
    private BinaryExpression And(DiscreteExpression op1, DiscreteExpression op2)
    {
        return new BinaryExpression(op1,new LogicalAndOperator(), op2);
    }
     
    private BinaryExpression Or(DiscreteExpression op1, DiscreteExpression op2)
    {
        return new BinaryExpression(op1,new LogicalOrOperator(), op2);
    }
    
    private ConstantDiscreteExpression True()
    {
        return new TrueConst();
    }
    
    private ConstantDiscreteExpression False()
    {
        return new FalseConst();
    } 
    private BinaryExpression L(DiscreteExpression exp1, DiscreteExpression exp2)
    {
        return new BinaryExpression(exp1, new LesserOperator(), exp2);
    }
    
    private BinaryExpression LE(DiscreteExpression exp1, DiscreteExpression exp2)
    {
        return new BinaryExpression(exp1, new LesserEqualOperator(), exp2);
    }
    private BinaryExpression EQ(DiscreteExpression exp1, DiscreteExpression exp2)
    {
        return new BinaryExpression(exp1, new EqualityOperator(), exp2);
    }
        private BinaryExpression GE(DiscreteExpression exp1, DiscreteExpression exp2)
    {
        return new BinaryExpression(exp1, new GreaterEqualOperator(), exp2);
    }
    private BinaryExpression G(DiscreteExpression exp1, DiscreteExpression exp2)
    {
        return new BinaryExpression(exp1, new GreaterOperator(), exp2);
    }

}
