/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.Location;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class StatementToLocationConvertor
{  
    private abstract class StatementLocation
    {
        protected Location loc;
        
        public Location GetLoc()
        {
            return loc;
        }
        
        public abstract void ProcessInLabel(HybridLabel label);
        public abstract void ProcessOutLabel(HybridLabel label);
    }
    
    private class StartLocation extends UrgentLocation
    {

        public StartLocation(String name, ActorModelData actorData)
        {
            super(name, actorData);
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            super.ProcessOutLabel(label);
            label.AddGuard(actorData.GetLockGainGuard());
            label.AddAssignment(actorData.GetLockGainReset());
        }
        
    }
    
    private class EndLocation extends UrgentLocation
    {
        public EndLocation(String name, ActorModelData actorData)
        {
            super(name, actorData);
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            super.ProcessOutLabel(label);
            label.AddAssignment(actorData.GetLockReleaseReset());
        }
        
        
    }
    
    private class NotImplementedLocation extends StatementLocation
    {
        
        public NotImplementedLocation(Statement statement, String name)
        {
            loc = new Location(name+"_NotImplementated");
        }

        @Override
        public void ProcessInLabel(HybridLabel label)
        {
            
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            
        }
        
    }
   
    private abstract class UrgentLocation extends StatementLocation
    {
        private String urgGaurd;
        private String urgReset;
        protected ActorModelData actorData;
        
        public UrgentLocation(String name, ActorModelData actorData)
        {
            this.urgGaurd = actorData.GetUrgentGuard();
            this.urgReset = actorData.GetUrgentReset();
            this.actorData = actorData;
            
            this.loc = new Location(name);
            loc.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
            loc.AddFlow(new Flow(actorData.GetUrgentFlow()));
        }

        @Override
        public void ProcessInLabel(HybridLabel label)
        {
            label.AddAssignment(urgReset);
        }
        
        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            label.AddGuard(urgGaurd);
        }
    }
    private class DelayLocation extends StatementLocation
    {
        private DelayStatement statement;
        private String delayVar;
        public DelayLocation(DelayStatement statement, String name, ActorModelData actorData)
        {
            this.statement = statement;
            this.loc = new Location(name);
            this.delayVar =actorData.GetActor().GetDelayVariable().Name(); 
            String invarient = delayVar + "<=" + String.valueOf(statement.GetDelay());  

            loc.AddInvarient(new Invarient(invarient));
        }

        @Override
        public void ProcessInLabel(HybridLabel label)
        {
            label.AddAssignment(delayVar+" := 0");
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            label.AddGuard(delayVar + " == " + String.valueOf(statement.GetDelay()));
        }
    }
    
    private class CAssignmentLocation extends UrgentLocation
    {
        private ContinuousAssignmentStatement statement;
        
   
        public CAssignmentLocation(ContinuousAssignmentStatement statement, String name, ActorModelData actorData)
        {
            super(name, actorData);
            this.statement = statement;         
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            super.ProcessOutLabel(label);
            label.AddAssignment(statement.Variable() +" := " + statement.Expression().toString());
        }
    }
    
    private class CBehaviorLocation extends UrgentLocation
    {
        private ContinuousBehaviorStatement statement;

        private String behaviorLabel;
        
        public CBehaviorLocation(ContinuousBehaviorStatement statement, String name, ActorModelData actorData)
        {
            super(name, actorData);
            this.statement = statement;
            this.behaviorLabel = actorData.GetStartLabelFor(statement.GetBehavior());        
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
           super.ProcessOutLabel(label);
           label.SetSyncLabel(behaviorLabel);
        }    
    }
    
    private class SendLocation extends UrgentLocation
    {
        private SendStatement statement;
        
        private String sendLabel;
        
        public SendLocation(SendStatement statement,String name, ActorModelData actorData)
        {
            super(name, actorData);
            this.statement = statement;
            this.sendLabel = actorData.GetSendLabelFor(statement).GetLabel();
        }

        @Override
        public void ProcessOutLabel(HybridLabel label)
        {
            super.ProcessOutLabel(label);
            label.SetSyncLabel(sendLabel);
        }
        
        
    }
                
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
    
    private final List<StatementLocation> statLocations = new LinkedList<>();   
    private final List<StatementTransition> statTransitions = new LinkedList<>();

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
            HybridTransition trans = HybridTransition.CreateEmpty(endLocation.GetLoc(), startOrigin);
            endLocation.ProcessOutLabel(trans.GetLabel());
            comp.AddTransition(trans);
        }
    }
    
    public HybridTransition GetFirstTransition()
    {
        HybridLabel label = new HybridLabel();
        HybridTransition trans = new HybridTransition(startOrigin, label, startLocation.GetLoc());
        
        startLocation.ProcessInLabel(label);
        
        comp.AddTransition(trans);
                
        return trans;
    }
    
    public Location GetLastLocation()
    {
        return endLocation.GetLoc();
    }
    
    void ProcessLastLocation(HybridLabel label)
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
        
        Statement stat = statementIt.next();

        StatementLocation loc = new NotImplementedLocation(stat, locName);
        
        if(stat instanceof DelayStatement)
            loc = new DelayLocation((DelayStatement) stat, locName, actorData);
        else if(stat instanceof ContinuousAssignmentStatement)
            loc = new CAssignmentLocation((ContinuousAssignmentStatement) stat,locName, actorData);
        else if(stat instanceof ContinuousBehaviorStatement)
            loc = new CBehaviorLocation((ContinuousBehaviorStatement) stat, locName, actorData);
        else if(stat instanceof SendStatement)
            loc = new SendLocation((SendStatement) stat, locName, actorData);

        statLocations.add(loc);
        statTransitions.add(new StatementTransition(origin, loc));
        ConvertStatementChain(statementIt, loc, prefix, i+1);
    }   
}
