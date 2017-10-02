/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ActorType;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorTypeModelData
{

    private final ActorType type;
    
    ActorTypeModelData(ActorType type)
    {
        this.type = type;
    }

    String Name()
    {
        return type.Name();
    }
    
}
