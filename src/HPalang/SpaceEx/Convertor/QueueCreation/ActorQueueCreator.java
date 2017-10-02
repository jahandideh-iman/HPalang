/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreation;

import HPalang.Core.MessageHandler;
import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Convertor.ActorQueueData;
import HPalang.SpaceEx.Convertor.CommunicationLabel;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.RealParameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorQueueCreator
{
    private final BaseComponent comp;
    private final List<QueueLocation> queueLocs = new LinkedList<>();
    private final List<QueueTransition> queueTrans = new LinkedList<>();
    private final ActorModelData actorData;
    private final ActorQueueData queueData;
    
    private IdleQueueLocation initialQueueLocation;
    
    public ActorQueueCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
        this.queueData = actorData.QueueData();
    }
    
    public void Create()
    {
        initialQueueLocation = new IdleQueueLocation("idle", actorData);
        queueLocs.add(initialQueueLocation);
        comp.AddLocation(initialQueueLocation.GetLoc());
        
        CreateBufferParameters();
        CreateQueueControlParameters();
        CreateQueueElementsParameters();
        CreateBufferProcessingLocations();

        CreateMessageProcessingLabels();
        
        CreateMessageProcessingLocations();
        for(QueueTransition qTran : queueTrans)
            qTran.Process(comp);
    }
    
    
    private void CreateBufferParameters()
    {
        comp.AddParameter(new RealParameter(queueData.BufferIsEmptyVar(), false));
        comp.AddParameter(new RealParameter(queueData.BufferValueVar(), false));
        comp.AddParameter(new RealParameter(queueData.BufferMessageVar(), false));
    }
    
    private void CreateQueueControlParameters()
    {
        comp.AddParameter(new RealParameter(queueData.QueueHeadVar(), true));
        comp.AddParameter(new RealParameter(queueData.QueueTailVar(), true));
        comp.AddParameter(new RealParameter(queueData.QueueSizeVar(), true));
    }
    
    private void CreateQueueElementsParameters()
    {
        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) {
            comp.AddParameter(new RealParameter(queueData.ElementMessageVar(i), true));
            comp.AddParameter(new RealParameter(queueData.ElementValueVar(i), true));
        }
    }
    
    private void CreateBufferProcessingLocations()
    {
        QueueLocation bufferLoc_0 = new UrgentQueueLocation("bufferProc", actorData);
        AddTransition(CreateGuardedTransition(initialQueueLocation, queueData.BufferIsFullGuard(), bufferLoc_0));
        
        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) {
            QueueLocation bufferLoc_i = new UrgentQueueLocation(String.format("bufferProc_%d", i), actorData);
            AddBufferProcessTrantisionFor(bufferLoc_0, bufferLoc_i, i);
            
            AddTransition(CreateTransition(bufferLoc_i, initialQueueLocation));
        }
        
        QueueLocation bufferLoc_full = new UrgentQueueLocation("bufferProc_full", actorData);
        QueueTransition tr = CreateTransition(bufferLoc_0, bufferLoc_full);
        tr.label.AddGuard(queueData.QueueIsFullGuard())
                .AddAssignment(queueData.SetBufferEmptyAssignment());
        AddTransition(tr);
        AddTransition(CreateTransition(bufferLoc_full, initialQueueLocation));
        
    }
    
    private void AddBufferProcessTrantisionFor(QueueLocation previousLocation, QueueLocation currentLocation, int i)
    {
        QueueTransition tran = CreateTransition(previousLocation, currentLocation);
        tran.label.AddGuard(queueData.TailGuard(i));
        tran.label.AddAssignment(String.format("%s := %s", queueData.ElementValueVar(i), queueData.BufferValueVar()));
        tran.label.AddAssignment(String.format("%s := %s", queueData.ElementMessageVar(i), queueData.BufferMessageVar()));
        tran.label.AddAssignment(queueData.SetBufferEmptyAssignment());
        tran.label.AddAssignment(queueData.TailIncrementAssignment(i));
        tran.label.AddAssignment(queueData.SizeIncrementAssignment());
        
        AddTransition(tran);
    }
    
    private void CreateMessageProcessingLabels()
    {
        comp.AddParameter(new LabelParameter(queueData.TakeMessageLabel(), false));
        for(MessageHandler m : actorData.MessageHandlers())
            comp.AddParameter(new LabelParameter(actorData.ExecuteLabelFor(m), false));
    }
    
    private void CreateMessageProcessingLocations()
    {
        QueueLocation messageLoc_0 = new UrgentQueueLocation("messageProc", actorData);
        AddLocation(messageLoc_0);
        
        AddTransition(CreateTransition(initialQueueLocation, messageLoc_0));
        
        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) 
        {
            for(MessageHandler messageHandler : actorData.MessageHandlers())
            {
                QueueLocation messageLoc_check_m = new UrgentQueueLocation(
                        String.format("messageProc_check_%d_%s", i, actorData.MessageHandlerName(messageHandler)),
                        actorData);

                AddTransition(new QueueTransitionBuilder().
                        SetOrigin(messageLoc_0).
                        SetDestination(messageLoc_check_m).
                        AddGuard(queueData.HeadGuard(i)).
                        AddGuard(queueData.ElementMessageGuard(messageHandler, i)).
                        AddGuard(queueData.QueueIsNotEmptyGuard()).
                        Build());
                
                QueueLocation messageLoc_unbox_m = new UrgentQueueLocation(
                        String.format("messageProc_unbox_%d_%s", i, actorData.MessageHandlerName(messageHandler)),
                        actorData);
                
                AddTransition(new QueueTransitionBuilder().
                        SetOrigin(messageLoc_check_m).
                        SetDestination(messageLoc_unbox_m).
                        Build());
                
                AddTransition(new QueueTransitionBuilder()
                        .SetOrigin(messageLoc_unbox_m)
                        .SetDestination(initialQueueLocation)
                        .SetSynclabel(actorData.ExecuteLabelFor(messageHandler))
                        .AddAssignment(queueData.HeadDecrementAssignment(i))
                        .Build());
            }
        }
        

    }
    
    private void AddTransition(QueueTransition tran)
    {
        queueTrans.add(tran);
    }
    
    private void AddLocation(QueueLocation location)
    {
        queueLocs.add(location);
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
//        if(cap == 0)
//            return;
//        
//        for(MessageHandler handler : actorData.MessageHandlers())
//        {          
//            Queue<String> content = new LinkedList<>(urgOrigin.GetContent());
//            content.add(handler.GetID());
//           
//            UrgentQueueLocation urgLoc = new UrgentQueueLocation(content, actorData);
//            WaitingQueueLocation waitLoc = new WaitingQueueLocation(content, actorData);
//            queueLocs.add(urgLoc);
//            queueLocs.add(waitLoc);
//            
//            queueTrans.add(CreateQueueUnitTransition(urgLoc, waitLoc));
//            
//            for(CommunicationLabel receive : actorData.GetReceiveLabelsFor(handler))
//            {
//                queueTrans.add(CreateTransition(urgOrigin, receive.GetLabel(), urgLoc));
//                if(waitOrigin != urgOrigin)
//                    queueTrans.add(CreateTransition(waitOrigin, receive.GetLabel(), urgLoc));
//            }
//            ExpandQueue(cap-1, urgLoc, waitLoc);     
//        }
    }
    
    private void AddTakeTransition()
    {
//        for(QueueLocation qLoc : queueLocs)
//        {
//            if(qLoc.content.isEmpty())
//                continue;
//            
//            Queue<String> remaining = new LinkedList<>(qLoc.GetContent());
//            String head = remaining.poll();
//            
//            QueueLocation takeLoc = FindUrgentLocationWith(remaining);
//            queueTrans.add(CreateTransition(qLoc, actorData.TakeLabelFor(head), takeLoc));   
//        }
    }
    
    private QueueTransition CreateTransition(QueueLocation origin, String syncLabel, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.SetSyncLabel(syncLabel);
        return new QueueTransition(origin, label, destination);
    }
    
    private QueueTransition CreateTransition(QueueLocation origin , QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        return new QueueTransition(origin, label, destination);
    }
    
    private QueueTransition CreateGuardedTransition(QueueLocation origin, String guard, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.AddGuard(guard);
        return new QueueTransition(origin, label, destination);
    }
    
    private QueueTransition CreateQueueUnitTransition(UrgentQueueLocation urgent, WaitingQueueLocation waiting)
    {
        HybridLabel label = new HybridLabel();
        label.AddGuard(actorData.GetIsBusyGuard());
        return new QueueTransition(urgent, label, waiting);
    }
    
}
