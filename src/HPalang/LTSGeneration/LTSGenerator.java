/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGenerator
{
    
    public void Generate(ProgramDefinition definition)
    {
        GlobalRunTimeState initialState = new GlobalRunTimeState();
        for(Actor actor : definition.GetActors())
            initialState.AddActorRunTimeState(new ActorRunTimeState(actor));
        
        for(SendStatement send : definition.GetInitialSends())
            initialState.AddSendStatement(send);
    }
    
}
