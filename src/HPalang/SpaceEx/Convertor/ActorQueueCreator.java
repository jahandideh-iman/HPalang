/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
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
    static class QueueLocation
    {
        private Queue<String> content = new LinkedList<>();
        
        private Location loc1;
        private Location loc2;
        
        public QueueLocation(Queue<String> content)
        {
            this.content.addAll(content);
            
            if (content.isEmpty())
                loc1 = new Location("idle");
            else 
            {
                String preFix = String.join("_", content);
                loc1 = new Location(preFix + "_1");
                loc2 = new Location(preFix + "_2");
            }
            
        }
              
        public Queue<String> GetContent()
        {
            return content;
        }
        
        public void AddTo(BaseComponent comp)
        {
            comp.AddLocation(loc1);
            if (loc2 != null) 
            {
                comp.AddLocation(loc2);
                comp.AddTransition(new HybridTransition(loc1, new HybridLabel().AddGuard("actorBusy == 0"), loc2));
            }        
        }
        
        public Location GetLoc1()
        {
            return loc1;
        }
        
        public Location GetLoc2()
        {
            return loc2;
        }
    }
    
    static class QueueTransition
    {
        private QueueLocation origin;
        private String label;
        private QueueLocation destination;
        
        public QueueTransition(QueueLocation origin, String label, QueueLocation destination)
        {
            this.origin = origin;
            this.label = label;
            this.destination = destination;
        }
        
        public void AddTo(BaseComponent comp)
        {
            comp.AddTransition(
                    new HybridTransition(origin.GetLoc1()
                            , new HybridLabel().SetSyncLabel(label)
                            , destination.GetLoc1()));
            if(origin.GetLoc2()!= null)
                comp.AddTransition(
                        new HybridTransition(origin.GetLoc2(),
                                 new HybridLabel().SetSyncLabel(label),
                                 destination.GetLoc1()));
            
        }
    }
    
    private BaseComponent comp;
    private List<QueueLocation> queueLocs = new LinkedList<>();
    private List<QueueTransition> queueTrans = new LinkedList<>();
    private ActorModelData actorData;
    
    public ActorQueueCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
    }
    
    public void Create()
    {
        
        QueueLocation idle = new QueueLocation(new LinkedList<>());
        queueLocs.add(idle);
        
        ExpandQueue(1, idle);
        
        for(QueueLocation qLoc : queueLocs)
            qLoc.AddTo(comp);
        
        for(QueueLocation qLoc : queueLocs)
        {
            if(qLoc.content.isEmpty())
                continue;
            
            Queue<String> remaining = new LinkedList<>(qLoc.GetContent());
            String head = remaining.poll();
            
            QueueLocation takeLoc = FindLocationWith(remaining);
            
            queueTrans.add(new QueueTransition(qLoc, actorData.CreateTakeLabel(head), takeLoc));
            
        }
                
        for(QueueTransition qTrans : queueTrans)
            qTrans.AddTo(comp);
        

    }
   
    private QueueLocation FindLocationWith(Queue<String> content)
    {
        for(QueueLocation qLoc : queueLocs)
            if(content.equals(qLoc.GetContent()))
                return qLoc;
        return null;
    }
    private void ExpandQueue(int cap, QueueLocation origin /* ,Map<String,Location> takesLocation*/)
    {
        if(cap == 0)
            return;
        
        for(String handler : actorData.GetHandlersName())
        {          
            Queue<String> content = new LinkedList<>(origin.GetContent());
            content.add(handler);

           
            QueueLocation loc = new QueueLocation(content);

            queueLocs.add(loc);
    
            for(String recieve : actorData.GetReceiveLabelsFor(handler))
                queueTrans.add(CreateTransition(origin, recieve, loc));
            
            ExpandQueue(cap-1, loc);     
        }
    }
    
    
    
    private QueueTransition CreateTransition(QueueLocation origin, String label, QueueLocation destination)
    {
        return new QueueTransition(origin, label, destination);
    }
    
}
