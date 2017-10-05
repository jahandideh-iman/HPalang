/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.SoftwareActor;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorQueueData
{
    private final ActorModelData actorModelData;
    
    public ActorQueueData(ActorModelData actorModelData)
    {
        this.actorModelData = actorModelData;
        
    }

    public String QueueHeadVar()
    {
        return "queue_head";
    }
    
    public String QueueTailVar()
    {
        return "queue_tail";
    }
    
    public String QueueSizeVar()
    {
        return "queue_size";
    }
    
    public String BufferIsEmptyVar()
    {
        return "buffer_isEmpty";
    }
    
    public String ElementMessageVar(int i)
    {
        return String.format("element_%d_message", i);
    }
    
    private String ElementParamterVarFor(VariableParameter parameter, int elementIndex)
    {
        return String.format("element_%d_parameter_%s", elementIndex, parameter.Name());
    }
    
    public String ElementValueVar(int i)
    {
        return String.format("element_%d_value", i);
    }

    public String BufferValueVar()
    {
        return "buffer_value";
    }
    
    String BufferParameterAssignmentFor(Message message, int parameterIndex, String value , String prefix)
    {
        VariableParameter parameter =  message.Parameters().AsList().get(parameterIndex);
        
        return actorModelData.ResetFor(
                String.format("%s_%s", prefix, BufferParamaterVarFor(parameter)), 
                value);
        
    }
    
    String BufferParamaterVarFor(VariableParameter parameter)
    {
        return String.format("buffer_value_%s", parameter.Name());
    }

    public String BufferMessageVar()
    {
        return "buffer_message";
    }

    public String BufferIsFullGuard()
    {
        return String.format("%s == 0", BufferIsEmptyVar());
    }
    
    public String BufferIsEmptyGuard(String prefix)
    {
        return String.format("%s_%s == 1", prefix,BufferIsEmptyVar());
    }

    public String TailGuard(int i)
    {
        return String.format("%s == %d", QueueTailVar(), i);
    }
    
    public String HeadGuard(int i)
    {
        return String.format("%s == %d", QueueHeadVar(), i);
    }

    public String SetBufferEmptyAssignment()
    {
        return String.format("%s := 1", BufferIsEmptyVar());
    }
    
    String SetBufferFullAssignment(String prefix)
    {
        return String.format("%s_%s := 0", prefix, BufferIsEmptyVar());
    }

    public String TailIncrementAssignment(int i)
    {
        return String.format("%s := %d", QueueTailVar(), (i+1)% QueueCapacity());
    }
    
    public String HeadDecrementAssignment(int i)
    {
        return String.format("%s := %d", QueueHeadVar(), (i + QueueCapacity() -1)% QueueCapacity());
    }

    public String SizeIncrementAssignment()
    {
        return String.format("%s := %s + %d", QueueSizeVar(), QueueSizeVar(), 1 );
    }

    public String QueueIsFullGuard()
    {
        return String.format("%s == %d", QueueSizeVar(), QueueCapacity());
    }
    
    public String QueueIsNotEmptyGuard()
    {
        return String.format("%s >= 0", QueueSizeVar());
    }

    public String TakeMessageLabel()
    {
        return "take_message";
    }

    public String ElementMessageGuard(MessageHandler messageHandler, int i)
    {
        return String.format("%s == %d", ElementMessageVar(i), actorModelData.MessageGUID(messageHandler));
    }


    private int QueueCapacity()
    {
        return actorModelData.Actor().QueueCapacity();
    }

    public List<String> BufferToElementAssignmentsFor(MessageHandler messageHandler, int elementIndex)
    {
        List<String> assignments = new LinkedList<>();
        
        for(VariableParameter parameter : messageHandler.Parameters().AsList())
        {
            assignments.add(
                    actorModelData.ResetFor(
                            ElementParamterVarFor(parameter, elementIndex),
                            BufferParamaterVarFor(parameter)));
        }     
        return assignments;
    }



}
