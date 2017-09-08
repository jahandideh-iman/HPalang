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
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.Event.SendPacketAndResetNetworkAction;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.DirectActorLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
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
        return new SendStatement(new DirectActorLocator(actor), new DirectMessageLocator(message));
    }
    
    static public SendStatement CreateSendStatement(SoftwareActor actor, Message message, MessageArguments arguments)
    {
        return new SendStatement(new DirectActorLocator(actor), new DirectMessageLocator(message), arguments);
    }
    
    static public SendStatement CreateEmptySendStatementTo(SoftwareActor actor)
    {
        return CreateSendStatement(actor, new EmptyMessage());
    }
    
    static public void PutMessagePacketInActor(MessagePacket packet, SoftwareActorState actorState)
    {
        actorState.MessageQueueState().Messages().Enqueue(packet);
    }
    
    static public void RemoveMessagePacket(MessagePacket packet, MessageQueueState state)
    {
        state.Messages().Remove(packet);
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
        return NetworkingUtility.MessagePacket(actor, actor, new EmptyMessage(), MessageArguments.Empty());
    }
    
    static public MessagePacket EmptySelfMessagePacket(SoftwareActor actor, int priority)
    {
        return NetworkingUtility.MessagePacket(actor, actor, new EmptyMessage(priority), MessageArguments.Empty());
    }
    
    static public MessagePacket MessagePacket(Actor sender, SoftwareActor receiver, Message message)
    {
        return NetworkingUtility.MessagePacket(sender, receiver, message, MessageArguments.Empty());
    }
    static public MessagePacket MessagePacket(Actor sender, SoftwareActor receiver , Message message, MessageArguments arguments)
    {
        MessagePacket packet = new MessagePacket(
                sender,
                receiver,
                message, 
                arguments);
        
        return packet;
    }
    
    static public MessagePacket MessagePacket(Actor sender,SendStatement sendStatement)
    {
        return NetworkingUtility.MessagePacket(
                sender, 
                (SoftwareActor)sendStatement.ReceiverLocator().GetActor(), 
                sendStatement.MessageLocator().Get(null), 
                sendStatement.Arguments());

    }
    
    static public MessagePacket FindLastPacket(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        return globalState.DiscreteState().FindActorState(actor).MessageQueueState().Messages().Head();
    }
    
    static public void FillActorsQeueue(SoftwareActorState receiverState)
    {
        for(int i = 0 ; i < receiverState.SActor().Capacity(); i++)
        {
            MessagePacket packet = EmptySelfMessagePacketFor(receiverState.SActor());
            PutMessagePacketInActor(packet, receiverState);
        }
    }
    
    static public Event CreateEventFor(float delay, Action action, GlobalRunTimeState globalState)
    {
        return globalState.DeepCopy().EventsState().RegisterEvent(delay, action);
    }
    
}
