/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActor extends Actor<PhysicalActorType>
{
    
    public PhysicalActor(String name, PhysicalActorType type, int queueCapacity)
    {
        super(name, type, queueCapacity);
    }
    
    public Mode InitialMode()
    {
        return Type().InitialMode();
    }
}
