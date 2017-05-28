/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Iman Jahandideh
 */
public class SingleLevelParseTreeWalker extends ParseTreeWalker
{
    public static final ParseTreeWalker SingleDEFAULT = new SingleLevelParseTreeWalker();

    @Override
    public void walk(ParseTreeListener listener, ParseTree t)
    {
        enter(listener, t);
        int n = t.getChildCount();
        for (int i = 0; i < n; i++) {
            enter(listener, t.getChild(i));            
            exit(listener, t.getChild(i));

        }
        exit(listener, t);
    }
    
    protected void enter(ParseTreeListener listener,ParseTree t)
    {
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode) t);
            return;
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode) t);
            return;
        }
        
        RuleNode r = (RuleNode) t;
        enterRule(listener, r);
    }
    
    protected void exit(ParseTreeListener listener,ParseTree t)
    {
        if (t instanceof ErrorNode) {
            return;
        } else if (t instanceof TerminalNode) {
            return;
        }
        RuleNode r = (RuleNode) t;
        exitRule(listener, r);
    }
    
    
    
}
