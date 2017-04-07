/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.antlr.Listeners;

import HPalang.Core.Actor;
import HPalang.Core.MainBlock;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Parser.antlr.HPalangParser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelListener extends HPalangBaseListener
{
    private ProgramDefinition def;

    @Override
    public void enterModel(HPalangParser.ModelContext ctx)
    {
        def = new ProgramDefinition();
    }
    
    
    @Override
    public void enterActor(HPalangParser.ActorContext ctx) 
    {
        ActorListener listener = new ActorListener(ctx);
        ParseTreeWalker.DEFAULT.walk(listener, ctx);  
        def.AddActor(listener.GetActor());
    }

    @Override
    public void enterMain(HPalangParser.MainContext ctx)
    {
        def.SetMainBlock(new MainBlock());
        //def.AddActor(new Actor(ctx.ID().getText(), 0));
    }
    

    public ProgramDefinition GetDefinition()
    {
        return def;
    }
}
