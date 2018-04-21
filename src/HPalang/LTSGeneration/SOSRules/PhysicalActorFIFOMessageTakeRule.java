/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.Utilities.Queue;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorFIFOMessageTakeRule extends PhysicalActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(PhysicalActorState actorState, StateInfo globalStateInfo)
    {
        return  actorState.ExecutionQueueState().Statements().IsEmpty() &&
                !actorState.MessageQueueState().Messages().IsEmpty();
    }

    @Override
    protected void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        PhysicalActorState newActorState = newGlobalState.ContinuousState().FindActorState(actorState.PActor());
        
        MessagePacket packet = FindAndRemoveAppropriateMessagePacket(newActorState.MessageQueueState().Messages());
        
        Message message = packet.Message();
        
        assert( packet.Arguments().Match(message.Parameters()));
        
        List<VariableParameter> parametersList =  message.Parameters().AsList();  
        List<VariableArgument> argumentsList = packet.Arguments().AsList();
        
        for(int i = 0 ; i < parametersList.size(); i++)
        {
            Variable variable = parametersList.get(i).Variable();
            newActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(
                            variable, 
                            argumentsList.get(i).Value()));
            
        }
        
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        newActorState.ExecutionQueueState().Statements().Enqueue(new MessageTeardownStatement(message.Parameters(), packet.PooledVariables()));
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
    
    private MessagePacket FindAndRemoveAppropriateMessagePacket(Queue<MessagePacket> packets)
    {
        MessagePacket packet = null;
        packet = FindFirstDataMessagePacket(packets);
        if(packet == null)
            packet = FindFirstMessage(packets);
        
        packets.Remove(packet);
        return packet;
    }
    
    private MessagePacket FindFirstDataMessagePacket(Queue<MessagePacket> packets)
    {
        for(MessagePacket packet : packets)
            if(packet.Message().MessageType() == Message.MessageType.Data)
                return packet;
        
        return null;
    }

    private MessagePacket FindFirstMessage(Queue<MessagePacket> packets)
    {
        return packets.Head();
    }
    
}
