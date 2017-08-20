/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.ModelDefinition;
import HPalang.Parser.SubParser;
import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorSkeletonParser extends SubParser<HPalangParser.ActorContext>
{
    private final SoftwareActor actor;

    public ActorSkeletonParser(ModelDefinition model, HPalangParser.ActorContext ctx)
    {
        super(model, ctx);
        actor = new SoftwareActor(ctx.ID().getText(), 5);
    }

    @Override
    public void enterVar_def(HPalangParser.Var_defContext ctx)
    {
        String type = ctx.type().getText();
        String varName = ctx.var_name().ID().getText();
        
        if(type.equals("real"))
            actor.AddContinuousVariable(new ContinuousVariable(varName),0);
        else if(type.equals("int"))
            actor.AddDiscreteVariable(new DiscreteVariable(varName), 0);
        
    }

    @Override
    public void enterMethod_def(HPalangParser.Method_defContext ctx)
    {
        actor.AddMessageHandler(ctx.ID().getText(), new MessageHandler());
    }  

    @Override
    public void exitActor(HPalangParser.ActorContext ctx)
    {
        model.AddActor(actor);
    }
    
    
}
