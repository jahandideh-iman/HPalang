/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.DiscreteExpressionT;
import HPalang.Core.Variables.IntegerVariable;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated //Use AssignmentStatement
public class DiscreteAssignmentStatement extends AssignmentStatement
{
    public  DiscreteAssignmentStatement(IntegerVariable dVar, DiscreteExpression dExpr)
    {
        super(dVar, dExpr);
    }
}
