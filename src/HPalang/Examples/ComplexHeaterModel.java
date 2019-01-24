/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Examples;


import HPalang.Core.CommunicationType;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MainBlock;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.SetModeMessage;
import HPalang.Core.Mode;
import static HPalang.Core.ModelCreationUtilities.*;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.SimpleRealVariablePool;
import HPalang.Core.SoftwareActor;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class ComplexHeaterModel
{
    public static final String Heater__controller_instance = "controller";
    public static final String Heater__temperature = "temp";
    public static final String Heater__timer = "timer";
    public static final String Heater__onMode = "On";
    public static final String Heater__offMode = "Off";
    public static final float Heater_period_const = 0.05f;
    
    public static final String Controller__heater_instance = "heater";
    public static final String Controller__control = "control";
    public static final String Controller__control_tempParam = "temp";


   
    public static ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType heaterType = new PhysicalActorType("Heater");
        SoftwareActorType controllerType = new SoftwareActorType("Controller");

               
        FillSkeletonForHeaterType(heaterType, controllerType);
        FillSkeletonForControllerType(controllerType, heaterType);

        
        FillFleshForHeaterType(heaterType, controllerType.FindMessageHandler(Controller__control));
        FillFleshForControllerType(controllerType);

        PhysicalActor heater = new PhysicalActor("heater", heaterType, 1);
        SoftwareActor controller = new SoftwareActor("controller", controllerType, 1);
        

        FillHeaterActor(heater, controller);
        FillControllerActor(controller, heater);

        definition.AddActor(heater);
        definition.AddActor(controller);
                
        definition.SetMainBlock(new MainBlock());
         
        SetNetworkPriority(definition, controller , Controller__control, 0);
        // TODO: Find a less hacky way.
        SetNetworkPriority(definition, heater , new SetModeMessage(null), 1);
        
        SetNetworkDelay(definition, heater, controller, Controller__control, 0.01f);
        SetNetworkDelay(definition, controller, heater, new SetModeMessage(null), 0.01f);

        definition.SetInitialEventSystemVariablePool(new SimpleRealVariablePool(0));
        definition.SetInitialGlobalVariablePool(new SimpleRealVariablePool(1));
    
        return definition;
    }

    private static void FillSkeletonForHeaterType(PhysicalActorType heaterType, SoftwareActorType controllerType)
    {
        heaterType.AddInstanceParameter(new InstanceParameter(Heater__controller_instance, controllerType));
        
        heaterType.AddVariable(new RealVariable(Heater__temperature));
        heaterType.AddVariable(new RealVariable(Heater__timer));
        
        heaterType.AddMode(new Mode(Heater__onMode));
        heaterType.AddMode(new Mode(Heater__offMode));
        
        heaterType.SetInitialMode(heaterType.FindMode(Heater__onMode));
    }
    
    private static void FillFleshForHeaterType(PhysicalActorType heaterType,  MessageHandler controller_control)
    {   
        Mode onMode = heaterType.FindMode(Heater__onMode);
        Mode offMode = heaterType.FindMode(Heater__offMode);
        
        RealVariable temperature = (RealVariable) heaterType.FindVariable(Heater__temperature) ;    
        RealVariable timer = (RealVariable) heaterType.FindVariable(Heater__timer);

        InstanceParameter controllerInstance = heaterType.FindInstanceParameter(Heater__controller_instance); 
        
        
        onMode.SetInvarient(CreateInvarient(timer, "<=", Const(Heater_period_const)));
        onMode.SetGuard(CreateGuard(timer, "==", Heater_period_const));
        onMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        onMode.AddDifferentialEquation(new DifferentialEquation(
                temperature, 
                CreateBinaryExpression(
                        Const(4),
                        "-", 
                        CreateBinaryExpression(Const(0.1f), "*", VariableExpression(temperature)))));
        
        //onMode.AddAction(CreateResetFor(timer));        
        onMode.AddAction(CreateModeChangeStatement(onMode));
        onMode.AddAction(CreateSendStatement(controllerInstance, controller_control, VariableExpression(temperature)));
        
        offMode.SetInvarient(CreateInvarient(timer, "<=", Const(Heater_period_const)));
        offMode.SetGuard(CreateGuard(timer, "==", Heater_period_const));
        offMode.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        offMode.AddDifferentialEquation(new DifferentialEquation(
                temperature, 
                CreateBinaryExpression(
                        Const(0),
                        "-", 
                        CreateBinaryExpression(Const(0.1f), "*", VariableExpression(temperature)))));
        
        //offMode.AddAction(CreateModeChangeStatement(offMode));
        //offMode.AddAction(CreateResetFor(timer));
        //offMode.AddAction(CreateSendStatement(controllerInstance, controller_control, VariableExpression(temperature)));

    }
    

    private static void FillSkeletonForControllerType(SoftwareActorType controllerType, PhysicalActorType heaterType)
    {
        controllerType.AddInstanceParameter(new InstanceParameter(Controller__heater_instance, heaterType));
        
        MessageHandler controlMessageServer = new MessageHandler(Message.MessageType.Control);
        
        AddParameter(controlMessageServer, Controller__control_tempParam, FloatVariable.class);
        controllerType.AddMessageHandler(Controller__control, controlMessageServer);
    }

    private static void FillFleshForControllerType(SoftwareActorType controllerType)
    {
        InstanceParameter heater = controllerType.FindInstanceParameter(Controller__heater_instance);
        MessageHandler control = controllerType.FindMessageHandler(Controller__control);
        FloatVariable temperature = (FloatVariable) control.Parameters().Find(Controller__control_tempParam).Variable();

        control.AddStatement(new IfStatement(
                CreateBinaryExpression(temperature, ">=", Const(22)),
                Statement.StatementsFrom(CreateModeChangeSendStatement(Heater__offMode, heater)), 
                Statement.StatementsFrom(
                        new IfStatement(CreateBinaryExpression(
                                temperature, "<=", Const(18)),
                                Statement.StatementsFrom(CreateModeChangeSendStatement(Heater__onMode, heater)),
                                Statement.EmptyStatements()))));
        


    }

    private static void FillHeaterActor(PhysicalActor heater, SoftwareActor controller)
    {
        BindInstance(heater, Heater__controller_instance, controller, CommunicationType.CAN);
    }

    private static void FillControllerActor(SoftwareActor controller, PhysicalActor heater)
    {
        BindInstance(controller, Controller__heater_instance, heater, CommunicationType.CAN);
    }


}
