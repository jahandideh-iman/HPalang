/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Statements.SendStatement;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelDefinition
{
    private List<SoftwareActor> softwareActors = new LinkedList<>();   
    private List<PhysicalActor> physicalActors = new LinkedList<>();

    private Set<ActorType> types = new HashSet<>();
    private MainBlock mainBlock;
    
    private int eventSystemVariablePoolSize = 1;
    private int globalVariablePoolSize = 1;
    
    public void SetEventSystemVariablePoolSize(int size)
    {
        eventSystemVariablePoolSize = size;
    }
    
    public int EventSystemVariablePoolSize()
    {
        return eventSystemVariablePoolSize;
    }
    
    public void SetGlobalVariablePoolSize(int size)
    {
        globalVariablePoolSize = size;
    }
    
    public int GlobalVariablePoolSize()
    {
        return globalVariablePoolSize;
    }

    public void SetMainBlock(MainBlock mainBlock)
    {
        this.mainBlock = mainBlock;
    }

    public List<SoftwareActor> SoftwareActors()
    {
        return softwareActors;
    }
    
    public List<PhysicalActor> PhysicalActors()
    {
        return physicalActors;
    }

    public Queue<SendStatement> GetInitialSends()
    {
        return mainBlock.GetSendStatements();
    }

    public SoftwareActor FindActor(String name)
    {
        for(SoftwareActor actor : softwareActors)
            if(actor.Name().equals(name))
                return actor;
        return null;
    }

    public void AddActor(PhysicalActor actor)
    {
        physicalActors.add(actor);
        types.add(actor.Type());
    }
    
    public void AddActor(SoftwareActor actor)
    {
        softwareActors.add(actor);
        types.add(actor.Type());
    }

    @Deprecated // AddActor will automatically adds the actor's type.
    public void AddType(ActorType type)
    {
        types.add(type);
    }
    
    public Iterable<ActorType> ActorTypes()
    {
        return types;
    }

}
