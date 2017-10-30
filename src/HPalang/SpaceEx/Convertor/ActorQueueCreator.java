/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Convertor.Utilities.ProcessableTransitionBuilder;
import HPalang.Core.MessageHandler;
import HPalang.Core.VariableParameter;
import HPalang.SpaceEx.Convertor.QueueCreationUtilities.*;
import HPalang.SpaceEx.Convertor.Utilities.ProcessableTransition;
import static HPalang.SpaceEx.Convertor.Utilities.TransitionCreationUtilities.*;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.Core.ContinuousExpressions.Invarient;
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
    //private final List<QueueLocation> queueLocs = new LinkedList<>();
    private final List<ProcessableTransition> queueTrans = new LinkedList<>();
    private final ActorModelData actorData;
    private final ActorQueueData queueData;
    
    private QueueLocation initialQueueLocation_0;
    //private QueueLocation initialQueueLocation_1;

    
    public ActorQueueCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
        this.queueData = actorData.QueueData();
    }
    
    public void Create()
    {
        initialQueueLocation_0 = new IdleQueueLocation("initial_0", actorData);
        //initialQueueLocation_1 = new IdleQueueLocation("initial_1", actorData);

        AddLocation(initialQueueLocation_0);
        //AddLocation(initialQueueLocation_1);

        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true));
        comp.AddParameter(new RealParameter(actorData.BusyVar(), false));
        
        CreateMessageParameters();
        CreateBufferParameters();
        CreateQueueControlParameters();
        CreateQueueElementsParameters();
        CreateBufferProcessingLocations();

        CreateMessageProcessingLabels();
        
        CreateMessageProcessingLocations();
        
        for(ProcessableTransition qTran : queueTrans)
            qTran.Process(comp);
    }
    
    private void CreateMessageParameters()
    {
        for (String localVariable : actorData.MessageParameterNames())
            comp.AddParameter(new RealParameter(localVariable, false));    
        
    }
    private void CreateBufferParameters()
    {
        comp.AddParameter(new RealParameter(queueData.BufferIsFullVar(), false));
        comp.AddParameter(new RealParameter(queueData.BufferMessageVar(), false));
        for (VariableParameter parameter : actorData.MessageParameters()) {
            comp.AddParameter(new RealParameter(queueData.BufferParamaterVarFor(parameter), false));
        }
    }
    
    private void CreateQueueControlParameters()
    {
        comp.AddParameter(new RealParameter(queueData.QueueHeadVar(), false));
        comp.AddParameter(new RealParameter(queueData.QueueTailVar(), false));
        comp.AddParameter(new RealParameter(queueData.QueueSizeVar(), false));
    }
    
    private void CreateQueueElementsParameters()
    {
        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) {
            comp.AddParameter(new RealParameter(queueData.ElementMessageVar(i), false));
            for (VariableParameter parameter : actorData.MessageParameters()) {
                comp.AddParameter(new RealParameter(queueData.ElementParamterVarFor(parameter,i), false));
            }
        }
    }
    
    private void CreateBufferProcessingLocations()
    {
        QueueLocation bufferLoc_0 = new UrgentQueueLocation("bufferProc", actorData);
        
        //initialQueueLocation_1.GetLoc().AddInvarient( new Invarient(queueData.BufferIsEmptyInvarient()));
        //AddTransition(CreateGuardedTransition(initialQueueLocation_0, queueData.BufferIsEmptyGuard(), initialQueueLocation_1));
        AddTransition(CreateGuardedTransition(initialQueueLocation_0, queueData.BufferIsFullGuard(), bufferLoc_0));
        //AddTransition(CreateGuardedTransition(initialQueueLocation_1, queueData.BufferIsFullGuard(), bufferLoc_0));


        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) {
            QueueLocation bufferLoc_i = new UrgentQueueLocation(String.format("bufferProc_%d", i), actorData);
            AddBufferProcessTrantisionFor(bufferLoc_0, bufferLoc_i, i);
            
            AddTransition(CreateTransition(bufferLoc_i, initialQueueLocation_0));
        }
        
        QueueLocation bufferLoc_full = new UrgentQueueLocation("bufferProc_full", actorData);
        ProcessableTransition tr = CreateTransition(bufferLoc_0, bufferLoc_full);
        tr.label.AddGuard(queueData.QueueIsFullGuard())
                .AddAssignment(queueData.SetBufferEmptyAssignment());
        AddTransition(tr);
        AddTransition(CreateTransition(bufferLoc_full, initialQueueLocation_0));
        
    }
    
    private void AddBufferProcessTrantisionFor(QueueLocation previousLocation, QueueLocation currentLocation, int i)
    {
        ProcessableTransition tran = CreateTransition(previousLocation, currentLocation);
        tran.label.AddGuard(queueData.TailGuard(i));
        tran.label.AddGuard(queueData.QueueIsNotFullGuard());
        tran.label.AddAssignment(actorData.ResetFor(queueData.ElementMessageVar(i), queueData.BufferMessageVar()));
        tran.label.AddAssignment(queueData.SetBufferEmptyAssignment());
        tran.label.AddAssignment(queueData.TailIncrementAssignment(i));
        tran.label.AddAssignment(queueData.SizeIncrementAssignment());
        
        for(String assignment : queueData.BufferToElementAssignments(i))
            tran.label.AddAssignment(assignment);
        
        AddTransition(tran);
    }
    
    private void CreateMessageProcessingLabels()
    {
        //comp.AddParameter(new LabelParameter(queueData.TakeMessageLabel(), false));
        
        
        for(MessageHandler m : actorData.MessageHandlers())
            comp.AddParameter(new LabelParameter(actorData.ExecuteLabelFor(m), false));
    }
    
    private void CreateMessageProcessingLocations()
    {
        QueueLocation messageLoc_0 = new UrgentQueueLocation("messageProc", actorData);
        AddLocation(messageLoc_0);
        
        //initialQueueLocation_1.GetLoc().AddInvarient(new Invarient(queueData.QueueIsEmptyInvarianet()));
        
        
        
        //AddTransition(CreateGuardedTransition(initialQueueLocation_0, queueData.QueueIsEmptyGuard(), initialQueueLocation_1));

        AddTransition(new ProcessableTransitionBuilder().
                SetOrigin(initialQueueLocation_0).
                SetDestination(messageLoc_0).
                AddGuard(queueData.QueueIsNotEmptyGuard()).
                AddGuard(actorData.IsNotBusyGuard()).
                AddAssignment(actorData.SetBusyAssignment()).
                Build());
        //AddTransition(CreateGuardedAndLabledTransition(initialQueueLocation_0,queueData.QueueIsNotEmptyGuard(), queueData.TakeMessageLabel(), messageLoc_0));
        //AddTransition(CreateGuardedAndLabledTransition(initialQueueLocation_1,queueData.QueueIsNotEmptyGuard(), queueData.TakeMessageLabel(), messageLoc_0));
        
        for (int i = 0; i < actorData.Actor().QueueCapacity(); i++) 
        {
            for(MessageHandler messageHandler : actorData.MessageHandlers())
            {
                QueueLocation messageLoc_check_m = new UrgentQueueLocation(
                        String.format("messageProc_check_%d_%s", i, actorData.MessageHandlerName(messageHandler)),
                        actorData);

                AddTransition(new ProcessableTransitionBuilder().
                        SetOrigin(messageLoc_0).
                        SetDestination(messageLoc_check_m).
                        AddGuard(queueData.HeadGuard(i)).
                        AddGuard(queueData.ElementMessageGuard(messageHandler, i)).
                        AddGuard(queueData.QueueIsNotEmptyGuard()).
                        Build());
                
                QueueLocation messageLoc_unbox_m = new UrgentQueueLocation(
                        String.format("messageProc_unbox_%d_%s", i, actorData.MessageHandlerName(messageHandler)),
                        actorData);
                
                List<String> paramterUnboxingAssignments = queueData.ElementsToParametersAssignmentFor(messageHandler, i);
                
                AddTransition(new ProcessableTransitionBuilder().
                        SetOrigin(messageLoc_check_m).
                        SetDestination(messageLoc_unbox_m).
                        AddAssignments(paramterUnboxingAssignments).
                        Build());
                
                AddTransition(new ProcessableTransitionBuilder()
                        .SetOrigin(messageLoc_unbox_m)
                        .SetDestination(initialQueueLocation_0)
                        .SetSynclabel(actorData.ExecuteLabelFor(messageHandler))
                        .AddAssignment(queueData.HeadDecrementAssignment(i))
                        .AddAssignment(queueData.SizeDecrementAssignment())
                        .Build());
            }
        }
        
        
        

    }
    
    private void AddTransition(ProcessableTransition tran)
    {
        queueTrans.add(tran);
    }
    
    private void AddLocation(QueueLocation location)
    {
        comp.AddLocation(location.GetLoc());
    }
   
}
