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
    private CANSpecification canSpecification = new CANSpecification();
    
    private int eventSystemVariablePoolSize = 1;
    private int globalVariablePoolSize = 1;
    
    private RealVariablePool initialEventSystemVariablePool;
    private RealVariablePool initialGlobalVariablePool;
    
    public void SetInitialEventSystemVariablePool(RealVariablePool pool)
    {
        this.initialEventSystemVariablePool = pool;
    }
    
    public void SetInitialGlobalVariablePool(RealVariablePool pool)
    {
        this.initialGlobalVariablePool = pool;
    }
    
    public RealVariablePool InitialEventSystemVariablePool()
    {
        return initialEventSystemVariablePool;
    }
    
    public RealVariablePool InitialGlobalVariablePool()
    {
        return initialGlobalVariablePool;
    }
    
    @Deprecated //Use SetInitialEventSystemVariablePool.
    public void SetEventSystemVariablePoolSize(int size)
    {
        eventSystemVariablePoolSize = size;
    }
    
    @Deprecated //Use InitialEventSystemVariablePool.
    public int EventSystemVariablePoolSize()
    {
        return eventSystemVariablePoolSize;
    }
    
    @Deprecated //Use SetInitialGlobalVariablePool.
    public void SetGlobalVariablePoolSize(int size)
    {
        globalVariablePoolSize = size;
    }
    
    @Deprecated //Use InitialGlobalVariablePool.
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

    public Iterable<Actor> Actors()
    {
        List<Actor> actors = new LinkedList<>();
        actors.addAll(physicalActors);
        actors.addAll(softwareActors);
        return actors;
    }
    
    public CANSpecification CANSpecification()
    {
        return canSpecification;
    }

}
