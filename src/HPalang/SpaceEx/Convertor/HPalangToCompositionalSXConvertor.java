/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SoftwareActorType;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class HPalangToCompositionalSXConvertor
{
    SpaceExModel model = new SpaceExModel();
    HPalangModelData hpalangModelData;
    
    //private List<NetworkComponent> actorTypeComponents = new LinkedList<>();

    
    public void Convert(ModelDefinition def)
    {
        hpalangModelData = new HPalangModelData(def);
        
        NetworkComponent systemComp = new NetworkComponent("System");
        
//        for(CommunicationLabel send : hpalangModelData.GetGlobalSendLabels())
//        {
//            systemComp.AddParameter(new LabelParameter(send.GetLabel(), true));
//        }
//
//        for(ActorType type : def.ActorTypes())
//        {
//            ActorTypeModelData typeData = hpalangModelData.GetActorTypeData(type);
//            
//            ActorTypeConvertor convertor = new ActorTypeConvertor(typeData, model);
//            convertor.Convert();
//        }
        for(SoftwareActor actor : def.SoftwareActors())
        {
            ActorModelData actorData = hpalangModelData.GetActorData(actor);
            ActorConvertor convertor = new ActorConvertor(actorData, model);
            convertor.Convert();
            
            ComponentInstance actorInst = new ComponentInstance(convertor.GetActorComponent().GetID(), convertor.GetActorComponent());
            
            for(CommunicationLabel send : actorData.GetSendLables())
                if(send.IsSelf() == false)
                    actorInst.SetBinding(send.GetLabel(), send.GetLabel());
            
            for(CommunicationLabel receive : actorData.GetReceiveLabels())
                if(receive.IsSelf() == false)
                    actorInst.SetBinding(receive.GetLabel(), hpalangModelData.GetSendLabelFor(receive).GetLabel());
            systemComp.AddInstance(actorInst);
        }

        Component timerComponent = CreateTimerComponent();
        
        ComponentInstance timerInst = new ComponentInstance("timer", timerComponent);
        
        systemComp.AddParameter(new RealParameter("time", false)); 

        timerInst.SetBinding("time", "time");
        timerInst.SetBinding("duration", "15");
        
        systemComp.AddInstance(timerInst);
        
        model.AddComponent(timerComponent);
        model.AddComponent(systemComp);    
    }
    
    private Component CreateTimerComponent()
    {
        BaseComponent timerComp = new BaseComponent("Timer");
        
        timerComp.AddParameter(new RealParameter("duration", false, RealParameter.Dynamic.Const));       
        timerComp.AddParameter(new RealParameter("time", false));
        
        Location loc1 = new Location("loc1");
        Location loc2 = new Location("loc2");
        
        loc1.AddInvarient(new Invarient("time <= duration"));
        loc1.AddFlow(new Flow("time' == 1"));
        
        loc2.AddFlow(new Flow("time' == 0"));
        
        timerComp.AddTransition(loc1, new HybridLabel().AddGuard("time == duration"), loc2);

        
        return timerComp;
    }
    
    public SpaceExModel GetConvertedModel()
    {
        return model;
    }

}
