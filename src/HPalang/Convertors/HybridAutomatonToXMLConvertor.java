/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.HybridAutomataGeneration.Location;
import HPalang.HybridAutomataGeneration.Transition;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonToXMLConvertor
{
    public String Convert(HybridAutomaton hybridAutomaton)
    {
        String output = "";
        output += "<DMEComponent type=\"FiniteStateMachine\" >";
        output += "<States>\n";
        for(Location location : hybridAutomaton.GetLocations())
        {
            String stateStr = LocationToString(location);
            
            output +=("       <State  >\n");
            output += "           <Name>" + stateStr+"</Name>\n";
            output +=("       </State>\n");
        }
        output += "</States>\n";
        
        output += "<Transitions>\n";
        
        for(Transition transition : hybridAutomaton.GetTransitions())
        {
            String transitionStr = "\t<Transition>\n";
            
            String orignStr = LocationToString(transition.GetOrign());
            String destinationStr = LocationToString(transition.GetDestination());
            String labelStr = transition.GetLabel().toString();
            
            transitionStr+=("\t\t<From>" + orignStr+"</From>\n");
            transitionStr+=("\t\t<To>" + destinationStr+"</To>\n");
            transitionStr+=("\t\t<Condition>" + xmlEscapeText(labelStr)+"</Condition>\n");

            transitionStr += "\t</Transition>\n";
           
            output+= transitionStr;
        }
        
        output += "</Transitions>\n";
        output +="<InitialState>" + LocationToString(hybridAutomaton.GetInitialState())+"</InitialState>";
        output += "</DMEComponent>";
        return output;
    }

    private String LocationToString(Location location)
    {
        String locationStr = "";
        
        
        String invarientsStr = "";
        for(String invarient : location.GetInvarients())
            invarientsStr += invarient + "&&";
        
        int lastIndex;
        lastIndex = invarientsStr.lastIndexOf("&&");
        if(lastIndex >= 0)
            invarientsStr =  invarientsStr.substring(0,lastIndex);
        
        String equationsStr = "";
        for(String equation : location.GetEquations())
            equationsStr += equation + "&&";
        
        lastIndex = equationsStr.lastIndexOf("&&");
        if(lastIndex >= 0)
        equationsStr =  equationsStr.substring(0,lastIndex);
        
        locationStr = invarientsStr + "\t" + equationsStr;
        
        return xmlEscapeText(locationStr);
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
