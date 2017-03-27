/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.ProgramDefinition;
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
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class HPalangToCompositionalSXConvertor
{
    SpaceExModel model = new SpaceExModel();
    HPalangModelData hpalangModelData;

    
    public void Convert(ProgramDefinition def)
    {
        hpalangModelData = new HPalangModelData(def);
        for(Actor actor : def.GetActors())
            Convert(hpalangModelData.GetActorData(actor));
        
        NetworkComponent system = CreateSystem();
        model.AddComponent(system);
        
       
    }
    
    private NetworkComponent CreateSystem()
    {
        return new NetworkComponent("System");
    }
    
    public void Convert(ActorModelData actorData)
    {              
        NetworkComponent actorComp = new NetworkComponent(actorData.GetName()+"_Actor");
        
        BaseComponent handlers = CreateHandlers(actorData);
        BaseComponent queue = CreateQueue(actorData);

        BaseComponent vars = CreateVars(actorData);
        
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
            model.AddComponent(CreateContinuousBehavior(cb, actorData));
        
        for(ContinuousVariable var : actorData.GetContinuousVariables())
            model.AddComponent(CreateContinuousVariableLock(var, actorData));
        
        actorComp.AddParameter(new RealParameter("actorBusy", true));
        
        ComponentInstance instance = new ComponentInstance("queue",queue);
        
        instance.SetBinding("actorBusy", "actorBusy");
        
        actorComp.AddInstance(instance);
        
        model.AddComponent(handlers);
        model.AddComponent(vars);
        model.AddComponent(queue);
        model.AddComponent(actorComp);
    }
    private Component CreateContinuousVariableLock(ContinuousVariable var, ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetActor().GetName() + "_" + var.Name() + "_Lock");
        
        comp.AddParameter(new LabelParameter("Lock", false));        
        comp.AddParameter(new LabelParameter("Unlock", false));

        Location unlock = new Location("unlock");
        comp.AddLocation(unlock);
        unlock.AddFlow(new Flow(var.Name()+" == 0"));

        Location lock = new Location("lock");
        comp.AddLocation(lock);

        comp.AddTransition(lock, new HybridLabel().SetSyncLabel("Unlock"), unlock);       
        comp.AddTransition(unlock, new HybridLabel().SetSyncLabel("lock"), lock);
        
        return comp;
    }
    
    private BaseComponent CreateContinuousBehavior(ContinuousBehavior cb, ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetActor().GetName() +"_CB_" + actorData.GetIDFor(cb));
        
        String acquireLabel = "Acquire_" + cb.GetEquation().GetVariable().Name();
        String releaseLabel = "Release_" + cb.GetEquation().GetVariable().Name();
        
        comp.AddParameter(new LabelParameter("Start", false));
        comp.AddParameter(new LabelParameter(acquireLabel, false));
        comp.AddParameter(new LabelParameter(releaseLabel, false));
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true));
        
        for(String send : actorData.GetSendLables())
            comp.AddParameter(new LabelParameter(send, false));
        
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);

        Location acquireLockLoc = new Location("acquireLock");
        MakeLocationUrgent(acquireLockLoc, actorData);
        comp.AddLocation(acquireLockLoc);
        
        Location behaviorLoc = new Location("behvaior");        
        behaviorLoc.AddFlow(new Flow(cb.GetEquation()));
        behaviorLoc.AddInvarient(new Invarient(cb.GetInvarient()));
        comp.AddLocation(behaviorLoc);
        
        comp.AddTransition(idleLoc, new HybridLabel().SetSyncLabel("Start").AddAssignment(actorData.GetUrgentReset()), acquireLockLoc);
        
        comp.AddTransition(acquireLockLoc, new HybridLabel().SetSyncLabel(acquireLabel).AddGuard(actorData.GetUrgentGuard()), behaviorLoc);
        
        StatementToLocationConvertor convertor = new StatementToLocationConvertor(cb.GetActions(), actorData, behaviorLoc, comp, "s");
        convertor.ConvertStatementChain(false);
        HybridTransition trans =  convertor.GetFirstTransition();
        trans.GetLabel().AddGuard(cb.GetGuard());
        
        HybridTransition recurseTrans = new HybridTransition(convertor.GetLastLocation(), new HybridLabel().SetSyncLabel(releaseLabel), idleLoc);
        
        convertor.ProcessLastLocation(recurseTrans.GetLabel());
        
        comp.AddTransition(recurseTrans);

        return comp;
    }
    
    private BaseComponent CreateHandlers(ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetName()+"_Handlers");
        
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);
        
        comp.AddParameter(new RealParameter(actorData.GetUrgentVar(), true)); 
        comp.AddParameter(new RealParameter(actorData.GetLockVar(), false));
        comp.AddParameter(new RealParameter(actorData.GetDelayVar(), false));
        
        for(ContinuousVariable var : actorData.GetContinuousVariables())
            comp.AddParameter(new RealParameter(var.Name(), false));
        
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
            comp.AddParameter(new LabelParameter(actorData.GetStartLabelFor(cb), false));
        
        for(String sendLables : actorData.GetSendLables())
            comp.AddParameter(new LabelParameter(sendLables, false));
        
        for(MessageHandler handler : actorData.GetActor().GetMessageHandlers())
            CreateHandler(handler,comp, actorData, idleLoc);
        
        return comp;
    }
    
    private void CreateHandler(MessageHandler handler, BaseComponent comp, ActorModelData actorData, Location startLoc)
    {
        String takeLabel =  actorData.CreateTakeLabel(handler.GetID());
        comp.AddParameter(new LabelParameter(takeLabel, false));

        StatementToLocationConvertor statementsConvertor
                = new StatementToLocationConvertor(handler.GetBody(), actorData, startLoc, comp, handler.GetID());

        statementsConvertor.ConvertStatementChain(true);
        HybridTransition firstTrans = statementsConvertor.GetFirstTransition();
        firstTrans.GetLabel().SetSyncLabel(takeLabel);
    }

    private BaseComponent CreateVars(ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetName()+"_Vars");
        comp.AddParameter(new RealParameter("busy", false));
        
        Location location = new Location("l0");
        location.AddFlow(new Flow("busy' == 0"));
        
        comp.AddLocation(location);
        
        return comp;
        
    }
    
    private BaseComponent CreateQueue(ActorModelData actorData)
    {
        BaseComponent comp = new BaseComponent(actorData.GetName()+"_Queue");
        
        comp.AddParameter(new RealParameter(actorData.GetBusyVar(), false));
        
        for(String label : actorData.GetReceiveLabels())
            comp.AddParameter(new LabelParameter(label, false));
        
        for(String label : actorData.GetHandlersName())
            comp.AddParameter(new LabelParameter(actorData.CreateTakeLabel(label), false));
        
        new ActorQueueCreator(comp, actorData).Create();
        
        return comp;
    }
    
 
    
    public SpaceExModel GetConvertedModel()
    {
        return model;
    }


    private void MakeLocationUrgent(Location location, ActorModelData actorData)
    {
        location.AddFlow(new Flow(actorData.GetUrgentFlow()));
        location.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
    }
}
