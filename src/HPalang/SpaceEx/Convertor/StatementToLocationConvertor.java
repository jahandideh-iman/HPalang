/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Pair;
import HPalang.Core.Statement;
import HPalang.Core.Statements.*;
import HPalang.SpaceEx.Convertor.StatementConversionUtilities.*;
import HPalang.SpaceEx.Core.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class StatementToLocationConvertor
{    


    private class StatementTransition
    {
        public StatementLocation origin;
        public StatementLocation destination;
        
        public HybridTransition transition;
        
        public StatementTransition(StatementLocation origin, StatementLocation destination)
        {
            this.origin = origin;
            this.destination = destination;
        }
        
        public void Process(BaseComponent comp)
        {
            HybridLabel label = new HybridLabel();
            transition = new HybridTransition(origin.GetLoc(), label, destination.GetLoc());
            
            origin.ProcessOutLabel(label);
            destination.ProcessInLabel(label);
            
            comp.AddTransition(transition);
        } 
    }
    
    private final Iterator<Statement> statIterator;
    private final String prefix;
    private final ActorModelData actorData;
    private final Location startOrigin;
    private final BaseComponent comp;
    private StatementLocation startLocation;
    private StatementLocation endLocation;
    
    private final Set<StatementLocation> statLocations = new HashSet<>();   
    private final Set<StatementTransition> statTransitions = new HashSet<>();
    
    private HybridTransition lastTransition;

    public StatementToLocationConvertor(Queue<Statement> statements, ActorModelData actorData, Location origin, BaseComponent comp, String prefix)
    {
        this.actorData = actorData;
        this.statIterator = statements.iterator();
        this.startOrigin = origin;
        this.prefix = prefix;
        this.comp = comp;
    }
    
    public void ConvertStatementChain(boolean recurse)
    {
        startLocation = new StartLocation(prefix+"_0", actorData);
        statLocations.add(startLocation);
        ConvertStatementChain(statIterator,startLocation, prefix, 1);
        
        for(StatementTransition tran : statTransitions)
            tran.Process(comp);
        
        if(recurse)
        {
            lastTransition = HybridTransition.CreateEmpty(endLocation.GetLoc(), startOrigin);
            endLocation.ProcessOutLabel(lastTransition.GetLabel());
            comp.AddTransition(lastTransition);
        }
    }
    
    
    // TODO: What will happen if this function is not called?
    public HybridTransition GetFirstTransition()
    {
        HybridLabel label = new HybridLabel();
        HybridTransition trans = new HybridTransition(startOrigin, label, startLocation.GetLoc());
        
        startLocation.ProcessInLabel(label);
        
        comp.AddTransition(trans);
                
        return trans;
    }
    
    public HybridTransition GetLastTransition()
    {
        return lastTransition;
    }
    
    public Location GetLastLocation()
    {
        return endLocation.GetLoc();
    }
    
    public void ProcessLastLocation(HybridLabel label)
    {
        endLocation.ProcessOutLabel(label);
    }
    
    private void ConvertStatementChain(Iterator<Statement> statementIt,StatementLocation origin , String prefix, int i)
    {
        String locName = prefix+"_"+ String.valueOf(i);
                
        if(statementIt.hasNext() == false)
        {
            endLocation = new EndLocation(locName, actorData);
            statTransitions.add(new StatementTransition(origin, endLocation));
            return;
        }
        
        Statement statement = statementIt.next();
        Pair<StatementLocation,StatementLocation> statementResult;
        
        if(statement instanceof DelayStatement)
            statementResult = CreateDelayStatementLocations(statement, locName);
        else if(statement instanceof AssignmentStatement)
            statementResult = CreateAssignmentLocation(statement, locName);
        else if(statement instanceof IfStatement)
            statementResult = CreateContidationlLocations((IfStatement)statement, locName);
        else if(statement instanceof SendStatement)
           statementResult = CreateSendLocation(statement, locName);
        else 
            statementResult = CreateNotImplementationLocation(statement, locName);

        statTransitions.add(new StatementTransition(origin, statementResult.A()));
        ConvertStatementChain(statementIt, statementResult.B(), prefix, i+1);
    }  
    
    private Pair<StatementLocation,StatementLocation> CreateDelayStatementLocations(Statement statement, String name)
    {
        DelayLocation loc = new DelayLocation((DelayStatement) statement, name, actorData);
        return new Pair<>(loc,loc);
    }
    
    private Pair<StatementLocation,StatementLocation> CreateNotImplementationLocation(Statement statement, String locName)
    {
        StatementLocation loc = new NotImplementedLocation(statement, locName);
        return new Pair<>(loc,loc);

    }
    
    private Pair<StatementLocation,StatementLocation> CreateAssignmentLocation(Statement statement, String locName)
    {
        StatementLocation loc = new AssignmentLocation((AssignmentStatement) statement,locName, actorData);
        return new Pair<>(loc,loc);
    }
    
    private Pair<StatementLocation, StatementLocation> CreateSendLocation(Statement statement, String locName)
    {
        StatementLocation loc = new SendLocation((SendStatement) statement, locName, actorData);
        return new Pair<>(loc,loc);
    }

    
    private Pair<StatementLocation,StatementLocation> CreateContidationlLocations(IfStatement statement, String locName)
    {
        StartLocation conditional_startLocation = new StartLocation(locName+"_start", actorData);
        EndLocation conditional_endLocation = new EndLocation(locName + "_end", actorData);
        
        StatementToLocationConvertor truePathConvertor =  new StatementToLocationConvertor(
                statement.TrueStatements(), 
                actorData, 
                conditional_startLocation.GetLoc(),
                comp, 
                locName);
        truePathConvertor.ConvertStatementChain(false);
        
        statTransitions.add(new StatementTransition(truePathConvertor.startLocation, conditional_startLocation));
        statTransitions.add(new StatementTransition(truePathConvertor.endLocation, conditional_endLocation));

        
        StatementToLocationConvertor falsePathConvertor = new StatementToLocationConvertor(
                statement.FalseStatements(),
                actorData,
                conditional_startLocation.GetLoc(),
                comp,
                locName);
        falsePathConvertor.ConvertStatementChain(false);

        statTransitions.add(new StatementTransition(falsePathConvertor.startLocation, conditional_startLocation));
        statTransitions.add(new StatementTransition(falsePathConvertor.endLocation, conditional_endLocation));
        
        statLocations.add(endLocation);
        
        return new Pair<>(conditional_startLocation,conditional_endLocation);
    }
}
