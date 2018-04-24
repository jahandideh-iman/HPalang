/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import HPalang.LTSGeneration.Builders.SoftwareActorStateBuilder;
import HPalang.LTSGeneration.Builders.GlobalRunTimeStateBuilder;
import HPalang.LTSGeneration.Builders.PhysicalActorStateBuilder;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.Mode;
import static HPalang.Core.Mode.EquationsFrom;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.Core.SimpleRealVariablePool;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.Core.TransitionSystem.Label;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.State;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;
import static HPalang.Core.CreationUtilities.CreateTrivialFlaseGuard;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.SoftwareActorType;
import Mocks.EmptyStatement;
import HPalang.Core.NullExpression;
import HPalang.Core.TransitionSystem.SimpleLTSState;
import Mocks.LabelMock;
import java.util.Collections;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import HPalang.Core.TransitionSystem.StateWrapper;

/**
 *
 * @author Iman Jahandideh
 */
public class CoreUtility
{
    
    static public SoftwareActorType EmptySoftwareActorType()
    {
        return new SoftwareActorType("type");
    }
    static public ContinuousBehavior EmptyBehavior()
    {
        return new ContinuousBehavior("", DifferentialEquation.Empty(""), "", Statement.StatementsFrom(new EmptyStatement()));
    }
    
    static public SoftwareActorState CreateSoftwareActorState(String actorName)
    {
        return CreateSoftwareActorState(actorName, 1);
    }
    
    static public SoftwareActorState CreateSoftwareActorState(String actorName, int capacity)
    {
        SoftwareActorStateBuilder builder = new SoftwareActorStateBuilder().
                WithActor(CreateSofwareActor(actorName, capacity));
        
        return builder.Build();
    }
    
    static public SoftwareActor CreateSofwareActor(String actorName, int capacity)
    {
        return new SoftwareActor(actorName, EmptySoftwareActorType(), capacity);
    }
    
    static public SoftwareActor CreateSofwareActor(String actorName)
    {
        return new SoftwareActor(actorName, EmptySoftwareActorType(), 0);
    }
    
    static public PhysicalActor CreatePhysicalActor(String actorName, Mode ... modes )
    {
        PhysicalActorType type = new PhysicalActorType(actorName + "Type");
        PhysicalActor actor = CreatePhysicalActor(actorName, type);
        
        for(Mode mode : modes)
            type.AddMode(mode);
        
        return actor;
    }
    
    static public PhysicalActor CreatePhysicalActor(String actorName, PhysicalActorType type)
    {
        PhysicalActor actor = new PhysicalActor(actorName, type, 0);
        return actor;
    }
    
    static public PhysicalActorState CreatePhysicalActorState(String actorName, Mode ... modes )
    {
        PhysicalActorType type = new PhysicalActorType(actorName + "Type");
        for(Mode mode : modes)
            type.AddMode(mode);
        
        PhysicalActor actor = CreatePhysicalActor(actorName, type);
        
        PhysicalActorStateBuilder builder = new PhysicalActorStateBuilder().
                WithActor(actor);
        
        return builder.Build();
    }
    
        
    public static void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
    
    public static GlobalRunTimeState CreateGlobalState()
    {
        GlobalRunTimeStateBuilder builder = new GlobalRunTimeStateBuilder();
        
        return builder.Build();
    }
    
 
        
    public static ContinuousState CreateContinuousState(PhysicalActorState actorState)
    {
        ContinuousState continuousState = new ContinuousState();
        
        continuousState.AddPhysicalActorState(actorState);
        
        return continuousState;
    }
    
    public static PhysicalActorState CreatePhysicalState(String actorName, State substate)
    {
        PhysicalActorState state =  new PhysicalActorState(CreatePhysicalActor(actorName));
        
        state.AddSubstate(substate);
        
        return state;
    }
        
    public static StateInfo SimpleStateInfo(GlobalRunTimeState globalState)
    {
        return new StateInfo(globalState, Collections.EMPTY_LIST , Collections.EMPTY_LIST);
    }
    
    static public SoftwareActorState FindActorState(SoftwareActor actor, GlobalRunTimeState globalState )
    {
        return globalState.DiscreteState().FindActorState(actor);
    }
    
