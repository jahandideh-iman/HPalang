/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.Statements.SendStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeState extends CompositeStateT<GlobalRunTimeState>
{
    // TODO: Change it to map
    private List<ActorRunTimeState> actorStates = new LinkedList<>();   
    


    public void AddActorRunTimeState(ActorRunTimeState actorRunTimeState)
    {
        actorStates.add(actorRunTimeState);
    }
    
    public ContinuousState ContinuousState()
    {
        return FindSubState(ContinuousState.class);
    }

    public void AddSendStatement(SendStatement sendStatement)
    {
        FindActorState(sendStatement.GetReceiver()).FindSubState(MessageQueueState.class).Messages().Enqueue(sendStatement.GetMessage());
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
//    @Override
//    protected boolean InternalEquals(GlobalRunTimeState other)
//    {
//        if(other.actorStates.size() != this.actorStates.size())
//            return false;
//               
//        for(ActorRunTimeState actorState : this.actorStates)
//            if(other.actorStates.contains(actorState) == false)
//                return false;
//        
//        return true;
//    }

//    @Override
//    protected int InternalHashCode()
//    {
//        int hash = 7;
//        hash = 83 * hash + Objects.hashCode(this.actorStates);
//        return hash;
//    }

    @Override
    protected boolean DataEquals(GlobalRunTimeState other)
    {
        if(other.actorStates.size() != this.actorStates.size())
            return false;
        
        for(ActorRunTimeState actorState : this.actorStates)
            if(other.actorStates.contains(actorState) == false)
                return false;
        
        return true;
    }

    @Override
    protected GlobalRunTimeState NewInstance()
    {
        return new GlobalRunTimeState();
    }

    @Override
    protected void CloneData(GlobalRunTimeState copy)
    {
        for(ActorRunTimeState state : actorStates)
            copy.AddActorRunTimeState((ActorRunTimeState) state.DeepCopy());

    }

}
