/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.Parameter;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorConvertor
{   
    private final SpaceExModel model;
    private final ActorModelData actorData;
    
    private NetworkComponent actorComponent;
    
    public ActorConvertor(ActorModelData actorData, SpaceExModel model)
    {
        this.actorData = actorData;
        this.model = model;
    }
    
    
    public void Convert()
    {              
        actorComponent = new NetworkComponent(actorData.GetName()+"_Actor");
        
                
        for(ContinuousVariable var : actorData.GetContinuousVariables())
            actorComponent.AddParameter(new RealParameter(var.Name(), true));
        
        for(CommunicationLabel receive : actorData.GetReceiveLabels())
            actorComponent.AddParameter(new LabelParameter(receive.GetLabel(), receive.IsSelf()));
        
        for (CommunicationLabel sned : actorData.GetSendLables()) 
            actorComponent.AddParameter(new LabelParameter(sned.GetLabel(), sned.IsSelf()));
        
        
        for(CommunicationLabel send : actorData.GetSendLables())
            actorComponent.AddParameter(new LabelParameter(send.GetLabel(), send.IsSelf()));
        
        for (String take : actorData.GetHandlerTakeLabels()) 
            actorComponent.AddParameter(new LabelParameter(take, true));
        
        
        actorComponent.AddParameter(new RealParameter(actorData.GetBusyVar(), true));
        actorComponent.AddParameter(new RealParameter(actorData.GetLockVar(), true));

        
        BaseComponent handlers = CreateHandlers();
        BaseComponent queue = CreateQueue();

        BaseComponent vars = CreateVars(actorData);
        
        ComponentInstance varsInst = new ComponentInstance("vars", vars);
        for(Parameter param : vars.GetParameters())
            varsInst.SetBinding(param.GetName(), param.GetName());
        
        actorComponent.AddInstance(varsInst);
        
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
        {
            BaseComponent cbComp = CreateContinuousBehavior(cb);
            model.AddComponent(cbComp);
            
            ComponentInstance cbCompInst = new ComponentInstance("CB_" + actorData.GetIDFor(cb), cbComp);
            
            actorComponent.AddInstance(cbCompInst);
            
            actorComponent.AddParameter(new LabelParameter(actorData.GetStartLabelFor(cb), true));
            cbCompInst.SetBinding("Start", actorData.GetStartLabelFor(cb));
            for(Parameter param : cbComp.GetParameters())
            {
                if(param instanceof LabelParameter 
                        && param.GetName().equals("Start") == false)
                    cbCompInst.SetBinding(param.GetName(), param.GetName());
                else if(param instanceof RealParameter && param.IsLocal() == false)
                    cbCompInst.SetBinding(param.GetName(), param.GetName());
            }
        }
        
        Component varLockTempComp = CreateContinuousVariableLockTemplate();
        model.AddComponent(varLockTempComp);
        
        for(ContinuousVariable var : actorData.GetContinuousVariables())
        {
            ComponentInstance inst = new ComponentInstance(var.Name() +"_Lock", varLockTempComp);
            inst.SetBinding("var", var.Name());
            actorComponent.AddInstance(inst);
            String acquireLabel ="Acquire_" +  var.Name();
            String releaseLabel = "Release_"+ var.Name();
            actorComponent.AddParameter(new LabelParameter(acquireLabel, true));
            actorComponent.AddParameter(new LabelParameter(releaseLabel, true));
            
            inst.SetBinding("Acquire", acquireLabel);
            inst.SetBinding("Release", releaseLabel);
        }
        
        ComponentInstance queueInst = new ComponentInstance("queue",queue);
        for(CommunicationLabel receive : actorData.GetReceiveLabels())
        {
            if(receive.IsSelf() == false)
                queueInst.SetBinding(receive.GetLabel(), receive.GetLabel());            
            else
                queueInst.SetBinding(receive.GetLabel(), actorData.GetSelfSendLabelFor(receive).GetLabel());
        }
        
        for(String take : actorData.GetHandlerTakeLabels())
            queueInst.SetBinding(take, take);
        
        queueInst.SetBinding(actorData.GetBusyVar(), actorData.GetBusyVar());
        actorComponent.AddInstance(queueInst);

       
        ComponentInstance handlersInst = new ComponentInstance("handlers", handlers);
//        handlersInst.SetBinding(actorData.GetBusyVar(), actorData.GetBusyVar());
//        handlersInst.SetBinding(actorData.GetLockVar(), actorData.GetLockVar());
        for(Parameter param : handlers.GetParameters())
        {
            if(param.IsLocal() == false)
                handlersInst.SetBinding(param.GetName(), param.GetName());
            
        }
        
        
        actorComponent.AddInstance(handlersInst);
        
        model.AddComponent(handlers);
        model.AddComponent(vars);
        model.AddComponent(queue);
        model.AddComponent(actorComponent);
    }
    private Component CreateContinuousVariableLockTemplate()
    {
        BaseComponent comp = new BaseComponent(actorData.GetActor().GetName()+ "_VarLockTemplate");
        
        comp.AddParameter(new RealParameter("var", false));
        
        comp.AddParameter(new LabelParameter("Acquire", false));        
        comp.AddParameter(new LabelParameter("Release", false));

        Location unlock = new Location("unlock");
        comp.AddLocation(unlock);
        unlock.AddFlow(new Flow(" var' == 0"));

        Location lock = new Location("lock");
        comp.AddLocation(lock);

        comp.AddTransition(lock, new HybridLabel().SetSyncLabel("Release"), unlock);       
        comp.AddTransition(unlock, new HybridLabel().SetSyncLabel("Acquire"), lock);
        
        return comp;
    }
    
    private BaseComponent CreateContinuousBehavior(ContinuousBehavior cb)
    {
        BaseComponent comp = new BaseComponent(actorData.GetActor().GetName() +"_CB_" + actorData.GetIDFor(cb));
        
        String acquireLabel = "Acquire_" + cb.GetEquation().GetVariable().Name();
        String releaseLabel = "Release_" + cb.GetEquation().GetVariable().Name();
        
        comp.AddParameter(new LabelParameter("Start", false));
        comp.AddParameter(new LabelParameter(acquireLabel, false));
        comp.AddParameter(new LabelParameter(releaseLabel, false));
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true));       
        comp.AddParameter(new RealParameter(actorData.GetLockVar(), false));
        comp.AddParameter(new RealParameter(cb.GetEquation().GetVariable().Name(), false));
        
        for(CommunicationLabel send : actorData.GetSendLables())
            comp.AddParameter(new LabelParameter(send.GetLabel(), false));
        
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);

        Location acquireLockLoc = new Location("acquireLock");
        MakeLocationUrgent(acquireLockLoc, actorData);
        comp.AddLocation(acquireLockLoc);
        
        Location behaviorLoc = new Location("behvaior");        
        behaviorLoc.AddFlow(new Flow(cb.GetEquation()));
        behaviorLoc.AddInvarient(new Invarient(cb.GetInvarient()));
        comp.AddLocation(behaviorLoc);
        
        Location releaseLockLoc = new Location("releaseLock");
        MakeLocationUrgent(releaseLockLoc, actorData);
        comp.AddLocation(releaseLockLoc);
        
        comp.AddTransition(idleLoc, new HybridLabel().SetSyncLabel("Start").AddAssignment(actorData.GetUrgentReset()), acquireLockLoc);
        
        comp.AddTransition(acquireLockLoc, new HybridLabel().SetSyncLabel(acquireLabel).AddGuard(actorData.GetUrgentGuard()), behaviorLoc);
        
        comp.AddTransition(
                behaviorLoc, 
                new HybridLabel()
                .SetSyncLabel(releaseLabel)
                .AddGuard(cb.GetGuard())
                .AddAssignment(actorData.GetUrgentReset()), 
                releaseLockLoc);
        
        StatementToLocationConvertor convertor = new StatementToLocationConvertor(
                cb.GetActions(), 
                actorData, 
                releaseLockLoc, 
                comp, 
                "s");
        convertor.ConvertStatementChain(false);
        HybridTransition trans =  convertor.GetFirstTransition();
        trans.GetLabel().AddGuard(actorData.GetUrgentGuard());

        
        HybridTransition recurseTrans = new HybridTransition(convertor.GetLastLocation(), new HybridLabel(), idleLoc);
        
        convertor.ProcessLastLocation(recurseTrans.GetLabel());
        
        comp.AddTransition(recurseTrans);

        return comp;
    }
    
    private BaseComponent CreateHandlers()
    {
        BaseComponent comp = new BaseComponent(actorData.GetName()+"_Handlers");
        
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);
        
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true)); 
        comp.AddParameter(new RealParameter(actorData.GetLockVar(), false));
        comp.AddParameter(new RealParameter(actorData.GetDelayVar(), true));
        comp.AddParameter(new RealParameter(actorData.GetBusyVar(), false ));
        
        for(ContinuousVariable var : actorData.GetContinuousVariables())
            comp.AddParameter(new RealParameter(var.Name(), false));
        
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
            comp.AddParameter(new LabelParameter(actorData.GetStartLabelFor(cb), false));
        
        for(CommunicationLabel send : actorData.GetHandlersSendLables())
            comp.AddParameter(new LabelParameter(send.GetLabel(), false));
        
        for(MessageHandler handler : actorData.GetActor().GetMessageHandlers())
            CreateHandler(handler,comp, idleLoc);
        
        return comp;
    }
    
    private void CreateHandler(MessageHandler handler, BaseComponent comp, Location startLoc)
    {
        String takeLabel =  actorData.CreateTakeLabel(handler.GetID());
        comp.AddParameter(new LabelParameter(takeLabel, false));
        
        Location preLoc = new Location("pre_" + handler.GetID());
        MakeLocationUrgent(preLoc, actorData);
        
        HybridTransition trans = new HybridTransition(
                startLoc, 
                new HybridLabel().SetSyncLabel(takeLabel).AddAssignment(actorData.GetUrgentReset()), 
                preLoc);
        
        comp.AddTransition(trans);

        StatementToLocationConvertor statementsConvertor
                = new StatementToLocationConvertor(handler.GetBody(), actorData, preLoc, comp, handler.GetID());

        statementsConvertor.ConvertStatementChain(false);
        
        statementsConvertor.GetFirstTransition().GetLabel()
                .AddAssignment(actorData.GetBusyAssignment()).AddGuard(actorData.GetUrgentGuard());
        
        HybridTransition recursTrans =  new HybridTransition(statementsConvertor.GetLastLocation(), new HybridLabel(), startLoc);
        statementsConvertor.ProcessLastLocation(recursTrans.GetLabel());
        recursTrans.GetLabel().AddAssignment(actorData.GetUnBusyAssignment());
        
        comp.AddTransition(recursTrans);
    }

    private BaseComponent CreateVars(ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetName() + "_DVars");
        comp.AddParameter(new RealParameter(actorData.GetBusyVar(), false));   
        comp.AddParameter(new RealParameter(actorData.GetLockVar(), false));
        
        Location location = new Location("loc1");
        location.AddFlow(new Flow(actorData.GetBusyVar() + "' == 0"));
        location.AddFlow(new Flow(actorData.GetLockVar() + "' == 0"));        
       
        
        for (DiscreteVariable var : actorData.GetDiscreteVaraible()) 
        {
            comp.AddParameter(new RealParameter(var.Name(), false));
            location.AddFlow(new Flow(var.Name() + "' == 0"));
        }
        
        comp.AddLocation(location);
        
        return comp;
        
    }
    
    private BaseComponent CreateQueue()
    {
        BaseComponent comp = new BaseComponent(actorData.GetName()+"_Queue");
        
        comp.AddParameter(new RealParameter(actorData.GetBusyVar(), false));
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true));

        
        for(CommunicationLabel label : actorData.GetReceiveLabels())
            comp.AddParameter(new LabelParameter(label.GetLabel(), false));
        
        for(String label : actorData.GetHandlersName())
            comp.AddParameter(new LabelParameter(actorData.CreateTakeLabel(label), false));
        
        new ActorQueueCreator(comp, actorData).Create();
        
        return comp;
    }
    
    
    private void MakeLocationUrgent(Location location, ActorModelData actorData)
    {
        location.AddFlow(new Flow(actorData.GetUrgentFlow()));
        location.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
    }

    public NetworkComponent GetActorComponent()
    {
        return actorComponent;
    }
    
    
}