    static public PhysicalActorState FindActorState(PhysicalActor actor, GlobalRunTimeState globalState )
    {
        return globalState.ContinuousState().FindActorState(actor);
    }
   
    static public  void ClearStatementsFor(SoftwareActorState actorState)
    {
        actorState.ExecutionQueueState().Statements().Clear();
    }

    static public void ClearStatementsFor(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        globalState.DiscreteState().FindActorState(actor).ExecutionQueueState().Statements().Clear();
    }
    
    static public void ClearStatementsFor(PhysicalActor actor, GlobalRunTimeState globalState)
    {
        globalState.ContinuousState().FindActorState(actor).ExecutionQueueState().Statements().Clear();
    }
    
    static public  void DequeueOneStatemenet(SoftwareActorState actorState)
    {
        actorState.ExecutionQueueState().Statements().Dequeue();
    }
    
    static public  void DequeueOneStatemenet(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        FindActorState(actor, globalState).ExecutionQueueState().Statements().Dequeue();
    }

    static public void EnqueueStatement(Statement statement, ActorState actorState)
    {
        actorState.ExecutionQueueState().Statements().Enqueue(statement);
    }
    
    static public void EnqueueStatements(Queue<Statement> statements, SoftwareActor actor, GlobalRunTimeState globalState)
    {
        FindActorState(actor, globalState).ExecutionQueueState().Statements().Enqueue(statements);
    }
    
    static public void ResetEventStatePool(GlobalRunTimeState globalState, int capacity)
    {
        globalState.EventsState().PoolState().SetPool(new SimpleRealVariablePool(capacity));
    }

    static public void ResetEventStatePool(GlobalRunTimeState globalState)
    {
        ResetEventStatePool(globalState, 1);
    }
        
    static public void ResetGlobalVariablePoolState(GlobalRunTimeState globalState, int capacity)
    {
        globalState.VariablePoolState().SetPool(new SimpleRealVariablePool(capacity));
    }
    
    static public Transition SelfTransition(GlobalRunTimeState globalState, Label label)
    {
        return CreateTransition(globalState, label, globalState);
    }
    
    static public <T> Transition CreateTransition(T origin, Label label, T destination)
    {
        return new Transition<>(new SimpleLTSState<>(origin), label, new SimpleLTSState<>(destination));
    }
    
    static public void RegisterEvent(float delay, Action action, GlobalRunTimeState globalState)
    {
        globalState.EventsState().RegisterEvent(delay, action);
    }
    
    static public void AddActorState(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        globalState.DiscreteState().AddSoftwareActorState(actorState);
    }
    
    static public void AddActorState(PhysicalActorState actorState, GlobalRunTimeState globalState)
    {
        globalState.ContinuousState().AddPhysicalActorState(actorState);
    }
    
    static public void AddActorState(ActorState actorState, GlobalRunTimeState globalState)
    {
        if(actorState instanceof SoftwareActorState)
            AddActorState((SoftwareActorState)actorState , globalState);
        else if(actorState instanceof PhysicalActorState)
            AddActorState((PhysicalActorState)actorState , globalState);
        
        throw new RuntimeException("Unsupported ActorState type.");
    }

    static public Mode CreateEmptyMode(String name)
    {
        return new Mode(
                name,
                TrivialInvarient(), 
                EquationsFrom(DifferentialEquation.Empty()),
                CreateTrivialFlaseGuard(), 
                Statement.EmptyStatements());
    }
    
    static public void SetMode(Mode mode, PhysicalActor actor, GlobalRunTimeState globalState)
    {
        globalState.ContinuousState().FindActorState(actor).SetMode(mode);
    }
    

    static public Invarient TrivialInvarient()
    {
        return new Invarient(new TrueConst());
    }
    
    static public Invarient FakeInvarient(String expr)
    {
        return new Invarient(new NullExpression(expr));
    }
    
    static public GlobalRunTimeState CreateUniqueGlobalState(String id)
    {
        GlobalRunTimeState state = CreateGlobalState();
        state.AddSubstate(CreateSoftwareActorState(id));
        
        return state;
    }
    
    static public Label CreateLabel(String id)
    {
        return new LabelMock(id);
    }
}
