/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.MessageHandler;
import HPalang.Core.Variable;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.HybridTransitionBuilder;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.RealParameter;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorHandlersCreator
{
    private final BaseComponent comp;
    private final ActorModelData actorData;
    
    private Location idleLoc;
    
    public ActorHandlersCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
    }

    public void Create()
    {
        idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);
        
        //comp.AddParameter(new LabelParameter(actorData.ReadyLabel(), false));        
        comp.AddParameter(new RealParameter(actorData.DelayVar(), true));
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true)); 
        comp.AddParameter(new RealParameter(actorData.BusyVar(),false));
        for(Variable variable : actorData.InstanceVariables())
            comp.AddParameter(new RealParameter(variable.Name(), false));
        
        for(Variable var: actorData.InstanceVariables())
            comp.AddParameter(new RealParameter(var.Name(), false));
        
        for (String var : actorData.MessageParameterNames()) 
            comp.AddParameter(new RealParameter(var, false));
        
        for(String var : actorData.AllSendVariables())
            comp.AddParameter(new RealParameter(var, false));
        
        for (String label : actorData.ExecuteMessageLabels()) {
            comp.AddParameter(new LabelParameter(label, false));
        }
        
//        for (String label : actorData.QueueData().SendBufferLabels()) {
//            comp.AddParameter(new LabelParameter(label, false));
//        }
        
        AddHandlersProcessing();
    }
    
    private void AddHandlersProcessing()
    {

//        Location handlerProc_0 = new Location("handlerProc_0");
//        
//        comp.AddTransition(new HybridTransitionBuilder().
//                SetOrigin(idleLoc).
//                SetDestination(handlerProc_0).
//                SetSynclabel(actorData.QueueData().TakeMessageLabel()).
//                Build());
        
        for(MessageHandler handler : actorData.MessageHandlers())
        {
            Location handlerProc_h = new Location(String.format("handlerProc_%s", actorData.MessageHandlerName(handler)));
                        handlerProc_h.AddFlow(new Flow(actorData.GetUrgentFlow()));
            handlerProc_h.AddInvarient(actorData.GetUrgentInvarient());
            comp.AddTransition(new HybridTransitionBuilder().
                    SetOrigin(idleLoc).
                    SetDestination(handlerProc_h).
                    SetSynclabel(actorData.ExecuteLabelFor(handler)).
 //                   AddAssignment(actorData.SetBusyAssignment()).
                    Build());
            
            CreateHandler(handler, comp, handlerProc_h);
        }
    }

    private void CreateHandler(MessageHandler handler, BaseComponent comp, Location startLoc)
    {
        StatementToLocationConvertor statementsConvertor = new StatementToLocationConvertor(
                handler.GetBody(), 
                actorData, 
                startLoc, 
                comp, 
                actorData.MessageHandlerName(handler));

        statementsConvertor.ConvertStatementChain(false);
        
        statementsConvertor.GetFirstTransition().GetLabel().AddGuard(actorData.GetUrgentGuard());

        
        HybridTransition recursTrans  = new HybridTransition(statementsConvertor.GetLastLocation(), new HybridLabel(), idleLoc, false);
        recursTrans.GetLabel().AddAssignment(actorData.SetNotBusyAssignment());
        //recursTrans.GetLabel().SetSyncLabel(actorData.ReadyLabel());
        statementsConvertor.ProcessLastLocation(recursTrans.GetLabel());
        
        comp.AddTransition(recursTrans);
    }


}
