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
import HPalang.Core.Statement;
import HPalang.Core.Statements.DelayStatement;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

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
            Convert(actor);
        
        NetworkComponent system = CreateSystem();
        model.AddComponent(system);
        
       
    }
    
    private NetworkComponent CreateSystem()
    {
        return new NetworkComponent("System");
    }
    
    public void Convert(Actor actor)
    {
        ActorModelData actorData = hpalangModelData.GetActorData(actor);
        
                
        NetworkComponent actorComp = new NetworkComponent(actor.GetName()+"_Actor");
        
        BaseComponent handlers = CreateHandlers(actor);
        BaseComponent queue = CreateQueue(actor);

        BaseComponent vars = CreateVars(actor);
        
        int i = 0;
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
        {
            model.AddComponent(CreateContinuousBehavior(cb, actorData,i));
            i++;
        }
        
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
    
    private BaseComponent CreateContinuousBehavior(ContinuousBehavior cb, ActorModelData actorData, int index)
    {
        BaseComponent comp = new BaseComponent(actorData.GetActor().GetName() + "_CB_"+ String.valueOf(index));
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);
        
        Location bLoc = new Location("behvaior");
        comp.AddLocation(bLoc);
        
        bLoc.AddFlow(new Flow(cb.GetEquation()));
        bLoc.AddInvarient(new Invarient(cb.GetInvarient()));
        comp.AddTransition(idleLoc, new HybridLabel().SetSyncLabel("start"), bLoc);
        
        HybridTransition trans =  new StatementToLocationConvertor(cb.GetActions(), actorData, bLoc, comp, "s").ConvertStatementChain();
        
        trans.GetLabel().AddGuard(cb.GetGuard());
        
        comp.AddTransition(trans);
        
        return comp;
    }
    
    private BaseComponent CreateHandlers(Actor actor)
    {
        ActorModelData actorData = hpalangModelData.GetActorData(actor);
        BaseComponent comp = new BaseComponent(actor.GetName()+"_Handlers");
        
        Location idleLoc = new Location("idle");
        comp.AddLocation(idleLoc);
        comp.AddParameter(new RealParameter("urg", true));
        comp.AddParameter(new RealParameter(actor.GetDelayVariable().Name(), false));
        
        for(ContinuousVariable var : actor.GetContinuousVariables())
            comp.AddParameter(new RealParameter(var.Name(), false));
        
        for(ContinuousBehavior cb : actorData.GetContinuousBehaviors())
            comp.AddParameter(new LabelParameter(actorData.GetStartLabelFor(cb), false));
        
        for(String sendLables : actorData.GetSendLables())
            comp.AddParameter(new LabelParameter(sendLables, false));
        
        for(MessageHandler handler : actor.GetMessageHandlers())
        {
            String takeLabel = "Take_"+handler.GetID();
            comp.AddParameter(new LabelParameter(takeLabel, false));
            
            
            StatementToLocationConvertor statementsConvertor = 
                    new StatementToLocationConvertor(handler.GetBody(), hpalangModelData.GetActorData(actor), idleLoc, comp, handler.GetID());
            
            HybridTransition firstTrans = statementsConvertor.ConvertStatementChain();
            firstTrans.GetLabel().SetSyncLabel(takeLabel);
            
            comp.AddTransition(firstTrans);
            
        }
        
        return comp;
    }

    
    private BaseComponent CreateVars(Actor actor)
    {
        BaseComponent comp = new BaseComponent(actor.GetName()+"_Vars");
        comp.AddParameter(new RealParameter("busy", false));
        
        Location location = new Location("l0");
        location.AddFlow(new Flow("busy' == 0"));
        
        comp.AddLocation(location);
        
        return comp;
        
    }
    
    private BaseComponent CreateQueue(Actor actor)
    {
        BaseComponent comp = new BaseComponent(actor.GetName()+"_Queue");
        comp.AddParameter(new RealParameter("actorBusy", false));
        
        for(String label : hpalangModelData.GetActorData(actor).GetReceiveLabels())
            comp.AddParameter(new LabelParameter(label, false));
        
        for(String label : hpalangModelData.GetActorData(actor).GetHandlersName())
            comp.AddParameter(new LabelParameter(CreateTakeLabel(label), false));
        
        new ActorQueueCreator(comp, hpalangModelData.GetActorData(actor)).Create();
        //Location idle = new Location("idle");
        //comp.AddLocation(idle);
        
        //ExpandQueue(actor, comp, 3, "", idle);
        //ExpandQueue2(actor, comp, Arrays.asList(idle), 2);
        
//        for(Location loc : comp.GetLocations())
//        {
//            for(String label : hpalangModelData.GetActorData(actor).GetHandlersName())
//            {
//                if(ExtractHeadLabel(loc))
//            }
//        }
        

        return comp;
    }
    
    private String CreateTakeLabel(String handler)
    {
        return "Take_" + handler;
    }
    private List<Location> ExpandQueue(Actor actor, BaseComponent comp, int cap, String prefix, Location origin /* ,Map<String,Location> takesLocation*/)
    {
        List<Location> createdLocations = new LinkedList<>();
        if(cap == 0)
            return createdLocations;
        
        for(String handler : hpalangModelData.GetActorData(actor).GetHandlersName())
        {
            String name = prefix+"_"+handler;
            Location loc1 = new Location(name+"_1"); 
            Location loc2 = new Location(name+"_2");

            comp.AddLocation(loc1);            
            comp.AddLocation(loc2);
            
            createdLocations.add(loc1);
            createdLocations.add(loc2);
            
            HybridLabel label1 = new HybridLabel();
            label1.AddGuard("acturBusy == 1");
            HybridTransition trans1 = new HybridTransition(loc1, label1, loc2);
            comp.AddTransition(trans1);
            
            for(String recieve : hpalangModelData.GetActorData(actor).GetReceiveLabelsFor(handler))
            {
                HybridLabel label = new HybridLabel();
                label.SetSyncLabel(recieve);
                HybridTransition trans = new HybridTransition(origin, label, loc1);
                comp.AddTransition(trans);
            }
            ExpandQueue(actor, comp, cap-1, name, loc2);
        }
        
        return createdLocations;
    }
    
    public void ExpandQueue2(Actor actor, BaseComponent comp,Collection<Location> locs, int steps)
    {
        if(steps == 0)
            return;
        List<Location> createdLocations = new LinkedList<>();
        
        for(Location loc : locs)
        {
            for (String handler : hpalangModelData.GetActorData(actor).GetHandlersName()) 
            {
                String name = loc.GetName()+"_"+handler;
                Location loc1 = new Location(name+"_1"); 
                Location loc2 = new Location(name+"_2");

                comp.AddLocation(loc1);            
                comp.AddLocation(loc2);

                createdLocations.add(loc1);
                createdLocations.add(loc2);
                
                HybridLabel label1 = new HybridLabel();
                label1.AddGuard("acturBusy == 1");
                HybridTransition trans1 = new HybridTransition(loc1, label1, loc2);
                comp.AddTransition(trans1);
                
                for (String recieve : hpalangModelData.GetActorData(actor).GetReceiveLabelsFor(handler)) 
                {
                    HybridLabel label = new HybridLabel();
                    label.SetSyncLabel(recieve);
                    HybridTransition trans = new HybridTransition(loc, label, loc1);
                    comp.AddTransition(trans);
                }
            }
        }
        
        ExpandQueue2(actor, comp, createdLocations, steps-1);

    }
    
    public SpaceExModel GetConvertedModel()
    {
        return model;
    }


}
