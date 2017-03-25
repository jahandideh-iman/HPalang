/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.Core.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSUtility
{
    static public GlobalRunTimeState FromProgramDefinition(ProgramDefinition program)
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        for(Actor actor : program.GetActors())
            state.AddActorRunTimeState(new ActorRunTimeState(actor));
        
        for(SendStatement send : program.GetInitialSends())
            state.AddSendStatement(send);
        
        return state;
    }
}