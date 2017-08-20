/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Core.ModelDefinition;
import HPalang.Parser.SubParser;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelFleshParser extends SubParser<HPalangParser.ModelContext>
{

    public ModelFleshParser(ModelDefinition model, HPalangParser.ModelContext ctx)
    {
        super(model, ctx);
    }

    @Override
    public void enterActor(HPalangParser.ActorContext ctx)
    {
        new ActorFleshParser(model,ctx).Parse();
    }
    
    
}
