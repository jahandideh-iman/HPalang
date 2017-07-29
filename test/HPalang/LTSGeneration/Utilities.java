/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.Actor;
import HPalang.Core.DefferentialEquation;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import Mocks.EmptyMessage;
import Mocks.EmptyStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class Utilities
{
    static public ContinuousBehavior EmptyBehavior()
    {
        return new ContinuousBehavior("", DefferentialEquation.Empty(""), "", Statement.StatementsFrom(new EmptyStatement()));
    }
    
    static public ActorRunTimeState NewActorState(String actorName)
    {
        Actor actor = new Actor(actorName, 0);
        ActorRunTimeState state = new ActorRunTimeState(actor);
        
        state.ContinuousBehaviors().Add(Utilities.EmptyBehavior());
        state.LowPriorityMessageQueue().Enqueue(new EmptyMessage());
        state.StatementQueue().Enqueue(new EmptyStatement());
        
        return state;
    }
    
    public static GlobalRunTimeState NewGlobalState(ActorRunTimeState ... actorStates)
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        for(ActorRunTimeState actorState : actorStates)
           state.AddActorRunTimeState(actorState);
        
        return state;
    }
}
