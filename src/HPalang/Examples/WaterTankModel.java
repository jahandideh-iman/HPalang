/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Examples;

import HPalang.Core.*;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class WaterTankModel
{
    public static final String Tank__amount = "amount";
    public static final String Tank__inFlowCap = "inFlowCap";
    public static final String Tank__outFlowCap = "outFlowCap";
    public static final String Tank__inRate = "inRate";
    public static final String Tank__outRate = "outRate";
    public static final String Tank__openOutput = "OpenOutput";
    public static final String Tank__openInput = "OpenInput";
    public static final String Tank__closeInput = "CloseInput";
    public static final String Tank__emptyMode = "Empty";
    public static final String Tank__notEmptyMode = "NotEmpty";
    
    public static final String Sensor__controller_instance = "controller";
    public static final String Sensor__tank_instance = "tank";
    public static final String Sensor__timer = "timer";
    public static final String Sensor__activeMode = "active";
    
    
    public static final String Controller_tank_instance = "tank";
    public static final String Controller_tankAmount = "tankAmount";
    public static final String Controller_setTankAmount = "setTankAmount";
    public static final String Controller_control = "control";
    
    public static final String Clock_controller_instance = "controller";
    public static final String Clock_timer = "timer";
    public static final String Clock_activeMode = "Active";
    
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType tankType = new PhysicalActorType("Tank");
        PhysicalActorType sensorType = new PhysicalActorType("Sensor");
        SoftwareActorType controllerType = new SoftwareActorType("Controller");
        PhysicalActorType clockType = new PhysicalActorType("Clock");

               
//        FillSkeletonForTankType(tankType);
//        FillSkeletonForSensorType(sensorType, controllerType, tankType);
//        FillSkeletonForControllerType()
//        
//        FillFleshForHeaterType(heaterType);
//
//        PhysicalActor heater = new PhysicalActor("heater", heaterType, 1);
//
//        
//        FillHeaterActor(heater);

        //definition.AddActor(heater);
                
        definition.SetMainBlock(new MainBlock());
        
        definition.SetInitialEventSystemVariablePool(new SimpleRealVariablePool(0));
        definition.SetInitialGlobalVariablePool(new SimpleRealVariablePool(0));
        
        return definition;
    }

    private static void FillSkeletonForTankType(PhysicalActorType tankType)
    {
        tankType.AddVariable(CreateVariable(Variable.Type.real, Tank__amount));
        tankType.AddVariable(CreateVariable(Variable.Type.floatingPoint, Tank__inFlowCap));
        tankType.AddVariable(CreateVariable(Variable.Type.floatingPoint, Tank__outFlowCap));
        tankType.AddVariable(CreateVariable(Variable.Type.floatingPoint, Tank__inRate));
        tankType.AddVariable(CreateVariable(Variable.Type.floatingPoint, Tank__outRate));
        
        tankType.AddMessageHandler(Tank__openOutput, new MessageHandler(Message.MessageType.Control));
        tankType.AddMessageHandler(Tank__openInput, new MessageHandler(Message.MessageType.Control));
        tankType.AddMessageHandler(Tank__closeInput, new MessageHandler(Message.MessageType.Control));
        
        tankType.AddMode(new Mode(Tank__notEmptyMode));
        tankType.AddMode(new Mode(Tank__emptyMode));
        
        tankType.SetInitialMode(tankType.FindMode(Tank__notEmptyMode));
    }

    private static void FillSkeletonForSensorType(PhysicalActorType sensorType, SoftwareActorType controllerType, PhysicalActorType tankType)
    {
        sensorType.AddInstanceParameter(new InstanceParameter(Sensor__controller_instance, controllerType));
        sensorType.AddInstanceParameter(new InstanceParameter(Sensor__tank_instance, tankType));
        
        sensorType.AddVariable(CreateVariable(Variable.Type.floatingPoint, Sensor__timer));
        
        sensorType.AddMode(new Mode(Sensor__activeMode));
        
        sensorType.SetInitialMode(sensorType.FindMode(Sensor__activeMode));
    }
}
