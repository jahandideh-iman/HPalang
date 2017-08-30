/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.DirectActorLocator;
import Mocks.EmptyMessage;
import Mocks.NullExpression;

/**
 *
 * @author Iman Jahandideh
 */
public class NetworkingUtility
{
    static public VariableArgument ArgumentFor(VariableParameter parameter)
    {
        return new VariableArgument(parameter, new NullExpression());
    }
    
    static public VariableParameter ParameterFor(String param)
    {
        return new VariableParameter(new IntegerVariable(param));
    }
    

    
    static public SendStatement CreateSendStatement(SoftwareActor actor, Message message)
    {
        return new SendStatement(new DirectActorLocator(actor),message);
    }
    
    static public SendStatement CreateEmptySendStatementTo(SoftwareActor actor)
    {
        return new SendStatement(new DirectActorLocator(actor),new EmptyMessage());
    }
    
    static public void PutMessagePacketInActor(SoftwareActorState actorState, MessagePacket packet)
    {
        actorState.MessageQueueState().Messages().Enqueue(packet);
    }

    static public void PutMessagePacketInNetworkState(MessagePacket packet, GlobalRunTimeState globalState)
    {
        globalState.NetworkState().Buffer(packet);
    }
    
    static public void DebufferFromNetworkState(MessagePacket packet, GlobalRunTimeState globalState)
    {
        globalState.NetworkState().Debuffer(packet);
    }
    
    static public void SetNetworkStateIdle(boolean idle, GlobalRunTimeState globalState)
    {
        globalState.NetworkState().SetIdle(idle);
    } 
    static public MessagePacket EmptySelfMessagePacketFor(SoftwareActor actor)
    {
        return MessagePacketFor(actor, actor, new EmptyMessage(), MessageArguments.Empty());
    }
    
    static public MessagePacket EmptySelfMessagePacket(SoftwareActor actor, int priority)
    {
        return MessagePacketFor(actor, actor, new EmptyMessage(priority), MessageArguments.Empty());
    }
    
    static public MessagePacket MessagePacketFor(Actor sender, SoftwareActor receiver , Message message, MessageArguments arguments)
    {
        MessagePacket packet = new MessagePacket(
                sender,
                receiver,
                message, 
                arguments);
        
        return packet;
    }
    
    static public MessagePacket MessagePacketFor(Actor sender,SendStatement sendStatement)
    {
        return MessagePacketFor(
                sender, 
                sendStatement.Receiver(), 
                sendStatement.Message(), 
                sendStatement.Arguments());

    }
    
    static public MessagePacket FindLastPacket(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        return globalState.DiscreteState().FindActorState(actor).MessageQueueState().Messages().Head();
    }
    
    static public void FillActorsQeueue(SoftwareActorState receiverState)
    {
        for(int i = 0 ; i < receiverState.Actor().Capacity(); i++)
        {
            MessagePacket packet = EmptySelfMessagePacketFor(receiverState.Actor());
            PutMessagePacketInActor(receiverState,packet);
        }
    }
    
}
