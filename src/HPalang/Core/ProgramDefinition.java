/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Statements.SendStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ProgramDefinition
{
    private List<Actor> actors = new ArrayList<>();
    private MainBlock mainBlock;
    
    public void AddActor(Actor actor)
    {
        actors.add(actor);
    }

    public void SetMainBlock(MainBlock mainBlock)
    {
        this.mainBlock = mainBlock;
    }

    public List<Actor> GetActors()
    {
        return actors;
    }

    public List<SendStatement> GetInitialSends()
    {
        return mainBlock.GetSendStatements();
    }
    
}
