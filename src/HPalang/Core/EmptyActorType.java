/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyActorType extends ActorType
{
    
    public EmptyActorType(String name)
    {
        super(name, Collections.EMPTY_LIST);
    }
    
}
