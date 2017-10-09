/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class AssignmentLocation extends UrgentLocation
{

    private final HPalang.Core.Statements.AssignmentStatement statement;

    public AssignmentLocation(AssignmentStatement statement, String name, ActorModelData actorData)
    {
        super(name + "_assignemnt", actorData);
        this.statement = statement;
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        super.ProcessOutLabel(label);
        Expression processedExpression = Process(statement.Expression());
        label.AddAssignment(actorData.ResetFor(statement.Variable().Name(), processedExpression.toString()));
    }

    // TODO: Remove this crap. Find another way. 
    private Expression Process(Expression expression)
    {
        if(expression instanceof BinaryExpression)
        {
            BinaryExpression binaryExpression = (BinaryExpression) expression;
            
            return new BinaryExpression(
                    Process(binaryExpression.Operand1()), 
                    binaryExpression.Operator(),
                    Process(binaryExpression.Operand2()));
        }
        if(expression instanceof VariableExpression)
        {
            VariableExpression variableExpression = (VariableExpression) expression;
            
            for(MessageHandler handler : actorData.MessageHandlers())
            {
                for(VariableParameter parameter : handler.Parameters().AsList())
                {
                    if(variableExpression.Variable().Name().equals(parameter.Name()))
                        return new VariableExpression(new IntegerVariable(actorData.ParameterNameFor(parameter)));
                } 
            }
            
            return variableExpression;
        }
        if(expression instanceof ConstantDiscreteExpression)
            return expression;
        throw new RuntimeException(String.format("Can Process expression %s", expression.getClass().toString()));
    }
}
