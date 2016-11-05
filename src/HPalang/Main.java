/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Convertors.HybridAutomatonToXMLConvertor;
import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.Convertors.LTSToXMLConvertor;
import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.HybridAutomataGeneration.SOSRules.ConversionRule;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.NormalMessage;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.ContinuousBehaviorDepricationRule;
import HPalang.LTSGeneration.SOSRules.ContinuousBehaviorRule;
import HPalang.LTSGeneration.SOSRules.DelayRule;
import HPalang.LTSGeneration.SOSRules.MessageDropRule;
import HPalang.LTSGeneration.SOSRules.MessageSendRule;
import HPalang.LTSGeneration.SOSRules.MessageTakeRule;
import HPalang.LTSGeneration.SOSRules.ResumeTakeRule;
import HPalang.LTSGeneration.TauLabel;
import HPalang.LTSGeneration.Transition;
import HPalang.Statements.ContinuousBehaviorStatement;
import HPalang.Statements.DelayStatement;
import HPalang.Statements.SendStatement;
import HPalang.Statements.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import sun.rmi.runtime.Log;

/**
 *
 * @author Iman Jahandideh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        LTSGenerator ltsGenerator = new LTSGenerator();
        
        ltsGenerator.AddSOSRule(new MessageTakeRule());
        ltsGenerator.AddSOSRule(new DelayRule());
        ltsGenerator.AddSOSRule(new MessageSendRule());
        ltsGenerator.AddSOSRule(new MessageDropRule());
        ltsGenerator.AddSOSRule(new ResumeTakeRule());
        ltsGenerator.AddSOSRule(new ContinuousBehaviorDepricationRule());
        ltsGenerator.AddSOSRule(new ContinuousBehaviorRule());
        
        HybridAutomatonGenerator hybridAutomatonGenerator = new HybridAutomatonGenerator();
        hybridAutomatonGenerator.AddSOSRule(new ConversionRule());
        
        ProgramDefinition definition = CreateProgramWithComplexMessageing();
        
        LabeledTransitionSystem lts =  ltsGenerator.Generate(definition.ToGlobalState());
        
        FileWriter writer = new FileWriter();
        
        PrioritizeTauActions(lts);
        RemoveUnreachableStates(lts);
        RemoveTauLabels(lts);
        //Prune(lts);
        
        HybridAutomaton automaton = hybridAutomatonGenerator.Generate(lts);
        
        //writer.Write("output.xml", new LTSToXMLConvertor().Convert(lts));
        writer.Write("output.xml", new HybridAutomatonToXMLConvertor().Convert(automaton));
        
        System.out.println("LTS States : " + lts.GetStates().size());
        System.out.println("LTS Transition : " + lts.GetTransitions().size());
        
        System.out.println("HA Locations : " + automaton.GetLocations().size());
        System.out.println("HA Transition : " + automaton.GetTransitions().size());
        
    }
    
    private static void PrioritizeTauActions(LabeledTransitionSystem lts)
    {
        while(true)
        {
            boolean change = false;
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
            {
                List<Transition> outTrans = lts.GetTransitionsFrom(state);
                boolean hasTauLabled = false;
                for(Transition t : outTrans)
                    if(t.GetLabel() instanceof TauLabel)
                    {
                        hasTauLabled = true;
                        break;
                    }
                
                if(hasTauLabled)
                {
                    for(Transition t : outTrans)
                        if(t.GetLabel() instanceof  TauLabel == false)
                            lts.RemoveTranstion(t);
                }
            }
            
            if(change == false)
                break;
        }
    }
    
    private static void Prune(LabeledTransitionSystem lts)
    {

        while(true)
        {
            boolean change = false;
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
            {
                List<Transition> outTrans = lts.GetTransitionsFrom(state);
                List<Transition> inTrans = lts.GetTransitionsTo(state);
                
                if(inTrans.size() == 1 && outTrans.size() == 1
                        && inTrans.get(0).GetLabel() instanceof TauLabel
                        && state.equals(lts.GetInitialState()) == false)
                {
                    lts.RemoveTranstion(inTrans.get(0));
                    lts.RemoveTranstion(outTrans.get(0));
                    lts.RemoveState(state);
                    
                    lts.AddTransition(inTrans.get(0).GetOrign(), outTrans.get(0).GetLabel() , outTrans.get(0).GetDestination());
                    change = true;
                }
            }
            
            if(change == false)
                break;
        }
//        for(GlobalRunTimeState state : lts.GetStates())
//        {
//            List<LabeledTransitionSystem.Transition> trans = lts.GetTransitionsFrom(state);
//            
//            boolean hasTauLabel = false;
//            for(LabeledTransitionSystem.Transition t : trans)
//            {
//                if(t.GetLabel() instanceof TauLabel)
//                {
//                    hasTauLabel = true;
//                    break;
//                }
//            }
//            if(hasTauLabel)
//            {
//                for(LabeledTransitionSystem.Transition t : trans)
//                {
//                    if(t.GetLabel() instanceof TauLabel == false)
//                         lts.RemoveTranstion(t);
//                }
//            }
//        }
        
//        
//        for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
//        {
//            List<LabeledTransitionSystem.Transition> fromTrans = lts.GetTransitionsFrom(state);
//            List<LabeledTransitionSystem.Transition> toTrans = lts.GetTransitionsTo(state);
//            
//            if(toTrans.size() == 0 && fromTrans.size() == 0)
//                lts.RemoveState(state);
//           
//        }
//        
    }
    
    static ProgramDefinition CreateProgramWithSimpleMessageing1()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor actorA = new Actor("A",2);        
        Actor actorB = new Actor("B",2);
        
        MessageHandler hanlder_a1 = new MessageHandler();        
        MessageHandler hanlder_b1 = new MessageHandler();

        
        actorA.AddMessageHandler("a1",hanlder_a1);
        actorB.AddMessageHandler("b1",hanlder_b1);
        
        hanlder_a1.AddStatement(new DelayStatement(1.0f));
        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage(hanlder_b1)));

        hanlder_b1.AddStatement(new DelayStatement(1.0f));
        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage(hanlder_a1)));
        
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(hanlder_b1)));
               
        definition.AddActor(actorA);
        definition.AddActor(actorB);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
    
    static ProgramDefinition CreateProgramWithSimpleMessageing2()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor actorA = new Actor("A",1);        
        Actor actorB = new Actor("B",1);
        
        MessageHandler hanlder_a1 = new MessageHandler();        
        MessageHandler hanlder_b1 = new MessageHandler();

        
        actorA.AddMessageHandler("a1",hanlder_a1);
        actorB.AddMessageHandler("b1",hanlder_b1);
        
        hanlder_a1.AddStatement(new DelayStatement(1.0f));
        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage(hanlder_b1)));

        hanlder_b1.AddStatement(new DelayStatement(1.0f));
        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage(hanlder_a1)));
        
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(hanlder_b1)));
        mainBlock.AddSendStatement(new SendStatement(actorA, new NormalMessage(hanlder_a1)));
               
        definition.AddActor(actorA);
        definition.AddActor(actorB);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
    
    static ProgramDefinition CreateThermostatPorgram()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor actorThermostat = new Actor("Thermostat",1);        
        
        MessageHandler hanlder_On = new MessageHandler();        
        MessageHandler hanlder_Off = new MessageHandler();

        
        actorThermostat.AddMessageHandler("TurnOn",hanlder_On);
        actorThermostat.AddMessageHandler("TurnOff",hanlder_Off);
        
        hanlder_On.AddStatement(new ContinuousBehaviorStatement
        (new ContinuousBehavior("x <= 100"
                , "x' = -x + 100"
                , "x >= 18"
                , Statement.StatementsFrom(new SendStatement(actorThermostat, new NormalMessage(hanlder_Off))))));

        hanlder_Off.AddStatement(new ContinuousBehaviorStatement
        (new ContinuousBehavior("x>=50"
                , "x' = -x + 50"
                , "x <= 2"
                , Statement.StatementsFrom(new SendStatement(actorThermostat, new NormalMessage(hanlder_On))))));
        
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(actorThermostat, new NormalMessage(hanlder_On)));
               
        definition.AddActor(actorThermostat);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
   
    static ProgramDefinition CreateProgramWithComplexMessageing()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor actorA = new Actor("A",1);        
        Actor actorB = new Actor("B",1);
        
        MessageHandler handler_a1 = new MessageHandler();        
        MessageHandler handler_b1 = new MessageHandler();
        MessageHandler handler_b2 = new MessageHandler();

        
        actorA.AddMessageHandler("a1",handler_a1);
        actorB.AddMessageHandler("b1",handler_b1);
        actorB.AddMessageHandler("b2",handler_b2);
        
        handler_a1.AddStatement(new DelayStatement(1.0f));
        handler_a1.AddStatement(new SendStatement(actorB,new NormalMessage(handler_b1)));
        handler_a1.AddStatement(new SendStatement(actorB,new NormalMessage(handler_b2)));

        handler_b1.AddStatement(new DelayStatement(1.0f));
        handler_b1.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1)));
        
        handler_b2.AddStatement(new DelayStatement(0.5f));
        handler_b2.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1)));
       
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(handler_b1)));
        
                
        definition.AddActor(actorA);
        definition.AddActor(actorB);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }

    private static void RemoveUnreachableStates(LabeledTransitionSystem lts)
    {
        List<GlobalRunTimeState> reachableStates = new LinkedList<>();
        Queue<GlobalRunTimeState> notVisitedStates = new LinkedList<>();
        Queue<GlobalRunTimeState> visitedStates = new LinkedList<>();
        
        notVisitedStates.add(lts.GetInitialState());
        
        while(notVisitedStates.isEmpty() == false)
        {
            GlobalRunTimeState state = notVisitedStates.poll();
            reachableStates.add(state);
            visitedStates.add(state);
            
            for(Transition t : lts.GetTransitionsFrom(state))
                if(visitedStates.contains(t.GetDestination()) == false
                        && notVisitedStates.contains(t.GetDestination()) == false)
                    notVisitedStates.add(t.GetDestination());
        }
        
        for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
            if(reachableStates.contains(state) == false)
                lts.RemoveState(state);
        
    }

    private static void RemoveTauLabels(LabeledTransitionSystem lts)
    {
        while(true)
        {
            boolean change = false;
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
            {
                if(state.equals(lts.GetInitialState()))
                    continue;
                
                
                List<Transition> outTrans = lts.GetTransitionsFrom(state);
                List<Transition> inTrans = lts.GetTransitionsTo(state);
                
                
                boolean allTauLabled = true && outTrans.size() > 0;

                for(Transition outT : outTrans)
                    if(outT.GetLabel() instanceof TauLabel == false)
                    {
                        allTauLabled = false;
                        break;
                    }
                
                if(allTauLabled)
                {
                    for(Transition outT: outTrans)
                        for(Transition inT : inTrans)
                        {
                            lts.AddTransition(inT.GetOrign(), inT.GetLabel(), outT.GetDestination() );
                            change = true;
                        }
                    lts.RemoveState(state);
                    break;

                }
            }
            
            if(change == false)
                break;
        }
    }

}
