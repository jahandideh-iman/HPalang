/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.ProgramDefinition;
import HPalang.Parser.antlr.HPalangBaseListener;
import HPalang.Parser.antlr.HPalangLexer;
import HPalang.Parser.antlr.HPalangListener;
import HPalang.Parser.SubParsers.ModelSkeletonParser;
import HPalang.Parser.antlr.HPalangParser;
import HPalang.Parser.SubParsers.ModelFleshParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Iman Jahandideh
 */
public class Parser
{
    public ProgramDefinition Parse(InputStream inputStream) throws IOException  
    {
        CharStream charStream = new ANTLRInputStream(inputStream);
        HPalangLexer lexer = new HPalangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HPalangParser parser = new HPalangParser(tokens);
        
        HPalangParser.ModelContext tree = parser.model();
        
        ProgramDefinition model = new ProgramDefinition();
        
        ProcessPhaseOne(tree, model);
        ProcessPhaseTwo(tree, model);
        
        return model;
    }

    private void ProcessPhaseOne(HPalangParser.ModelContext tree, ProgramDefinition model)
    {
        new ModelSkeletonParser(model,tree).Parse();
    }

    private void ProcessPhaseTwo(HPalangParser.ModelContext tree, ProgramDefinition model)
    {
        new ModelFleshParser(model,tree).Parse(); 
    }
}
