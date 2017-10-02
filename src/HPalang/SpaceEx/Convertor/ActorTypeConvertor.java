/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorTypeConvertor extends Convertor
{

    private final ActorTypeModelData typeData;
    
    private NetworkComponent typeComponent;
    
    ActorTypeConvertor(ActorTypeModelData typeData, SpaceExModel model)
    {
        super(model);
        this.typeData = typeData;
    }
    
    @Override
    public void Convert()
    {
        typeComponent = new NetworkComponent(typeData.Name()+"_Type");
        
        BaseComponent queue = CreateQueue();
        
        model.AddComponent(typeComponent);
    }
    
    private BaseComponent CreateQueue()
    {
        BaseComponent comp = new BaseComponent(typeData.Name()+"_Queue");
        
//        comp.AddParameter(new RealParameter(typeData.GetBusyVar(), false));
//        comp.AddParameter(new RealParameter(typeData.GetUrgentVar(), true));
//
//        
//        for(CommunicationLabel label : typeData.GetReceiveLabels())
//            comp.AddParameter(new LabelParameter(label.GetLabel(), false));
//        
//        for(String label : typeData.GetHandlersName())
//            comp.AddParameter(new LabelParameter(typeData.CreateTakeLabel(label), false));
//        
//        new ActorQueueCreator(comp, typeData).Create();
        
        return comp;
    }
}
