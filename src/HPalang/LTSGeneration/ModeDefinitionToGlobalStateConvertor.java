/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SimpleContinuousVariablePool;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.DiscreteState;
import static HPalang.LTSGeneration.Utilities.CreationUtility.*;
import static HPalang.LTSGeneration.Utilities.FillerUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeDefinitionToGlobalStateConvertor
{
    public GlobalRunTimeState Convert(ModelDefinition model)
    {
        
        GlobalRunTimeState globalState = CreateEmptyGlobalState();
        
        FillDiscreteState(globalState, model.SoftwareActors());
        FillContinuousState(globalState, model.PhysicalActors());
        
        globalState.EventsState().PoolState().SetPool(new SimpleContinuousVariablePool(5));
        globalState.VariablePoolState().SetPool(new SimpleContinuousVariablePool(5));
        
        return globalState;
    }
}
