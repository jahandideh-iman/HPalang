/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.RealParameter;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorVariablesCreator
{
    private final ActorModelData actorData;
    private final BaseComponent comp;
    
    ActorVariablesCreator(BaseComponent comp, ActorModelData actorData)
    {
        this.comp = comp;
        this.actorData = actorData;
    }

    public void Create()
    {
        Location location = new Location("loc");
                
        for(Variable variable : actorData.InstanceVariables())
        {
            comp.AddParameter(new RealParameter(variable.Name(), false));
            location.AddFlow(new Flow(actorData.FlowFor(variable.Name(),0)));
        }
        
        for(String localVariable : actorData.MessageParameterNames())
        {
            comp.AddParameter(new RealParameter(localVariable, false));
            location.AddFlow(new Flow(actorData.FlowFor(localVariable,0)));
        }
        
        for(String queueVariable : actorData.QueueData().Variables())
        {
            comp.AddParameter(new RealParameter(queueVariable, false));
            location.AddFlow(new Flow(actorData.FlowFor(queueVariable,0)));
        }

        
        comp.AddLocation(location);
    }
    
}
