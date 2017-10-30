/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Visitors;

import HPalang.Core.DiscreteExpressions.FalseConst;

/**
 *
 * @author Iman Jahandideh
 */
public interface FalseConstVisitor
{
    public void Visit(FalseConst expr);
}
