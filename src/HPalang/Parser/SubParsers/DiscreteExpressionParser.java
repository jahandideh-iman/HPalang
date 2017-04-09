/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers;

import HPalang.Core.ProgramDefinition;
import HPalang.Parser.SubParser;
import HPalang.Parser.antlr.HPalangParser;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteExpressionParser extends SubParser<HPalangParser.D_exprContext>
{
    
    public DiscreteExpressionParser(ProgramDefinition model, HPalangParser.D_exprContext ctx, ExpressionContainer container)
    {
        super(model, ctx);
    }
    
}
