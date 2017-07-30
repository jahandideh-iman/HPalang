/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Utilities.Queue;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Actor;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.Statement;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState extends CompositeStateT<ActorRunTimeState>
{
    private final Actor actor;
    
    private boolean isSuspended = false;
    private  Queue<Statement> suspendedStatements = new Queue<>();

    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;     
    }
    

    public int GetMessageQueueCapacity()
    {
        return actor.GetCapacity();
    }
    
    public Actor GetActor()
    {
       return actor;
    }
       
    public void SetSuspended(boolean suspended)
    {
        isSuspended = suspended;
    }
    
    public boolean IsSuspended()
    {
        return isSuspended;
    }
    
    public Queue<Statement> SuspendedStatements()
    {
        return suspendedStatements;
    }
    
    
    // TODO: Merge this with ValuationContainer
    public boolean ValuationEqual(ActorRunTimeState other)
    {
        return this.isSuspended == other.isSuspended
                && this.suspendedStatements.equals(other.suspendedStatements);
    }
   

   
    @Override
    protected ActorRunTimeState NewInstance()
    {
        return new ActorRunTimeState(actor);
    }

    @Override
    protected void CloneData(ActorRunTimeState copy)
    {
            //copy.statementQueue = this.statementQueue.DeepCopy();
            copy.suspendedStatements = this.suspendedStatements.DeepCopy();
    }

    @Override
    protected boolean DataEquals(ActorRunTimeState other)
    {
        return this.actor == other.actor && isSuspended == other.isSuspended;
    }
}
