/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import HPalang.Core.Actor;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.SoftwareActor;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
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
    
    public static MessagePacket EmptySelfPacketFor(SoftwareActor actor)
    {
        return new MessagePacket(actor, actor, new EmptyMessage(), MessageArguments.From());
    }
}
