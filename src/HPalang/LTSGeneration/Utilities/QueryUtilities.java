/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Utilities;

import HPalang.LTSGeneration.RunTimeStates.ActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class QueryUtilities
{
    static public boolean MessageQueueIsFull(ActorState actorState)
    {
        return actorState.MessageQueueState().Messages().Size() >= actorState.Actor().QueueCapacity();
    }
}
