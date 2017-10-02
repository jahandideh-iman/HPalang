/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.MessageHandler;
import HPalang.Core.SoftwareActor;

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
    
    public String ElementValueVar(int i)
    {
        return String.format("element_%d_value", i);
    }

    public String BufferValueVar()
    {
        return "buffer_value";
    }

    public String BufferMessageVar()
    {
        return "buffer_message";
    }

    public String BufferIsFullGuard()
    {
        return String.format("%s == 0", BufferIsEmptyVar());
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
    
    
}
