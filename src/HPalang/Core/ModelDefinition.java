/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Statements.SendStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ModelDefinition
{
    private List<SoftwareActor> softwareActors = new LinkedList<>();   
    private List<PhysicalActor> physicalActors = new LinkedList<>();

    private List<ActorType> types = new LinkedList<>();
    private MainBlock mainBlock;
    


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

    public void AddType(ActorType actorType)
    {
        types.add(actorType);
        
    }

    public void AddActor(PhysicalActor actor)
    {
        physicalActors.add(actor);
    }
    
    public void AddActor(SoftwareActor actor)
    {
        softwareActors.add(actor);
    }

}
