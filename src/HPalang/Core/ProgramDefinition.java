/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Statements.SendStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

    public Queue<SendStatement> GetInitialSends()
    {
        return mainBlock.GetSendStatements();
    }
    
    public GlobalRunTimeState ToGlobalState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        for(Actor actor : this.GetActors())
            state.AddActorRunTimeState(new ActorRunTimeState(actor));
        
        for(SendStatement send : this.GetInitialSends())
            state.AddSendStatement(send);
        
        return state;
    }
}
