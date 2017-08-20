/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.ModelDefinition;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class Expression3Pasrser extends ExpressionParserT<HPalangParser.Expr3Context>
{

    public Expression3Pasrser(ModelDefinition model, HPalangParser.Expr3Context ctx, ExpressionHolder holder, SoftwareActor actor)
    {
        super(model, ctx, holder, actor);
    }

    @Override
    public void enterExpr3(HPalangParser.Expr3Context ctx)
    {
        if(this.ctx != ctx)
            return;
    }

    @Override
    public void exitExpr3(HPalangParser.Expr3Context ctx)
    {
        if(this.ctx != ctx)
            return;
        holder.TrySetExpression(parsedExpression);
    }

    @Override
    public void enterNumber(HPalangParser.NumberContext ctx)
    {
        if(ctx.getChild(0) instanceof HPalangParser.Real_numContext )
            enterReal_num((HPalangParser.Real_numContext) ctx.getChild(0));
        else if(ctx.getChild(0) instanceof HPalangParser.Int_numContext )
            enterInt_num((HPalangParser.Int_numContext) ctx.getChild(0));
    }

    
    
    @Override
    public void enterReal_num(HPalangParser.Real_numContext ctx)
    {
        parsedExpression = new ConstantContinuousExpression(Float.parseFloat(ctx.getText()));
    }

    @Override
    public void enterInt_num(HPalangParser.Int_numContext ctx)
    {
        parsedExpression = new ConstantDiscreteExpression(Integer.parseInt(ctx.getText()));
    }

    @Override
    public void enterVar_name(HPalangParser.Var_nameContext ctx)
    {
        String varName = ctx.ID().getText();
        parsedExpression = new VariableExpression(actor.FindDiscreteVariable(varName));
    }

    @Override
    public void enterB_true(HPalangParser.B_trueContext ctx)
    {
        parsedExpression = new TrueConst();
    }

    @Override
    public void enterB_false(HPalangParser.B_falseContext ctx)
    {
        parsedExpression = new FalseConst();
    }
    
    @Override
    public void enterExpr(HPalangParser.ExprContext ctx)
    {
        ExpressionHolder c= new ExpressionHolder();
        new ExpressionParser(model, ctx, c, actor).Parse();
        parsedExpression = c.Expression();
    }

}
