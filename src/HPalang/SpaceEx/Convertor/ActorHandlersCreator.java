/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.MessageHandler;
import HPalang.Core.Variable;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.HybridTransitionBuilder;
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
        
        comp.AddParameter(new RealParameter(actorData.DelayVar(), true));
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true)); 
        comp.AddParameter(new RealParameter(actorData.GetLockVar(), false));
        comp.AddParameter(new RealParameter(actorData.GetBusyVar(), false ));
        comp.AddParameter(new LabelParameter(actorData.QueueData().TakeMessageLabel(), false));
        
        for(Variable var: actorData.Variables())
            comp.AddParameter(new RealParameter(var.Name(), false));
        
        AddHandlersProcessing();
    }
    
    private void AddHandlersProcessing()
    {

        Location handlerProc_0 = new Location("handlerProc_0");
        
        comp.AddTransition(new HybridTransitionBuilder().
                SetOrigin(idleLoc).
                SetDestination(handlerProc_0).
                SetSynclabel(actorData.QueueData().TakeMessageLabel()).
                Build());
        
        for(MessageHandler handler : actorData.MessageHandlers())
        {
            Location handlerProc_h = new Location(String.format("handlerProc_%s", actorData.MessageHandlerName(handler)));
            
            comp.AddTransition(new HybridTransitionBuilder().
                    SetOrigin(handlerProc_0).
                    SetDestination(handlerProc_h).
                    SetSynclabel(actorData.ExecuteLabelFor(handler)).
                    Build());
            
            CreateHandler(handler, comp, handlerProc_h);
        }
    }

    private void CreateHandler(MessageHandler handler, BaseComponent comp, Location startLoc)
    {
        //String takeLabel =  actorData.TakeLabelFor(handler);
        //comp.AddParameter(new LabelParameter(takeLabel, false));
        
//        Location preLoc = new Location("pre_" + handler.GetID());
//        //MakeLocationUrgent(preLoc, actorData);
//        
//        HybridTransition trans = new HybridTransition(
//                startLoc, 
//                new HybridLabel().SetSyncLabel(takeLabel).AddAssignment(actorData.GetUrgentReset()), 
//                preLoc);
//        
//        comp.AddTransition(trans);

        StatementToLocationConvertor statementsConvertor = new StatementToLocationConvertor(
                handler.GetBody(), 
                actorData, 
                startLoc, 
                comp, 
                actorData.MessageHandlerName(handler));

        statementsConvertor.ConvertStatementChain(false);
        
        statementsConvertor.GetFirstTransition().GetLabel()
                .AddAssignment(actorData.GetBusyAssignment()).AddGuard(actorData.GetUrgentGuard());
        
        HybridTransition recursTrans =  new HybridTransition(statementsConvertor.GetLastLocation(), new HybridLabel(), idleLoc);
        statementsConvertor.ProcessLastLocation(recursTrans.GetLabel());
        recursTrans.GetLabel().AddAssignment(actorData.GetUnBusyAssignment());
        
        comp.AddTransition(recursTrans);
    }


}
