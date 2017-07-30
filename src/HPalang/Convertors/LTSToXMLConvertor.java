/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.DiscreteVariable;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.Core.Message;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Transition;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import java.util.Map.Entry;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSToXMLConvertor
{
    public String Convert(LabeledTransitionSystem lts)
    {
        String output = "";
        output += "<DMEComponent type=\"FiniteStateMachine\" >";
        output += "<States>\n";
        for(GlobalRunTimeState state : lts.GetStates())
        {
            String stateStr = StateToString(state);
            
            output +=("       <State  >\n");
            output += "           <Name>" + stateStr+"</Name>\n";
            output +=("       </State>\n");
        }
        output += "</States>\n";
        
        output += "<Transitions>\n";
        
        for(Transition transition : lts.GetTransitions())
        {
            String transitionStr = "\t<Transition>\n";
            
            String orignStr = StateToString(transition.GetOrign());
            String destinationStr = StateToString(transition.GetDestination());
            String labelStr = transition.GetLabel().toString();
            
            transitionStr+=("\t\t<From>" + orignStr+"</From>\n");
            transitionStr+=("\t\t<To>" + destinationStr+"</To>\n");
            transitionStr+=("\t\t<Condition>" + xmlEscapeText(labelStr)+"</Condition>\n");

            transitionStr += "\t</Transition>\n";
           
            output+= transitionStr;
        }
        
        output += "</Transitions>\n";
        output +="<InitialState>" + StateToString(lts.GetInitialState())+"</InitialState>";
        output += "</DMEComponent>";
        return output;
    }

    private String StateToString(GlobalRunTimeState state)
    {
        String stateStr = "";
        for(ActorRunTimeState actorState : state.GetActorStates())
        {
            String actorStr = actorState.GetActor().GetName()+ "[";

            actorStr += "(";
            actorStr += "V:{";
            for(Entry<DiscreteVariable, Integer> m : actorState.FindSubState(ValuationState.class).Valuation())
                actorStr += "{" + m.getKey().Name() +":" + m.getValue().toString() +"}"+ ",";
            actorStr += "},";
            
            actorStr += "LQ:{";
            for(Message m : actorState.FindSubState(MessageQueueState.class).Messages())
                actorStr += m.toString() + ",";
            actorStr += "},";
            
            actorStr += "S:{";
            for(Statement s : actorState.FindSubState(ExecutionQueueState.class).Statements())
                actorStr += s.toString() + ",";
            
            actorStr += "},";
            actorStr += actorState.IsSuspended();
            actorStr += ",SS:{";
            for(Statement s : actorState.SuspendedStatements())
                actorStr += s.toString() + ",";
            actorStr += "},";
            actorStr += "),(";

            actorStr += "CB:{";
//            for(ContinuousBehavior b : actorState.ContinuousBehaviors())
//                 actorStr += b.toString() + ",";
            actorStr += "})";
            actorStr += "]";

            stateStr+= actorStr+",";
        }
        return xmlEscapeText(stateStr);
    }
    
   String xmlEscapeText(String t) 
   {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < t.length(); i++)
        {
           char c = t.charAt(i);
           switch(c)
           {
            case '<': sb.append("&lt;"); break;
            case '>': sb.append("&gt;"); break;
            case '\"': sb.append("&quot;"); break;
            case '&': sb.append("&amp;"); break;
            case '\'': sb.append("&apos;"); break;
            default:
               if(c>0x7e) {
                  sb.append("&#"+((int)c)+";");
               }else
                  sb.append(c);
           }
       }
        return sb.toString();
    }

}
