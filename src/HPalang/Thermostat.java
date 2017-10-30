/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.MainBlock;
import HPalang.Core.Mode;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.Core.Variables.RealVariable;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class Thermostat
{
    static final String Thermostat__t = "t";
    static final String Thermostat__on_mode = "On";
    static final String Thermostat__off_mode = "Off";
    
    static public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType thermostatType = new PhysicalActorType("Thermostat");
        
        FillSkeletonForThermostatType(thermostatType);
        FillFleshForThermostatType(thermostatType);
        
        PhysicalActor thermostat = new PhysicalActor("thermostat", thermostatType, 10);
        
        definition.AddActor(thermostat);
        
        definition.SetMainBlock(new MainBlock());

        return definition;
    }
    private static void FillSkeletonForThermostatType(PhysicalActorType thermostatType)
    {
        AddVariable(thermostatType, new RealVariable(Thermostat__t));
        
        thermostatType.AddMode(new Mode(Thermostat__on_mode));
        thermostatType.AddMode(new Mode(Thermostat__off_mode));

        
        thermostatType.SetInitialMode(thermostatType.FindMode(Thermostat__off_mode));
    }

    private static void FillFleshForThermostatType(PhysicalActorType thermostatType)
    {
        RealVariable t = (RealVariable) thermostatType.FindVariable(Thermostat__t);

        Mode on = thermostatType.FindMode(Thermostat__on_mode);
        Mode off = thermostatType.FindMode(Thermostat__off_mode);

        
        
        on.SetInvarient(CreateInvarient(t, "<=", Const(25f)));
        on.AddDifferentialEquation(new DifferentialEquation(t, 
                CreateBinaryExpression(t, "+", Const(100f))));
        on.SetGuard(CreateGuard(t, "==", 25));
        on.AddAction(new ModeChangeStatement(off));
        
        off.SetInvarient(CreateInvarient(t, ">=", Const(24f)));
        off.AddDifferentialEquation(new DifferentialEquation(t, CreateBinaryExpression(t, "+", Const(50f))));
        off.SetGuard(CreateGuard(t, "==", 24));
        off.AddAction(new ModeChangeStatement(on));
        
        
    }

}
