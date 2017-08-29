/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.MessageParameters;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.SoftwareActor;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import TestUtilities.CoreUtility;
import Mocks.EmptyStatement;
import Mocks.FakeMessage;
import Mocks.NullExpression;
import static TestUtilities.CoreUtility.SimpleStateInfo;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */

// TODO: Find a better way for testing the expected outcome of the tests
public class FIFOMessageTakeRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        rule = new FIFOMessageTakeRule();
    }
    
    @Test
    public void TakesAMessageIfIsNotSuspendedAndExecutionQueueIsEmpty()
    {
        Message message = new MessageWithBody(StatementsFrom(new EmptyStatement("s1"), new EmptyStatement("s2")));
        
        SoftwareActorState actorState =  CreateBlankActorStateWith(message);      
        globalState.DiscreteState().AddSoftwareActorState(actorState);
        
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, actorState, message);
        
        
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }
    
    // TODO: Refactor the assertion.
    @Test
    public void AddsAnAssignmentStatementAtTheBeginingForEachArgument()
    {
        VariableParameter param1 = new VariableParameter(new IntegerVariable("param1"));
        VariableParameter param2 = new VariableParameter(new IntegerVariable("param2"));
        
        VariableArgument arg1 = new VariableArgument(param1, new NullExpression("e1"));
        VariableArgument arg2 = new VariableArgument(param2, new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param1, param2));
        
        message.AddArgument(arg1);
        message.AddArgument(arg2);
        
        SoftwareActorState actorState =  CreateBlankActorStateWith(message);
        
        globalState.DiscreteState().AddSoftwareActorState(actorState);

        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, actorState, message);
        
        
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }
    
    
    // TODO: Refactor the assertion
    @Test
    public void AddsMessageArgumentsToActorsValuation()
    {
        VariableParameter param1 = new VariableParameter(new IntegerVariable("param1"));
        VariableParameter param2 = new VariableParameter(new IntegerVariable("param2"));
        
        VariableArgument arg1 = new VariableArgument(param1, new NullExpression("e1"));
        VariableArgument arg2 = new VariableArgument(param2, new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param1, param2));
        
        message.AddArgument(arg1);
        message.AddArgument(arg2);
        
        SoftwareActorState actorState =  CreateBlankActorStateWith(message);
        
        globalState.DiscreteState().AddSoftwareActorState(actorState);

        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, actorState, message);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }

    @Test
    public void AddsATearDownStatementAfterMessageBodyToRemoveMessageParametersFromValuation()
    {
        VariableParameter param = new VariableParameter(new IntegerVariable("param"));
        VariableArgument arg = new VariableArgument(param, new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param));
        message.AddArgument(arg);
        
        SoftwareActorState actorState =  CreateBlankActorStateWith(message);
        globalState.DiscreteState().AddSoftwareActorState(actorState);

        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, actorState, message);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }
    
    private GlobalRunTimeState ExpectedGlobalStateWhenMessageIsTaken(GlobalRunTimeState originalState, SoftwareActorState senderState , Message message)
    {
        GlobalRunTimeState expectedGlobalState = originalState.DeepCopy();
        SoftwareActorState expectedActorState = expectedGlobalState.DiscreteState().FindActorState(senderState.Actor());
        expectedActorState.MessageQueueState().Messages().Dequeue();
        
        for(VariableArgument argument : message.Arguments().AsSet())
            expectedActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(argument.Parameter().Variable(), argument.Value()));
        
        expectedActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        for (VariableParameter parameter : message.Parameters().AsSet()) {
            expectedActorState.ValuationState().Valuation().Add(parameter.Variable());
        }
        expectedActorState.ExecutionQueueState().Statements().Enqueue(
                new MessageTeardownStatement(message.Parameters()));
        
        return expectedGlobalState;
    }
    
    private SoftwareActorState CreateBlankActorStateWith(Message message)
    {
        SoftwareActorState actorState =  CoreUtility.CreateSoftwareActorState("actor");
        actorState.SetSuspended(false);
        actorState.ExecutionQueueState().Statements().Clear();
        
        MessagePacket packet = new MessagePacket(
                CoreUtility.CreateSofwareActor("null"),
                CoreUtility.CreateSofwareActor("null"),
                message, 
                new MessageArguments());
        
        actorState.MessageQueueState().Messages().Enqueue(packet);
        return actorState;
    }
}
