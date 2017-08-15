/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActorType
{
    private final Set<DiscreteVariable> variables = new HashSet<>();
    
    private final Map<String,MessageHandler> messageHandlers = new HashMap<>();
    
}
