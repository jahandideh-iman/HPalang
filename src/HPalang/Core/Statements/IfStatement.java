/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class IfStatement extends AbstractStatement<IfStatement>
{

    private final DiscreteExpression expression;
    
    private final Queue<Statement> trueStatements = new LinkedList<>();
    private final Queue<Statement> falseStatements = new LinkedList<>();
    
    public IfStatement(DiscreteExpression expression, Queue<Statement> trueStatements, Queue<Statement> falseStatements )
    {
        this.expression = expression;
        this.trueStatements.addAll(trueStatements);
        this.falseStatements.addAll(falseStatements);
    }
    
    public DiscreteExpression Expression()
    {
        return expression;
    }
    
    public Queue<Statement> TrueStatements()
    {
        return trueStatements;
    }
    
    public Queue<Statement> FalseStatements()
    {
        return falseStatements;
    }
    
    @Override
    protected boolean InternalEquals(IfStatement other)
    {
        return this.expression.equals(other.expression)
                && this.trueStatements.equals(other.trueStatements)
                && this.falseStatements.equals(other.falseStatements);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "if";
    }
    
}
