/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Core.Actor;
import HPalang.Core.MainBlock;
import HPalang.Core.ProgramDefinition;
import HPalang.Parser.SubParser;
import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Parser.antlr.HPalangParser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelSkeletonParser extends SubParser<HPalangParser.ModelContext>
{
    public ModelSkeletonParser(ProgramDefinition model, HPalangParser.ModelContext ctx)
    {
        super(model, ctx);
    }
    
    @Override
    public void enterActor(HPalangParser.ActorContext ctx) 
    {
        new ActorSkeletonParser(model,ctx).Parse();
    }

    @Override
    public void enterMain(HPalangParser.MainContext ctx)
    {
        model.SetMainBlock(new MainBlock());
    }
}
