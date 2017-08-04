/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.LTSGeneration.State;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousState extends CompositeStateT<ContinuousState>
{
    private final List<PhysicalActorState> physicalActorStatesCache = new LinkedList<>();
    
    public void AddPhysicalActorState(PhysicalActorState state)
    {
       AddSubstate(state);
    }  

    @Override
    protected void OnSubstateAdded(State substate)
    {
        if(substate instanceof PhysicalActorState)
            physicalActorStatesCache.add((PhysicalActorState)substate);
    }
        
    
    public Iterable<PhysicalActorState> ActorStates()
    {
        return physicalActorStatesCache;
    }
        
    @Override
    protected ContinuousState NewInstance()
    {
        return new ContinuousState();
    }

    @Override
    protected void CloneData(ContinuousState copy)
    {
    }

    @Override
    protected boolean DataEquals(ContinuousState other)
    {
        return true;
    }
    
    public PhysicalActorState FindActorState(PhysicalActor actor)
    {
        for(PhysicalActorState state : physicalActorStatesCache)
            if(state.Actor() == actor)
                return state;
        return null;
    }    


}
