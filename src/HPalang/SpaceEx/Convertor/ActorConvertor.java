/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Variable;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
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
        for (String var : actorData.AllSendVariables()) {
            actorComponent.AddParameter(new RealParameter(var, false));
        }

        for (Variable var : actorData.InstanceVariables()) {
            actorComponent.AddParameter(new RealParameter(var.Name(), true));
        }

        for (String var : actorData.MessageParameterNames()) {
            actorComponent.AddParameter(new RealParameter(var, true));
        }

        actorComponent.AddParameter(new LabelParameter(actorData.QueueData().TakeMessageLabel(), true));
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
    }

    private void CreateQueueInstance(BaseComponent queue)
    {
        ComponentInstance queueInst = new ComponentInstance("queue", queue);
        
        for(String elementVars : actorData.QueueData().AllQueueVariables())
            queueInst.SetBinding(elementVars, elementVars);
        
        queueInst.SetBinding(actorData.QueueData().TakeMessageLabel(), actorData.QueueData().TakeMessageLabel());
        
        for (String label : actorData.ExecuteMessageLabels()) {
            queueInst.SetBinding(label,label);
        }
        
        for (String param : actorData.MessageParameterNames()) {
            queueInst.SetBinding(param,param);
        }
        actorComponent.AddInstance(queueInst);
    }
    public NetworkComponent GetActorComponent()
    {
        return actorComponent;
    }

}
