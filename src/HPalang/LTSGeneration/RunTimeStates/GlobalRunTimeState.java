/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.Statements.SendStatement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeState extends CompositeStateT<GlobalRunTimeState>
{
    // TODO: Change it to map
    //private final List<SoftwareActorState> actorStates = new LinkedList<>();   
    

//    public void AddSoftwareActorState(SoftwareActorState actorRunTimeState)
//    {
//        actorStates.add(actorRunTimeState);
//    }
    
    public ContinuousState ContinuousState()
    {
        return FindSubState(ContinuousState.class);
    }
    
    public DiscreteState DiscreteState()
    {
        return FindSubState(DiscreteState.class);
    }
    
    public EventsState EventsState()
    {
        return FindSubState(EventsState.class);
    }

    public void AddSendStatement(SendStatement sendStatement)
    {
        DiscreteState().FindActorState(sendStatement.GetReceiver()).FindSubState(MessageQueueState.class).Messages().Enqueue(sendStatement.GetMessage());
    }
    
//    public SoftwareActorState FindActorState(SoftwareActor actor)
//    {
//        for(SoftwareActorState state : actorStates)
//            if(state.Actor() == actor)
//                return state;
//        return null;
//    }

//    public List<SoftwareActorState> ActorStates()
//    {
//        return actorStates;
//    }
//    @Override
//    protected boolean InternalEquals(GlobalRunTimeState other)
//    {
//        if(other.actorStates.size() != this.actorStates.size())
//            return false;
//               
//        for(SoftwareActorState actorState : this.actorStates)
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
//        if(other.actorStates.size() != this.actorStates.size())
//            return false;
//        
//        for(SoftwareActorState actorState : this.actorStates)
//            if(other.actorStates.contains(actorState) == false)
//                return false;
        
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
//        for(SoftwareActorState state : actorStates)
//            copy.AddSoftwareActorState((SoftwareActorState) state.DeepCopy());

    }

}
