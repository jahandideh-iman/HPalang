/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Variable;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.LabelParameter;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.Parameter;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorConvertor extends Convertor
{   
    private final ActorModelData actorData;
    
    private NetworkComponent actorComponent;
    
    public ActorConvertor(ActorModelData actorData, SpaceExModel model)
    {
        super(model);
        this.actorData = actorData;
    }
    
    
    @Override
    public void Convert()
    {              
        actorComponent = new NetworkComponent(actorData.Name()+"_Actor");
        
        BaseComponent handlersComp = CreateAndAddHandlersComponent();
        BaseComponent queueComp = CreateAndAddQueueComponent();
        BaseComponent varsComp = CreateAndAddVarsComponent();
        
        AddComponentParameters();
        
        CreateVariablesInstance(varsComp);
        CreateQueueInstance(queueComp);
        CreateHandlersInstance(handlersComp);
        
        model.AddComponent(actorComponent);
    }
    
    private void AddComponentParameters()
    {
        actorComponent.AddParameter(new RealParameter(actorData.BusyVar(), true));
        
        for (String var : actorData.AllSendVariables()) {
            actorComponent.AddParameter(new RealParameter(var, false));
        }

        for (Variable var : actorData.InstanceVariables()) {
            actorComponent.AddParameter(new RealParameter(var.Name(), true));
        }

        for (String var : actorData.MessageParameterNames()) {
            actorComponent.AddParameter(new RealParameter(var, true));
        }

        //actorComponent.AddParameter(new LabelParameter(actorData.ReadyLabel(), true));
        for (String label : actorData.ExecuteMessageLabels()) {
            actorComponent.AddParameter(new LabelParameter(label, true));
        }

        for (String var : actorData.QueueData().QueueElementVars()) {
            actorComponent.AddParameter(new RealParameter(var, true));
        }
        
        for (String var : actorData.QueueData().QueueControlVars()) {
            actorComponent.AddParameter(new RealParameter(var, true));
        }
        
        for (String var : actorData.QueueData().QueueBufferVars()) {
            actorComponent.AddParameter(new RealParameter(var, false));
        }
        
//        for(String label: actorData.QueueData().SendBufferLabels())
//            actorComponent.AddParameter(new LabelParameter(label, false));
//        
//        for (String label : actorData.QueueData().ReceiverBufferLabels()) {
//            actorComponent.AddParameter(new LabelParameter(label, false));
//        }

    }


    
    private BaseComponent CreateAndAddHandlersComponent()
    {
        BaseComponent comp = new BaseComponent(actorData.Name()+"_Handlers");
        new ActorHandlersCreator(comp, actorData).Create();
        model.AddComponent(comp);
        return comp;
    }
    
    private BaseComponent CreateAndAddVarsComponent()
    {
        BaseComponent comp = new BaseComponent(actorData.Name() + "_Vars");
        new ActorVariablesCreator(comp, actorData).Create(); 
        model.AddComponent(comp);
        return comp;
    }
    
    private BaseComponent CreateAndAddQueueComponent()
    {
        BaseComponent comp = new BaseComponent(actorData.Name()+"_Queue");
        new ActorQueueCreator(comp, actorData).Create();
        model.AddComponent(comp);
        
        return comp;
    }
    
    private void CreateVariablesInstance(BaseComponent vars)
    {
        ComponentInstance varsInst = new ComponentInstance("Vars", vars);
        actorComponent.AddInstance(varsInst);
        for (Parameter param : vars.GetParameters()) {
            varsInst.SetBinding(param.GetName(), param.GetName());
        }
        
        varsInst.SetBinding(actorData.BusyVar(), actorData.BusyVar());
    }
    
    private void CreateHandlersInstance(BaseComponent handlersComponent)
    {
        ComponentInstance handlersInst = new ComponentInstance("handlers", handlersComponent);
        actorComponent.AddInstance(handlersInst);

        for (Parameter param : handlersComponent.GetParameters()) {
            if (param.IsLocal() == false) {
                handlersInst.SetBinding(param.GetName(), param.GetName());
            }
        }
        
//        for (String label : actorData.QueueData().SendBufferLabels()) {
//            handlersInst.SetBinding(label, label);
//        }
        
        //handlersInst.SetBinding(actorData.ReadyLabel(), actorData.ReadyLabel());
        handlersInst.SetBinding(actorData.BusyVar(), actorData.BusyVar());
        
        
    }

    private void CreateQueueInstance(BaseComponent queue)
    {
        ComponentInstance queueInst = new ComponentInstance("queue", queue);
        
        for(String elementVars : actorData.QueueData().AllQueueVariables())
            queueInst.SetBinding(elementVars, elementVars);
        
        //queueInst.SetBinding(actorData.ReadyLabel(), actorData.ReadyLabel());
        //queueInst.SetBinding(actorData.QueueData().TakeMessageLabel(), actorData.QueueData().TakeMessageLabel());
        queueInst.SetBinding(actorData.BusyVar(), actorData.BusyVar());
        
        for (String label : actorData.ExecuteMessageLabels()) {
            queueInst.SetBinding(label,label);
        }
        
        for (String param : actorData.MessageParameterNames()) {
            queueInst.SetBinding(param,param);
        }
        
//        for (String label : actorData.QueueData().ReceiverBufferLabels()) {
//            queueInst.SetBinding(label, label);
//        }
        actorComponent.AddInstance(queueInst);
    }
    public NetworkComponent GetActorComponent()
    {
        return actorComponent;
    }

}
