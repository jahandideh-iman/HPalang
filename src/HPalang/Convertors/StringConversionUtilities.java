/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statement;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.DiscreteState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import HPalang.LTSGeneration.State;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */

// TODO: Write eqquivilant StringBuilder conversion for all
public class StringConversionUtilities
{
    
    static public String StringFor(Label label)
    {
        String labelDes;
        
        if(label.IsGuarded())
            labelDes = String.format(
                    "%s, %s", 
                    StringFor(label.Guard()), 
                    StringFor(label.Resets()));
        else
            labelDes =  StringFor(label.Resets());
        
        return String.format("(%s) %s", LabelType(label), labelDes);
    }
    
    
    static public String LabelType(Label label)
    {
        if(label instanceof SoftwareLabel)
            return "S";
        else if(label instanceof NetworkLabel)
            return "N";
        else if(label instanceof ContinuousLabel)
            return "C";
        
        throw new RuntimeException("Label type is unknow for conversion.");
    }
    static public String StringFor(Set<Reset> resets)
    {
        String output = "";
        for(Reset reset : resets)
            output = String.join(",", output, reset.toString());
        
        return output;
    }
    static public String StringFor(Guard guard)
    {
        return guard.toString();
    }
    
    static public String StringFor(BinaryExpression expression)
    {
        return expression.toString();
    }
    
    static public String StringFor(GlobalRunTimeState gs)
    {
        StringBuilder output = new StringBuilder();
        
        output = output.append(String.format("DS:(%s), ", StringForDiscreteState(gs.DiscreteState())));
        
        output = output.append(String.format("CS:(%s), ", StringForContinuousState(gs.ContinuousState())));
        
        return output.toString();
    }
    
    static public String StringForContinuousState(ContinuousState continuousState)
    {
        String output;
        
        List<CharSequence> actorStateStr  = new LinkedList<>();
        
        continuousState.ActorStates().forEach((actorState ) -> actorStateStr.add(StringForPhysicalActorState(actorState)));
        
        output = String.join(",", actorStateStr);

        return output;
    }
    static public String StringForPhysicalActorState(PhysicalActorState actorState)
    {
        StringBuilder output = new StringBuilder();
        
        output = output.append(String.format("Mode=%s,", actorState.Mode().Name()));
        
        output = output.append(StringForCommonActorState((ActorState)actorState));

        return  String.format("%s:(%s)", actorState.Actor().Name() ,output.toString());
    }
        
    static public String StringForDiscreteState(DiscreteState ds)
    {
        String output;
        
        List<CharSequence> actorStateStr  = new LinkedList<>();
        
        ds.ActorStates().forEach((actorState ) -> actorStateStr.add(StringForSoftwareActorState(actorState)));
        
        output = String.join(",", actorStateStr);

        return output;
    }
    
    static public String StringForSoftwareActorState(SoftwareActorState actorState)
    {
        String output = "";
        
        output = output.concat(String.format("IsSuspended=%b,", actorState.IsSuspended()));
        
        output = output.concat(StringForCommonActorState((ActorState)actorState));

        return  String.format("%s:(%s)", actorState.Actor().Name() ,output);
    }
    
    static public String StringForCommonActorState(ActorState actorState)
    {
        StringBuilder output = new StringBuilder();
        output = output.append(String.format("val:(%s),",StringFor(actorState.ValuationState())));
        
        output = output.append(String.format("exe:(%s),",StringFor(actorState.ExecutionQueueState())));
        
        output = output.append(String.format("mes:(%s)",StringFor(actorState.MessageQueueState())));
        
        return output.toString();
    }
    
    static public String StringFor(ValuationState valutationState)
    {
        String output;
        
        List<CharSequence> valuationStr  = new LinkedList<>();
        
        valutationState.Valuation().forEach(
                (pair) -> valuationStr.add(String.format("%s = %d", pair.getKey().Name(), pair.getValue())));
        
        output = String.join(",", valuationStr);
        
        return output;
    }
    
    static public String StringFor(ExecutionQueueState executionQueueState)
    {
        String output = "";
        
        List<CharSequence> statemenetsStr  = new LinkedList<>();
        
        executionQueueState.Statements().forEach(
                (s) -> statemenetsStr.add(StringForStatement(s)));
        
        output = String.join(",", statemenetsStr);
        
        return output;
    }
    
    static public String StringFor(MessageQueueState messageQueueState)
    {
        String output;
        
        List<CharSequence> messagesStr  = new LinkedList<>();
        
        messageQueueState.Messages().forEach(
                (packet) -> messagesStr.add(StringForPacket(packet)));
        
        output = String.join(",", messagesStr);
        
        return output;
    }
    
    static public String StringForStatement(Statement statement)
    {
        return statement.toString();
    }
    
    static public String StringForPacket(MessagePacket packet)
    {
        return StringForMessage(packet.Message());
    }
    
    static public String StringForMessage(Message message)
    {
        return message.toString();
    }
}
