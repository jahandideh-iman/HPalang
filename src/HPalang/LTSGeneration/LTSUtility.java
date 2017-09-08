/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ModelDefinition;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.DiscreteState;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSUtility
{
    static public GlobalRunTimeState FromModelDefinition(ModelDefinition model)
    {
        GlobalRunTimeState globalState = new GlobalRunTimeState();
        
        DiscreteState discreteState =  new DiscreteState();
        globalState.AddSubstate(discreteState);
        
        
        for(SoftwareActor actor : model.GetActors())
            discreteState.AddSoftwareActorState(new SoftwareActorState(actor));
        
        for(SendStatement send : model.GetInitialSends())
            globalState.AddSendStatement(send);
        
        return globalState;
    }
}
