/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.SoftwareActor;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.Location;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorQueueCreator
{
    private abstract class QueueLocation
    {
        protected Location loc;
        private Queue<String> content = new LinkedList<>();
        protected ActorModelData actorData;
        
        public QueueLocation(String name, Queue<String> content, ActorModelData actorData)
        {
            this.content.addAll(content);
            loc = new Location(name);
            this.actorData = actorData;
        }
        
        public Location GetLoc()
        {
            return loc;
        }
        public Queue<String> GetContent()
        {
            return content;
        }
        
        public abstract void ProcessInTransition(QueueTransition transition);
        public abstract void PrcoessOutTransition(QueueTransition transition);
    }
    
    class IdleQueueLocation extends QueueLocation
    {

        public IdleQueueLocation(String name, ActorModelData actorData)
        {
            super(name, new LinkedList<String>(), actorData);
        }

        @Override
        public void ProcessInTransition(QueueTransition transition)
        {
        }

        @Override
        public void PrcoessOutTransition(QueueTransition transition)
        {
        }   
    }
    
    class UrgentQueueLocation extends QueueLocation
    { 
        public UrgentQueueLocation(Queue<String> content, ActorModelData actorData)
        {
            super(String.join("_", content)+"_1",content, actorData);
            loc.AddFlow(new Flow(actorData.GetUrgentFlow()));
            loc.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
        }
        
        @Override
        public void ProcessInTransition(QueueTransition transition)
        {
            transition.label.AddAssignment(actorData.GetUrgentReset());
        }

        @Override
        public void PrcoessOutTransition(QueueTransition transition)
        {
            if((transition.destination instanceof WaitingQueueLocation ) == false)
                transition.label.AddGuard(actorData.GetIsNotBusyGuard());
            
            transition.label.AddGuard(actorData.GetUrgentGuard());
        }
    }
    
    class WaitingQueueLocation extends QueueLocation
    {
        public WaitingQueueLocation(Queue<String> content, ActorModelData actorData)
        {
            super(String.join("_", content)+"_2",content, actorData);
            loc.AddInvarient(new Invarient(actorData.GetBusyInvarient()));
        }   

        @Override
        public void ProcessInTransition(QueueTransition transition)
        {
        }

        @Override
        public void PrcoessOutTransition(QueueTransition transition)
        {
            transition.label.AddGuard(actorData.GetIsNotBusyGuard());
        }
    }

    static class QueueTransition
    {
        public final QueueLocation origin;
        public final HybridLabel label;
        public final QueueLocation destination;
        
        public QueueTransition(QueueLocation origin, HybridLabel label, QueueLocation destination)
        {
            this.origin = origin;
            this.label = label;
            this.destination = destination;
        }
        
        public void Process(BaseComponent comp)
        {
            origin.PrcoessOutTransition(this);
            destination.ProcessInTransition(this);
            HybridTransition firstTrans = new HybridTransition(origin.GetLoc(), label, destination.GetLoc());
            comp.AddTransition(firstTrans);        
        }
    }
    
    private final BaseComponent comp;
    private final List<QueueLocation> queueLocs = new LinkedList<>();
    private final List<QueueTransition> queueTrans = new LinkedList<>();
    private final ActorModelData actorData;
    
    public ActorQueueCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
    }
    
    public void Create()
    {
        IdleQueueLocation idle = new IdleQueueLocation("idle", actorData);
        queueLocs.add(idle);
        comp.AddLocation(idle.GetLoc());
        ExpandQueue(1, idle, idle);
        
        AddTakeTransition();
        
        for(QueueTransition qTran : queueTrans)
            qTran.Process(comp);
    }
    
    private QueueLocation FindUrgentLocationWith(Queue<String> content)
    {
        for(QueueLocation qLoc : queueLocs)
            if((qLoc instanceof WaitingQueueLocation) == false && content.equals(qLoc.GetContent()))
                return qLoc;
        return null;
    }
     
    private void ExpandQueue(int cap, QueueLocation urgOrigin , QueueLocation waitOrigin)
    {
        if(cap == 0)
            return;
        
        for(String handler : actorData.GetHandlersName())
        {          
            Queue<String> content = new LinkedList<>(urgOrigin.GetContent());
            content.add(handler);
           
            UrgentQueueLocation urgLoc = new UrgentQueueLocation(content, actorData);
            WaitingQueueLocation waitLoc = new WaitingQueueLocation(content, actorData);
            queueLocs.add(urgLoc);
            queueLocs.add(waitLoc);
            
            queueTrans.add(CreateQueueUnitTransition(urgLoc, waitLoc));
            
            for(CommunicationLabel receive : actorData.GetReceiveLabelsFor(handler))
            {
                queueTrans.add(CreateTransition(urgOrigin, receive.GetLabel(), urgLoc));
                if(waitOrigin != urgOrigin)
                    queueTrans.add(CreateTransition(waitOrigin, receive.GetLabel(), urgLoc));
            }
            ExpandQueue(cap-1, urgLoc, waitLoc);     
        }
    }
    
    private void AddTakeTransition()
    {
        for(QueueLocation qLoc : queueLocs)
        {
            if(qLoc.content.isEmpty())
                continue;
            
            Queue<String> remaining = new LinkedList<>(qLoc.GetContent());
            String head = remaining.poll();
            
            QueueLocation takeLoc = FindUrgentLocationWith(remaining);
            queueTrans.add(CreateTransition(qLoc, actorData.CreateTakeLabel(head), takeLoc));   
        }
    }
    
    private QueueTransition CreateTransition(QueueLocation origin, String syncLabel, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.SetSyncLabel(syncLabel);
        return new QueueTransition(origin, label, destination);
    }
    
    private QueueTransition CreateQueueUnitTransition(UrgentQueueLocation urgent, WaitingQueueLocation waiting)
    {
        HybridLabel label = new HybridLabel();
        label.AddGuard(actorData.GetIsBusyGuard());
        return new QueueTransition(urgent, label, waiting);
    }
    
}
