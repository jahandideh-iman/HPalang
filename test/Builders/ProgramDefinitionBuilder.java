/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.Actor;
import HPalang.Core.MainBlock;
import HPalang.Core.ProgramDefinition;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ProgramDefinitionBuilder
{
    private List<Actor> actors = new LinkedList<>();
    public ProgramDefinitionBuilder With(Actor actor)
    {
        actors.add(actor);
        return this;
    }
    
    public ProgramDefinition Build()
    {
        ProgramDefinition def = new ProgramDefinition();
        
        for(Actor actor : actors)
            def.AddActor(actor);
        
        return def;
    }

}
