/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.Statement;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.Location;
import java.util.Iterator;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class StatementToLocationConvertor
{

    private Iterator<Statement> statIterator;
    private String prefix;
    private Actor actor;
    private Location startOrigin;
    private BaseComponent comp;
    
    public StatementToLocationConvertor(Queue<Statement> statements, Actor actor, Location origin, BaseComponent comp, String prefix)
    {
        this.actor = actor;
        this.statIterator = statements.iterator();
        this.startOrigin = origin;
        this.prefix = prefix;
        this.comp = comp;
    }
    
    public HybridTransition ConvertStatementChain()
    {
        return ConvertStatementChain(statIterator,startOrigin, prefix, 0);
    }
    
    private HybridTransition ConvertStatementChain(Iterator<Statement> statementIt,Location origin , String prefix, int i)
    {
        String locName = prefix+"_"+ String.valueOf(i);
        Location nextLoc = new Location(locName);
        
        if(statementIt.hasNext() == false)
            return new HybridTransition(origin, new HybridLabel(), startOrigin);
        
        Statement stat = statementIt.next();
        
        if(stat instanceof DelayStatement)
        {
            DelayStatement dStat = (DelayStatement) stat;
            String invarient = actor.GetDelayVariable().Name() + "<=" + String.valueOf(dStat.GetDelay());
            nextLoc.AddInvarient(new Invarient(invarient));
            
            HybridTransition nextTrans = ConvertStatementChain(statementIt,nextLoc, prefix, i+1);
            comp.AddTransition(nextTrans);
            
            nextTrans.GetLabel().AddGuard(actor.GetDelayVariable().Name() + "==" + String.valueOf(dStat.GetDelay()));
            
            HybridLabel label = new HybridLabel();
            label.AddAssignment(actor.GetDelayVariable().Name() + ":= 0");
            HybridTransition transition = new HybridTransition(origin, label, nextLoc);
            
            return transition;  
        }
        if(stat instanceof SendStatement)
        {
            SendStatement sStat = (SendStatement) stat;
            
            HybridTransition nextTrans = ConvertStatementChain(statementIt,nextLoc, prefix, i+1);
            comp.AddTransition(nextTrans);
            
            nextTrans.GetLabel().SetSyncLabel("Send_"+ sStat.GetReceiver().GetName() + "_" +sStat.GetMessage().toString());
            
            HybridTransition transition = new HybridTransition(origin, new HybridLabel(), nextLoc);
            
            return transition;  
            
        }
        
        
        return new HybridTransition(origin, new HybridLabel(), startOrigin);
    }   
    
}
