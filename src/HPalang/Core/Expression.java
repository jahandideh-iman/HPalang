/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public interface Expression
{
    public boolean IsComputable(ValuationContainer valuations);
    public int Evaluate(ValuationContainer valuations);
    public Expression PartiallyEvaluate(ValuationContainer valuations);
    public void Visit(ExpressionVisitor visitor);
}
