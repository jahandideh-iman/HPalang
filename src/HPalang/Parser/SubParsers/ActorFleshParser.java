/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.Parser.SubParser;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorFleshParser extends SubParser<HPalangParser.ActorContext>
{ 
    private final Actor actor;
    public ActorFleshParser(ProgramDefinition model, HPalangParser.ActorContext ctx)
    {
        super(model, ctx);
        
        actor = model.FindActor(ctx.ID().getText());
    }

    @Override
    public void enterMethod_def(HPalangParser.Method_defContext ctx)
    {
        MessageHandler handler = actor.GetMessageHandler(ctx.ID().getText());
        
        new MethodDefinitionParser(model,ctx,actor,handler).Parse();
    }  
}
