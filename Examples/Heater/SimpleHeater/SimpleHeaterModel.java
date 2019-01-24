/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Examples;


import HPalang.Core.*;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import static HPalang.Core.ModelCreationUtilities.*;
import HPalang.Core.Variables.RealVariable;
/**
 *
 * @author Iman Jahandideh
 */
public class SimpleHeaterModel
{
    public static final String Heater__controller_instance = "controller";
    public static final String Heater__temperature = "temp";
    public static final String Heater__onMode = "On";
    public static final String Heater__offMode = "Off";
    
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType heaterType = new PhysicalActorType("Heater");

               
        FillSkeletonForHeaterType(heaterType);
        
        FillFleshForHeaterType(heaterType);

        PhysicalActor heater = new PhysicalActor("heater", heaterType, 1);

        
        FillHeaterActor(heater);

        definition.AddActor(heater);
                
        definition.SetMainBlock(new MainBlock());
        
        definition.SetInitialEventSystemVariablePool(new SimpleRealVariablePool(0));
        definition.SetInitialGlobalVariablePool(new SimpleRealVariablePool(0));
        
        return definition;
    }

    private static void FillSkeletonForHeaterType(PhysicalActorType heaterType)
    {
        heaterType.AddVariable(new RealVariable(Heater__temperature));
        
        heaterType.AddMode(new Mode(Heater__onMode));
        heaterType.AddMode(new Mode(Heater__offMode));
        
        heaterType.SetInitialMode(heaterType.FindMode(Heater__onMode));
    }
    
    private static void FillFleshForHeaterType(PhysicalActorType heaterType)
    {   
        Mode onMode = heaterType.FindMode(Heater__onMode);
        Mode offMode = heaterType.FindMode(Heater__offMode);
        
        RealVariable temperature = (RealVariable) heaterType.FindVariable(Heater__temperature) ;    


 
        
        onMode.SetInvarient(CreateInvarient(temperature, "<=", Const(22f)));
        onMode.SetGuard(CreateGuard(temperature, "==", 22f));
        onMode.AddDifferentialEquation(new DifferentialEquation(
                temperature, 
                CreateBinaryExpression(
                        Const(4),
                        "-", 
                        CreateBinaryExpression(Const(0.1f), "*", VariableExpression(temperature)))));
        
        onMode.AddAction(CreateModeChangeStatement(offMode));
        
        offMode.SetInvarient(CreateInvarient(temperature, ">=", Const(18f)));
        offMode.SetGuard(CreateGuard(temperature, "==", 18f));
        offMode.AddDifferentialEquation(new DifferentialEquation(
                temperature, 
                CreateBinaryExpression(
                        Const(0),
                        "-", 
                        CreateBinaryExpression(Const(0.1f), "*", VariableExpression(temperature)))));
        
        offMode.AddAction(CreateModeChangeStatement(onMode));

    }
    


    private static void FillHeaterActor(PhysicalActor heater)
    {
        
    }
}
