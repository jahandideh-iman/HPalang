/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Parser.SubParsers.Expression.ExpressionParser;
import HPalang.Parser.SubParsers.Expression.ExpressionHolder;
import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Parser.SubParser;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class MethodDefinitionParser extends SubParser<HPalangParser.Method_defContext>
{
    private final MessageHandler handler;
    private final Actor actor;
    
    public MethodDefinitionParser(
            ProgramDefinition model,
            HPalangParser.Method_defContext ctx,
            Actor actor,
            MessageHandler handler)
    {
        super(model, ctx);
        this.handler = handler;
        this.actor = actor;
    }

    @Override
    public void enterSend(HPalangParser.SendContext ctx)
    {
        Actor destination = model.FindActor(ctx.destination().getText()); 
        MessageHandler message = destination.GetMessageHandler(ctx.message().getText());
        
        SendStatement send = new SendStatement(destination, new NormalMessage(message));
        
        handler.AddStatement(send);
    }

    @Override
    public void enterAssignment(HPalangParser.AssignmentContext ctx)
    {
        ExpressionHolder container = new ExpressionHolder();
        
        new ExpressionParser(model, ctx.expr(), container, actor).Parse();
        
        String varName = ctx.var_name().getText();
        Statement statement = null;
        Variable var = actor.FindVariable(varName);
        if(actor.HasDiscreteVariable(varName))
            statement = new DiscreteAssignmentStatement(
                    (DiscreteVariable)var, 
                    (DiscreteExpression) container.Expression());
        
        else if(actor.HasContinuousVariable(varName))
            statement = new ContinuousAssignmentStatement(
                    (ContinuousVariable)var, 
                    (ContinuousExpression) container.Expression());

        handler.AddStatement(statement);
    }

    @Override
    public void enterC_behavior(HPalangParser.C_behaviorContext ctx)
    { 
        ContinuousVariable var = actor.FindContinuousVariable(ctx.def_equ().first_driv().var_name().ID().getText());
        String rightSide = ctx.def_equ().expr().getText();
        DifferentialEquation equ = new DifferentialEquation(var, rightSide);
        ContinuousBehavior behavior = new ContinuousBehavior(ctx.inv_expr().getText(), equ,ctx.guard_expr().getText(), Statement.EmptyStatements());
        
        ContinuousBehaviorStatement statement = new ContinuousBehaviorStatement(behavior);

        handler.AddStatement(statement);
    }
}
