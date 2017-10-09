/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Message;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ModelDefinition;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableParameter;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class HPalangToCompositionalSXConvertor
{
    SpaceExModel model = new SpaceExModel();
    HPalangModelData hpalangModelData;
    
    private Map<ActorModelData,ComponentInstance> actorInstances;
    
    private NetworkComponent systemComp;
    
    public void Convert(ModelDefinition def)
    {
        actorInstances = new HashMap<>();
        hpalangModelData = new HPalangModelData(def);
        systemComp = new NetworkComponent("System");
        
        ConvertAndAddActors(def);
        AddTimerComponent();
        
        AddActorParameters();
        WireActorInstances();
        
        
        model.AddComponent(systemComp);    
    } 

    private void AddTimerComponent()
    {
        Component timerComponent = CreateTimerComponent();
        ComponentInstance timerInst = new ComponentInstance("timer", timerComponent);
        systemComp.AddParameter(new RealParameter("time", false));
        timerInst.SetBinding("time", "time");
        timerInst.SetBinding("duration", "15");
        systemComp.AddInstance(timerInst);
        model.AddComponent(timerComponent);
    }

    private void ConvertAndAddActors(ModelDefinition def)
    {
        for(SoftwareActor actor : def.SoftwareActors())
        {
            ActorModelData actorData = hpalangModelData.ActorModelDataFor(actor);
            CreateAndAddActorInstance(actorData);
        }
    }

    private void CreateAndAddActorInstance(ActorModelData actorData)
    {
        ActorConvertor convertor = new ActorConvertor(actorData, model);
        convertor.Convert();
        NetworkComponent actorComp = convertor.GetActorComponent();
        
        ComponentInstance actorInst = new ComponentInstance(actorComp.GetID(), actorComp);
        systemComp.AddInstance(actorInst);
        
        actorInstances.put(actorData, actorInst);
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

    private void WireActorInstances()
    {
        for(Map.Entry<ActorModelData, ComponentInstance> entry : actorInstances.entrySet())
        {
            ActorModelData actorData = entry.getKey();
            ComponentInstance instance = entry.getValue();
            
            for (String var : actorData.QueueData().QueueBufferVars()) {
                instance.SetBinding(var, AddPrefix(var, actorData.Name()));
            }
            
            for(SendStatement sendStatement : actorData.SendStatements())
            {
                
                ActorModelData receiverData =actorData.FindActorDataFor(sendStatement.ReceiverLocator());
                Message message = receiverData.FindMessageFor(sendStatement.MessageLocator());
                String recieverParamName = actorData.ReceiverNameIn(sendStatement.ReceiverLocator());
                
                for(VariableParameter parameter : message.Parameters().AsList())
                {
                    String formalParam = receiverData.QueueData().BufferParamaterVarFor(parameter, recieverParamName);
                    String actualParam = receiverData.QueueData().BufferParamaterVarFor(parameter, receiverData.Name());
                    instance.SetBinding(formalParam, actualParam);
                }
                
                instance.SetBinding(receiverData.QueueData().BufferIsEmptyVar(recieverParamName),
                        receiverData.QueueData().BufferIsEmptyVar(receiverData.Name()));
                
                instance.SetBinding(receiverData.QueueData().BufferMessageVar(recieverParamName),
                        receiverData.QueueData().BufferMessageVar(receiverData.Name()));

            }

        }
    }
    
    private String AddPrefix(String value, String prefix)
    {
        return String.format("%s_%s", prefix, value); 
    }

    private void AddActorParameters()
    {
        for(ActorModelData actorData : hpalangModelData.ActorModelsData())
        {

            for(String var : actorData.QueueData().QueueBufferVars())
                systemComp.AddParameter(new RealParameter(AddPrefix(var, actorData.Name()), false));
        }
    }

}
