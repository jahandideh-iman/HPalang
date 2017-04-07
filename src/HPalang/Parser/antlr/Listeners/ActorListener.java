/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.antlr.Listeners;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DiscreteVariable;
import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorListener extends HPalangBaseListener
{
    private final Actor actor;
    
    public ActorListener(HPalangParser.ActorContext ctx)
    {
        actor = new Actor(ctx.ID().getText(), 5);
    }

    @Override
    public void enterVar_def(HPalangParser.Var_defContext ctx)
    {
        String type = ctx.type().getText();
        String varName = ctx.ID().getText();
        
        if(type.equals("real"))
            actor.AddContinuousVariable(new ContinuousVariable(varName),0);
        else if(type.equals("int"))
            actor.AddDiscreteVariable(new DiscreteVariable(varName), 0);
        
    }

    @Override
    public void enterMethod_def(HPalangParser.Method_defContext ctx)
    {
        super.enterMethod_def(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    Actor GetActor()
    {
        return actor;
    }
    
    
    
}
