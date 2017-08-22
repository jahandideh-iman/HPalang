/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ContinuousExpression;
import HPalang.Core.ContinuousVariable;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated //Use AssignmentStatement
public class ContinuousAssignmentStatement extends AssignmentStatement
{
    public  ContinuousAssignmentStatement(ContinuousVariable var, ContinuousExpression expr)
    {
        super(var, expr);
    }
}
