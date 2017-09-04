/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActor extends Actor<PhysicalActorType>
{
    public PhysicalActor(String name)
    {
        this(name,null);
    }
    
    public PhysicalActor(String name, PhysicalActorType type)
    {
        super(name, type);
    }
}
