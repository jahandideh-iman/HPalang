/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;
import HPalang.Core.Messages.MessageWithBody;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import TestUtilities.Utilities;
import Mocks.EmptyStatement;
import Mocks.FakeMessage;
import Mocks.NullExpression;
import java.security.Policy;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
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
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        SoftwareActorState nextActorState = expectedGlobalState.DiscreteState().FindActorState(actorState.Actor());
        nextActorState.MessageQueueState().Messages().Clear();
        nextActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
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

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        SoftwareActorState nextActorState = expectedGlobalState.DiscreteState().FindActorState(actorState.Actor());
        nextActorState.MessageQueueState().Messages().Clear();
        nextActorState.ExecutionQueueState().Statements().Enqueue(new AssignmentStatement(param1.Variable(), arg1.Value()));
        nextActorState.ExecutionQueueState().Statements().Enqueue(new AssignmentStatement(param2.Variable(), arg2.Value()));
        nextActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        nextActorState.ValuationState().Valuation().Add(param1.Variable());
        nextActorState.ValuationState().Valuation().Add(param2.Variable());
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
    }
    
    
    // TODO: Refactor the assertion
    @Test
    public void AddsMessageArgumentsToActosValuation()
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

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        SoftwareActorState nextActorState = expectedGlobalState.DiscreteState().FindActorState(actorState.Actor());
        nextActorState.MessageQueueState().Messages().Clear();
        nextActorState.ExecutionQueueState().Statements().Enqueue(new AssignmentStatement(param1.Variable(), arg1.Value()));
        nextActorState.ExecutionQueueState().Statements().Enqueue(new AssignmentStatement(param2.Variable(), arg2.Value()));
        nextActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        nextActorState.ValuationState().Valuation().Add(param1.Variable());
        nextActorState.ValuationState().Valuation().Add(param2.Variable());
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
    }

    private SoftwareActorState CreateBlankActorStateWith(Message message)
    {
        SoftwareActorState actorState =  Utilities.CreateSoftwareActorState("actor");
        actorState.SetSuspended(false);
        actorState.ExecutionQueueState().Statements().Clear();
        actorState.MessageQueueState().Messages().Enqueue(message);
        return actorState;
    }
}
