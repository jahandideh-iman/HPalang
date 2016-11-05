/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import Builders.ActorBuilder;
import Builders.ProgramDefinitionBuilder;
import HPalang.Core.Actor;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.ProgramDefinition;
import HPalang.Statements.SendStatement;
import Mocks.SOSRuleMock;
import Mocks.SOSRuleMonitor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGeneratorTest
{     
    LTSGenerator ltsGenerator = new LTSGenerator();
    
    @Test
    public void InitialStateHasMainBlockMessages()
    {
        ProgramDefinition program = new ProgramDefinition();
        
        Actor actor = CreateActorBuilder()
                .WithID("actor")
                .WithCapacity(1)
                .WithHandler("handler", new MessageHandler())
                .Build();
        
        
        MainBlock mainBlock = new MainBlock();
        mainBlock.AddSendStatement(new SendStatement(actor, new NormalMessage(actor.GetMessageHandler("handler"))));
        
        program.SetMainBlock(mainBlock);
        program.AddActor(actor);
        
        LabeledTransitionSystem generatedLTS = ltsGenerator.Generate(program.ToGlobalState());
        
        GlobalRunTimeState initalState = generatedLTS.GetInitialState();
        Queue<Message> messages = initalState.FindActorState(actor).GetMessages();
        NormalMessage message = new NormalMessage(actor.GetMessageHandler("handler"));
        
        assertThat(messages.size(), is(1));
        assertThat(messages, hasItem(message));
    }
    
    @Test
    public void InitialGlobalStateIsGivenToSOSRules()
    {
        ProgramDefinition emptyProgram = ProgramDefinitionBuilder.EmptyProgram();
        
        SOSRuleMonitor rule1 = new SOSRuleMonitor();        
        SOSRuleMonitor rule2 = new SOSRuleMonitor();
        
        ltsGenerator.AddSOSRule(rule1);
        ltsGenerator.AddSOSRule(rule2);
        
        LabeledTransitionSystem lts = ltsGenerator.Generate(emptyProgram.ToGlobalState());
        
        assertThat(rule1.appliedStates,hasItem(equalTo(lts.GetInitialState())));
        assertThat(rule2.appliedStates,hasItem(equalTo(lts.GetInitialState())));
    }
    
    @Test
    public void TransitionCanBeAddedDuringGeneration()
    {
        ProgramDefinition emptyProgram = ProgramDefinitionBuilder.EmptyProgram();
        
        GlobalRunTimeState transitionState = new GlobalRunTimeState();
        transitionState.AddActorRunTimeState(new ActorRunTimeState(new Actor("actor",0)));
        
        SOSRuleMock rule = new SOSRuleMock(new TauLabel(),transitionState);        
        
        ltsGenerator.AddSOSRule(rule);

        LabeledTransitionSystem lts = ltsGenerator.Generate(emptyProgram.ToGlobalState());
        
        assertTrue(lts.HasTransition(lts.GetInitialState(),rule.transitonLabel,rule.transitionState));
    }
    
    ActorBuilder CreateActorBuilder()
    {
        return new ActorBuilder();
    }
}
