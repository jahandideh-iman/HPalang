/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.SoftwareActor;
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
    private List<SoftwareActor> actors = new LinkedList<>();
    public ProgramDefinitionBuilder With(SoftwareActor actor)
    {
        actors.add(actor);
        return this;
    }
    
    public ProgramDefinition Build()
    {
        ProgramDefinition def = new ProgramDefinition();
        
        for(SoftwareActor actor : actors)
            def.AddActor(actor);
        
        return def;
    }

}
