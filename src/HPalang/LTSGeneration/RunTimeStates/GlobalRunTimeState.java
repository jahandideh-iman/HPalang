/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.Statements.SendStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeState implements Cloneable
{
    // TODO: Change it to map
    private List<ActorRunTimeState> actorStates = new ArrayList<>();
    
    public void AddActorRunTimeState(ActorRunTimeState actorRunTimeState)
    {
        actorStates.add(actorRunTimeState);
    }

    public void AddSendStatement(SendStatement sendStatement)
    {
        FindActorState(sendStatement.GetReceiver()).EnqueueMessage(sendStatement.GetMessage());
    }
    
    public ActorRunTimeState FindActorState(Actor actor)
    {
        for(ActorRunTimeState state : actorStates)
            if(state.GetActor() == actor)
                return state;
        return null;
    }

    public List<ActorRunTimeState> GetActorStates()
    {
        return actorStates;
    }
    
    public GlobalRunTimeState Clone()
    {
        try {
            Object copy = clone();
            return (GlobalRunTimeState) copy;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!GlobalRunTimeState.class.isAssignableFrom(other.getClass()))
            return false;
            
        GlobalRunTimeState otherState = (GlobalRunTimeState) other;
       
        if(otherState.actorStates.size() != this.actorStates.size())
            return false;
               
        for(ActorRunTimeState actorState : this.actorStates)
            otherState.actorStates.contains(actorState);
        
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.actorStates);
        return hash;
    }
    
}
