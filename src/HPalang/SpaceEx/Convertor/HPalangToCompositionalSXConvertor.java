/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.NetworkComponent;
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
    
    private List<NetworkComponent> actorComponents = new LinkedList<>();

    
    public void Convert(ProgramDefinition def)
    {
        hpalangModelData = new HPalangModelData(def);
        
        NetworkComponent systemComp = new NetworkComponent("System");
        
        for(CommunicationLabel send : hpalangModelData.GetGlobalSendLabels())
        {
            systemComp.AddParameter(new LabelParameter(send.GetLabel(), true));
        }

        for(Actor actor : def.GetActors())
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

        model.AddComponent(systemComp);    
    }
    
    public SpaceExModel GetConvertedModel()
    {
        return model;
    }

}
