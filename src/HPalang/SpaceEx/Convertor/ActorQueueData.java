/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.VariableParameter;
import java.util.Collection;
import java.util.LinkedList;

import static HPalang.SpaceEx.Convertor.Utilities.MiscUtilities.*;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorQueueData
{
    private final ActorModelData actorModelData;
    
    //private final Map<VariableParameter, String> bufferParameterNames = new HashMap<>();

    public ActorQueueData(ActorModelData actorModelData)
    {
        this.actorModelData = actorModelData;
        

    }
    
    public void Init()
    {

    }

    Iterable<String> AllQueueVariables()
    {
        List<String> variables = new LinkedList<>();
        
        variables.addAll(QueueControlVars());
        variables.addAll(QueueBufferVars());
        variables.addAll(QueueElementVars());
        
        return variables;
    }
    
    Collection<String> QueueControlVars()
    {
        List<String> variables = new LinkedList<>();

        variables.add(QueueHeadVar());
        variables.add(QueueSizeVar());
        variables.add(QueueTailVar());
        
        return variables;
    }

    Collection<String> QueueBufferVars()
    {
        List<String> variables = new LinkedList<>();

        variables.add(BufferIsFullVar());
        variables.add(BufferMessageVar());

        for (VariableParameter parameter : actorModelData.MessageParameters()) {
            variables.add(BufferParamaterVarFor(parameter));
        }
        return variables;
    }
    
    Collection<String> QueueBufferVars(String prefix)
    {
        List<String> variables = new LinkedList<>();

        variables.add(BufferIsEmptyVar(prefix));
        variables.add(BufferMessageVar(prefix));

        for (VariableParameter parameter : actorModelData.MessageParameters()) {
            variables.add(BufferParamaterVarFor(parameter,prefix));
        }
        return variables;
    }
    
    public Collection<String> QueueElementVars()
    {
        List<String> variables = new LinkedList<>();
        
        for (int i = 0; i < QueueCapacity(); i++) {

            variables.add(ElementMessageVar(i));
            for (VariableParameter parameter : actorModelData.MessageParameters()) {
                variables.add(ElementParamterVarFor(parameter, i));
            }
        }
        
        return variables;
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
    
    public String BufferIsFullVar()
    {
        return "buffer_isFull";
    }
    
    public String BufferIsEmptyVar(String prefix)
    {
        return String.format("%s_%s", prefix, BufferIsFullVar());
    }
    
    public String ElementMessageVar(int i)
    {
        return String.format("element_%d_message", i);
    }
    
    public String ElementParamterVarFor(VariableParameter parameter, int elementIndex)
    {
        return String.format(
                "element_%d_parameter_%s", 
                elementIndex,
                actorModelData.ParameterNameFor(parameter));
    }
    
    String BufferParameterAssignmentFor(Message message, int parameterIndex, String value , String prefix)
    {
        VariableParameter parameter =  message.Parameters().AsList().get(parameterIndex);
        
        return actorModelData.ResetFor(
                BufferParamaterVarFor(parameter, prefix), 
                value);
        
    } 
    public String BufferParamaterVarFor(VariableParameter parameter)
    {
        return String.format("buffer_value_%s",actorModelData.ParameterNameFor(parameter));
    }
    
    public String BufferParamaterVarFor(VariableParameter parameter, String prefix)
    {
        return String.format("%s_%s", prefix, BufferParamaterVarFor(parameter));
    }

    public String BufferMessageVar()
    {
        return "buffer_message";
    }
    
    public String MessageBufferAssignment(MessageHandler messageHandler, String prefix)
    {
        return actorModelData.ResetFor(BufferMessageVar(prefix), actorModelData.MessageGUID(messageHandler));
    }
    
    public String BufferMessageVar(String prefix)
    {
        return String.format("%s_%s", prefix, BufferMessageVar());
    }



    public String BufferIsFullGuard()
    {
        return String.format("%s == 1", BufferIsFullVar());
    }
    
    public String BufferIsEmptyInvarient()
    {
        return String.format("%s == 0", BufferIsFullVar());
    }
    
    public String BufferIsEmptyGuard()
    {
        return String.format("%s == 0",BufferIsFullVar());
    }
    
    public String BufferIsEmptyGuard(String prefix)
    {
        return String.format("%s_%s == 0", prefix,BufferIsFullVar());
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
        return String.format("%s := 0", BufferIsFullVar());
    }
    



    String SetBufferFullAssignment(String prefix)
    {
        return String.format("%s_%s := 1", prefix, BufferIsFullVar());
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
    
    public String SizeDecrementAssignment()
    {
        return String.format("%s := %s - %d", QueueSizeVar(), QueueSizeVar(), 1 );
    }

    public String QueueIsFullGuard()
    {
        return String.format("%s == %d", QueueSizeVar(), QueueCapacity());
    }
    
    public String QueueIsNotFullGuard()
    {
        return String.format("%s < %d", QueueSizeVar(), QueueCapacity());
    }
    
    public String QueueIsEmptyInvarianet()
    {
        return String.format("%s == 0", QueueSizeVar());
    }
    
    String QueueIsEmptyGuard()
    {
        return String.format("%s == 0", QueueSizeVar());
    }
    
    public String QueueIsNotEmptyGuard()
    {
        return String.format("%s >= 1", QueueSizeVar());
    }

//    public String TakeMessageLabel()
//    {
//        return "Take_Message";
//    }

    public String ElementMessageGuard(MessageHandler messageHandler, int i)
    {
        return String.format("%s == %d", ElementMessageVar(i), actorModelData.MessageGUID(messageHandler));
    }


    private int QueueCapacity()
    {
        return actorModelData.Actor().QueueCapacity();
    }
    
    public Iterable<String> BufferToElementAssignments(int i)
    {
        List<String> assignments = new LinkedList<>();
        
        for (VariableParameter parameter : actorModelData.MessageParameters()) {
            assignments.add(actorModelData.ResetFor(
                    ElementParamterVarFor(parameter, i), 
                    BufferParamaterVarFor(parameter)));
        }
        
        return assignments;
    }

    public List<String> ElementsToParametersAssignmentFor(MessageHandler messageHandler, int elementIndex)
    {
        List<String> assignments = new LinkedList<>();
        
        for(VariableParameter parameter : messageHandler.Parameters().AsList())
        {
            assignments.add(
                    actorModelData.ResetFor(
                            actorModelData.ParameterNameFor(parameter),
                            ElementParamterVarFor(parameter, elementIndex)));
        }     
        return assignments;
    }


}
