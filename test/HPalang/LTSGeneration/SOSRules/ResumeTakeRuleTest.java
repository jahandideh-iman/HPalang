/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statements.SendStatement;
import static HPalang.Core.Statement.StatementsFrom;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeTakeRuleTest
{
    
    LTSGenerator ltsGenerator = new LTSGenerator();
    LabeledTransitionSystem generatedLTS;
    GlobalRunTimeStateBuilder globalState = new GlobalRunTimeStateBuilder();
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ResumeTakeRule());
    }
    
    @Test
    public void ForEachActorStateIfThereIsResumeStatementAmongStatementsResumes()
    {
        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
       
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .SetDelayed(true)
                .EnqueueMessage(new EmptyMessage())
                .EnqueueMessage(new MessageWithBody(StatementsFrom(new ResumeStatement())));
        
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState nextGlobalState = globalState.Build();
        ActorRunTimeState nextActorState =  nextGlobalState.FindActorState(actor1);
        nextActorState .RemoveMessage(new MessageWithBody(StatementsFrom(new ResumeStatement())));
        nextActorState .SetDelayed(false);

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), nextGlobalState));
    }
}
