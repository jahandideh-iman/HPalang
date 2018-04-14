/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ActorLocators.DirectActorLocator;
import HPalang.Core.CommunicationType;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DelegationParameter;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.*;
import HPalang.Core.InstanceParameter;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Mode;
import HPalang.Core.ModelDefinition;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.SoftwareActorType;
import HPalang.Core.Statement;
import HPalang.Core.Statements.*;
import HPalang.Core.Variables.*;
import static HPalang.Core.ModelCreationUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class DrawBridge
{
    static final String Clock__callback_param = "callBack";
    static final String Clock__timer = "timer";
    static final String Clock__on_mode = "On";
    
    static final String Car_Dispatcher__controller_param = "controller";
    static final String Car_Dispatcher__total_cars = "totalCars";
    static final String Car_Dispatcher__dispatch_handler = "dispatch";
    
    static final String DBC__bridge_param = "drawBridge";
    static final String DBC__clock_param = "clock";
    static final String DBC__cars = "cars";
    static final String DBC__bridge_status = "drawBridgeStatus";
    static final String DBC__enqueue_car_handler = "enqueueCar";
    static final String DBC__bridge_lowered_handler = "bridgeLowered";
    static final String DBC__bridge_raised_handler = "bridgeRaised";
    static final String DBC__car_passed_handler = "ACarPassed";
    
    static final String Draw_Bridge__controller_param = "controller";
    static final String Draw_Bridge__degree = "degree";
    static final String Draw_Bridge__lowering_mode = "lowering";
    static final String Draw_Bridge__raising_mode = "raising";


    
    
    static public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
        PhysicalActorType clockType = new PhysicalActorType("Clock");
        SoftwareActorType carDispatcherType = new SoftwareActorType("CarDispatcher");
        SoftwareActorType drawBridgeContollerType = new SoftwareActorType("DrawBridgeController");
        PhysicalActorType drawBridgeType = new PhysicalActorType("DrawBridge");
        
        
        FillSkeletonForClockType(clockType);
        FillSkeletonForCarDispatcherType(carDispatcherType, drawBridgeContollerType);
        FillSkeletonForDrawBridgeController(drawBridgeContollerType, clockType, drawBridgeType);
        FillSkeletonForDrawBridgeType(drawBridgeType, drawBridgeContollerType);
        
        FillFleshForClockType(clockType);
        FillFleshForCarDispatcherType(carDispatcherType);
        FillFleshForDrawBridgeController(drawBridgeContollerType);
        FillFleshForDrawBridgeType(drawBridgeType);
        
        PhysicalActor carDispatcherClock = new PhysicalActor("carDisptacherClock", clockType, 10);
        SoftwareActor carDispatcher = new SoftwareActor("carDispatcher", carDispatcherType, 10);
        
        PhysicalActor carPassageClock = new PhysicalActor("carPassageClock", clockType, 10);
        SoftwareActor drawBridgeController = new SoftwareActor("drawBridgeController", drawBridgeContollerType, 10);
        
        PhysicalActor drawBridge = new PhysicalActor("drawBridge", drawBridgeType, 0);
        
        BindDelagation(carDispatcherClock, Clock__callback_param, carDispatcher,  Car_Dispatcher__dispatch_handler, CommunicationType.Wire);
        BindInstance(carDispatcher, Car_Dispatcher__controller_param, drawBridgeController, CommunicationType.Wire);
        
        BindDelagation(carPassageClock, Clock__callback_param, drawBridgeController, DBC__car_passed_handler, CommunicationType.Wire);
        BindInstance(drawBridgeController, DBC__bridge_param, drawBridge, CommunicationType.Wire);
        BindInstance(drawBridgeController, DBC__clock_param, carPassageClock, CommunicationType.Wire);
        
        BindInstance(drawBridge, Draw_Bridge__controller_param, drawBridgeController, CommunicationType.Wire);
        
        carDispatcher.SetInitialValue((IntegerVariable)carDispatcher.Type().FindVariable(Car_Dispatcher__total_cars), 2); 
        definition.AddActor(carDispatcherClock);
        definition.AddActor(carDispatcher);
        
        definition.AddActor(carPassageClock);
        definition.AddActor(drawBridgeController);
        definition.AddActor(drawBridge);
        
        MainBlock main = new MainBlock();
        
        main.AddSendStatement(
                CreateModeChangeSendStatement(
                        clockType.FindMode(Clock__on_mode),
                        new DirectActorLocator(carDispatcherClock)));
        
        definition.SetMainBlock(main);
        return definition;
    }

    static private void FillSkeletonForClockType(PhysicalActorType clockType)
    {
        DelegationParameter callback = new DelegationParameter(Clock__callback_param);
        RealVariable timer = new RealVariable(Clock__timer);
        
        clockType.AddDelegationParameter(callback);
        clockType.AddVariable(timer);
        
        clockType.AddMode(new Mode(Clock__on_mode));
    }

    static private void FillSkeletonForCarDispatcherType(SoftwareActorType carDispatcherType, SoftwareActorType drawBridgeContollerType)
    {
        InstanceParameter instanceParam = new InstanceParameter(Car_Dispatcher__controller_param, drawBridgeContollerType);
        carDispatcherType.AddInstanceParameter(instanceParam);
        
        IntegerVariable totalCars = new IntegerVariable(Car_Dispatcher__total_cars);
        carDispatcherType.AddVariable(totalCars);
        
        AddControlMessageHandler(carDispatcherType, Car_Dispatcher__dispatch_handler);
    }

    static private void FillSkeletonForDrawBridgeController(SoftwareActorType DBCType, PhysicalActorType clockType, PhysicalActorType drawBridgeType)
    {
        AddInstanceParameter(DBCType, DBC__bridge_param, drawBridgeType);
        AddInstanceParameter(DBCType, DBC__clock_param, clockType);
        
        AddVariable(DBCType, new IntegerVariable(DBC__cars));
        AddVariable(DBCType, new IntegerVariable(DBC__bridge_status));
        
        AddControlMessageHandler(DBCType, DBC__enqueue_car_handler);
        AddControlMessageHandler(DBCType, DBC__bridge_lowered_handler);
        AddControlMessageHandler(DBCType, DBC__bridge_raised_handler);
        AddControlMessageHandler(DBCType, DBC__car_passed_handler);
    }

    static private void FillSkeletonForDrawBridgeType(PhysicalActorType drawBridgeType, SoftwareActorType DBCType)
    {
        AddInstanceParameter(drawBridgeType, Draw_Bridge__controller_param, DBCType);
        AddVariable(drawBridgeType, new RealVariable(Draw_Bridge__degree));
        
        drawBridgeType.AddMode(new Mode(Draw_Bridge__lowering_mode));
        drawBridgeType.AddMode(new Mode(Draw_Bridge__raising_mode));
    }

    static private void FillFleshForClockType(PhysicalActorType clockType)
    {
        Mode on = clockType.FindMode(Clock__on_mode);
        RealVariable timer = (RealVariable) clockType.FindVariable(Clock__timer);
        DelegationParameter callback = clockType.FindDelegationParameter(Clock__callback_param);
        
        
        on.SetInvarient(CreateInvarient(timer, "<=", Const(5f)));
        on.AddDifferentialEquation(new DifferentialEquation(timer, Const(1f)));
        on.SetGuard(CreateGuard(timer, "==", 5f));
        on.AddAction(CreateResetFor(timer));
        on.AddAction(CreateSendStatement(callback));
    }

    static private void FillFleshForCarDispatcherType(SoftwareActorType carDispatcherType)
    {
        MessageHandler dispatch = carDispatcherType.FindMessageHandler(Car_Dispatcher__dispatch_handler);
        IntegerVariable totalCars = (IntegerVariable) carDispatcherType.FindVariable(Car_Dispatcher__total_cars);
        InstanceParameter controllerParam = carDispatcherType.FindInstanceParameter(Car_Dispatcher__controller_param);
        
        dispatch.AddStatement(
                new IfStatement(
                        CreateGreaterExpression(new VariableExpression(totalCars),new ConstantDiscreteExpression(0)), 
                        Statement.StatementsFrom(
                                new AssignmentStatement(totalCars, CreateSubtractExpression(new VariableExpression(totalCars), new ConstantDiscreteExpression(1))),
                                CreateSendStatement(controllerParam, DBC__enqueue_car_handler)), 
                        Statement.StatementsFrom()));
        
        
    }

    static private void FillFleshForDrawBridgeController(SoftwareActorType DBCType)
    {
        InstanceParameter drawBridge = DBCType.FindInstanceParameter(DBC__bridge_param);
        InstanceParameter clock  = DBCType.FindInstanceParameter(DBC__clock_param);
        
        IntegerVariable cars = (IntegerVariable) DBCType.FindVariable(DBC__cars);
        IntegerVariable bridgeStatus = (IntegerVariable) DBCType.FindVariable(DBC__bridge_status);
        
        MessageHandler enqueueCar = DBCType.FindMessageHandler(DBC__enqueue_car_handler);
        enqueueCar.AddStatement(
                new AssignmentStatement(cars, 
                CreateAddExpression(new VariableExpression(cars), new ConstantDiscreteExpression(1) )));
        
        enqueueCar.AddStatement(new IfStatement(
                        CreateEqualityExpression(new VariableExpression(bridgeStatus), new ConstantDiscreteExpression(0)), 
                        Statement.StatementsFrom(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(1)),
                                CreateModeChangeSendStatement(Draw_Bridge__lowering_mode, drawBridge)),
                        Statement.EmptyStatements()
                        
                )
        );
        
        MessageHandler bridgeLowred = DBCType.FindMessageHandler(DBC__bridge_lowered_handler);
        
        bridgeLowred.AddStatement(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(2)));
        bridgeLowred.AddStatement(new IfStatement(
                        CreateLesserEqualExpression(new VariableExpression(cars), new ConstantContinuousExpression(0)), 
                        Statement.StatementsFrom(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(1)),
                                CreateModeChangeSendStatement(Draw_Bridge__raising_mode, drawBridge)),
                        Statement.StatementsFrom(CreateModeChangeSendStatement(Clock__on_mode, clock))));
        
        MessageHandler bridgeRaised = DBCType.FindMessageHandler(DBC__bridge_raised_handler);
        
        bridgeRaised.AddStatement(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(0)));
        bridgeRaised.AddStatement(new IfStatement(
                        CreateGreaterExpression(new VariableExpression(cars), new ConstantContinuousExpression(0)), 
                        Statement.StatementsFrom(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(1)),
                                CreateModeChangeSendStatement(Draw_Bridge__lowering_mode, drawBridge)
                        ),
                        Statement.StatementsFrom()
                )
        );
        
        
        MessageHandler carPassedHandler = DBCType.FindMessageHandler(DBC__car_passed_handler);
        
        carPassedHandler.AddStatement(new IfStatement(
                        CreateLesserEqualExpression(new VariableExpression(cars), new ConstantDiscreteExpression(0)), 
                        Statement.StatementsFrom(new AssignmentStatement(bridgeStatus, new ConstantDiscreteExpression(1)),
                                CreateModeChangeSendStatement(Draw_Bridge__raising_mode, drawBridge),
                                CreateDeactiveModeRequest(drawBridge)
                        ), 
                        Statement.StatementsFrom(
                                CreateModeChangeSendStatement(Clock__on_mode, clock))
                )
        );

    }

    static private void FillFleshForDrawBridgeType(PhysicalActorType drawBridgeType)
    {
        InstanceParameter controller = drawBridgeType.FindInstanceParameter(Draw_Bridge__controller_param);
        RealVariable degree = (RealVariable) drawBridgeType.FindVariable(Draw_Bridge__degree);
        
        Mode loweringMode = drawBridgeType.FindMode(Draw_Bridge__lowering_mode);
        Mode raisingMode = drawBridgeType.FindMode(Draw_Bridge__raising_mode);
        
        
        loweringMode.SetInvarient(CreateInvarient(degree, ">=", Const(0f)));
        loweringMode.AddDifferentialEquation(new DifferentialEquation(degree,Const(-10f)));
        loweringMode.SetGuard(CreateGuard(degree, "==", 0f));
        loweringMode.AddAction(new ModeChangeStatement(Mode.None()));
        loweringMode.AddAction(CreateSendStatement(controller, DBC__bridge_lowered_handler));
        
        raisingMode.SetInvarient(CreateInvarient(degree, "<=", Const(90f)));
        raisingMode.AddDifferentialEquation(new DifferentialEquation(degree, CreateBinaryExpression(degree, "/", Const(100f))));
        raisingMode.SetGuard(CreateGuard(degree, "==", 90));
        raisingMode.AddAction(new ModeChangeStatement(Mode.None()));
        raisingMode.AddAction(CreateSendStatement(controller, DBC__bridge_raised_handler));   
    }
}
