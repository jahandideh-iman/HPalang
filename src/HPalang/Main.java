/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Convertors.HybridAutomatonToDMEConvertor;
import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.Convertors.LTSToXMLConvertor;
import HPalang.Core.ModelDefinition;
import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.HybridAutomataGeneration.SOSRules.ConversionRule;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.ModeDefinitionToGlobalStateConvertor;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.Transition;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.Labels.GuardedlLabel;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.SOSRules.*;
import HPalang.Parser.Parser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Main {

    
    // TODO: ------------------------- REFACTOR THIS -------------------------------
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        ModelDefinition definition;
        if(args.length ==0)
            definition = BrakeByWireModelSingleWheel.Create();
        else
            definition = new Parser().ParseModel(Read(args[0]));
        
        LTSGenerator tierOneLTSGenerator = CreateTierOneLTSGenrator();
        
        //HybridAutomatonGenerator hybridAutomatonGenerator = new HybridAutomatonGenerator();
        //hybridAutomatonGenerator.AddSOSRule(new ConversionRule());
        
         
        ModeDefinitionToGlobalStateConvertor convertor = new ModeDefinitionToGlobalStateConvertor();
        LabeledTransitionSystem lts =  tierOneLTSGenerator.Generate(convertor.Convert(definition));
        
        FileWriter writer = new FileWriter("output/");
        
        System.out.println("LTS(B) States  : " + lts.GetStates().size());
        System.out.println("LTS(B) Transition : " + lts.GetTransitions().size());
        
        PrioritizeTauActions(lts);
        RemoveUnreachableStates(lts);
        //RemoveTauLabels(lts);
        
//        HybridAutomaton automaton = hybridAutomatonGenerator.Generate(lts);
//        
//        writer.Write("output_LTS.xml", new LTSToXMLConvertor().Convert(lts));
//        writer.Write("output_HA.xml", new HybridAutomatonToDMEConvertor().Convert(automaton));
//        
//        System.out.println("LTS(A) Pruning States : " + lts.GetStates().size());
//        System.out.println("LTS(A) Pruning Transition : " + lts.GetTransitions().size());
//        
//        System.out.println("HA Locations : " + automaton.GetLocations().size());
//        System.out.println("HA Transition : " + automaton.GetTransitions().size());
    }
    
    private static InputStream Read(String filePath) throws FileNotFoundException
    {
        return new FileInputStream(filePath);
    }
    
    private static LTSGenerator CreateTierOneLTSGenrator()
    {
        LTSGenerator genetator = new LTSGenerator();
        
        // Software
        genetator.AddSOSRule(new FIFOMessageTakeRule());
        genetator.AddSOSRule(new MessageTeardownStatementRule());
        genetator.AddSOSRule(new DelayStatementRule());
        genetator.AddSOSRule(new AssignmentStatementRule());
        genetator.AddSOSRule(new IfStatementRule());
        genetator.AddSOSRule(new MessageSendRule());
        genetator.AddSOSRule(new ModeChangeStatementRule());

        // Network 
        genetator.AddSOSRule(new NetwrokCommunicationRule());
        
        // Phyiscal
        genetator.AddSOSRule(new ContinuousBehaviorExpirationRule());
        genetator.AddSOSRule(new EventExpirationRule());
        
        return genetator;
    }
    
    private static void PrioritizeTauActions(LabeledTransitionSystem lts)
    {
        while(true)
        {
            boolean change = false;
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
            {
                List<Transition> outTrans = lts.GetOutTransitionsFor(state);
                boolean hasTauLabled = false;
                for(Transition t : outTrans)
                    if(t.GetLabel() instanceof SoftwareLabel)
                    {
                        hasTauLabled = true;
                        break;
                    }
                
                if(hasTauLabled)
                {
                    for(Transition t : outTrans)
                        if(t.GetLabel() instanceof  SoftwareLabel == false)
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
                List<Transition> outTrans = lts.GetOutTransitionsFor(state);
                List<Transition> inTrans = lts.GetInTransitionFor(state);
                
                if(inTrans.size() == 1 && outTrans.size() == 1
                        && inTrans.get(0).GetLabel() instanceof SoftwareLabel
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
//            List<LabeledTransitionSystem.Transition> trans = lts.GetOutTransitionsFor(state);
//            
//            boolean hasTauLabel = false;
//            for(LabeledTransitionSystem.Transition t : trans)
//            {
//                if(t.GetLabel() instanceof SoftwareLabel)
//                {
//                    hasTauLabel = true;
//                    break;
//                }
//            }
//            if(hasTauLabel)
//            {
//                for(LabeledTransitionSystem.Transition t : trans)
//                {
//                    if(t.GetLabel() instanceof SoftwareLabel == false)
//                         lts.RemoveTranstion(t);
//                }
//            }
//        }
        
//        
//        for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.GetStates()))
//        {
//            List<LabeledTransitionSystem.Transition> fromTrans = lts.GetOutTransitionsFor(state);
//            List<LabeledTransitionSystem.Transition> toTrans = lts.GetInTransitionFor(state);
//            
//            if(toTrans.size() == 0 && fromTrans.size() == 0)
//                lts.RemoveState(state);
//           
//        }
//        
    }
    
    static ModelDefinition CreateProgramWithSimpleMessageing1()
    {
        ModelDefinition definition = new ModelDefinition();
//        
//        SoftwareActor actorA = new SoftwareActor("A",2);        
//        SoftwareActor actorB = new SoftwareActor("B",2);
//        
//        MessageHandler hanlder_a1 = new MessageHandler();        
//        MessageHandler hanlder_b1 = new MessageHandler();
//
//        
//        actorA.AddMessageHandler("a1",hanlder_a1);
//        actorB.AddMessageHandler("b1",hanlder_b1);
//        
//        hanlder_a1.AddStatement(new DelayStatement(1.0f));
//        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage(hanlder_b1)));
//
//        hanlder_b1.AddStatement(new DelayStatement(1.0f));
//        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage(hanlder_a1)));
//        
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(hanlder_b1)));
//               
//        definition.AddActor(actorA);
//        definition.AddActor(actorB);
//        definition.SetMainBlock(mainBlock);
//        
        return definition;
    }
    
    static ModelDefinition CreateProgramWithSimpleMessageing2()
    {
        ModelDefinition definition = new ModelDefinition();
        
//        SoftwareActor actorA = new SoftwareActor("A",1);        
//        SoftwareActor actorB = new SoftwareActor("B",1);
//        
//        MessageHandler hanlder_a1 = new MessageHandler();        
//        MessageHandler hanlder_b1 = new MessageHandler();
//
//        
//        actorA.AddMessageHandler("a1",hanlder_a1);
//        actorB.AddMessageHandler("b1",hanlder_b1);
//        
//        hanlder_a1.AddStatement(new DelayStatement(1.0f));
//        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage(hanlder_b1)));
//
//        hanlder_b1.AddStatement(new DelayStatement(1.0f));
//        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage(hanlder_a1)));
//        
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(hanlder_b1)));
//        mainBlock.AddSendStatement(new SendStatement(actorA, new NormalMessage(hanlder_a1)));
//               
//        definition.AddActor(actorA);
//        definition.AddActor(actorB);
//        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
    
    static ModelDefinition CreateThermostatPorgram()
    {
        ModelDefinition definition = new ModelDefinition();
        
//        SoftwareActor actorThermostat = new SoftwareActor("Thermostat",1);   
//        
//        ContinuousVariable x = new ContinuousVariable("x");
//        
//        MessageHandler hanlder_On = new MessageHandler();        
//        MessageHandler hanlder_Off = new MessageHandler();
//
//        
//        actorThermostat.AddMessageHandler("TurnOn",hanlder_On);
//        actorThermostat.AddMessageHandler("TurnOff",hanlder_Off);
//        
//        hanlder_On.AddStatement(new ContinuousBehaviorStatement
//        (new ContinuousBehavior("x <= 100"
//                , new DifferentialEquation(x, "-x + 100")
//                , "x >= 18"
//                , Statement.StatementsFrom(new SendStatement(actorThermostat, new NormalMessage(hanlder_Off))))));
//
//        hanlder_Off.AddStatement(new ContinuousBehaviorStatement
//        (new ContinuousBehavior("x>=50"
//                ,  new DifferentialEquation(x, "-x + 50")
//                , "x <= 2"
//                , Statement.StatementsFrom(new SendStatement(actorThermostat, new NormalMessage(hanlder_On))))));
//        
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(actorThermostat, new NormalMessage(hanlder_On)));
//               
//        definition.AddActor(actorThermostat);
//        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
   
    static ModelDefinition CreateProgramWithComplexMessageing()
    {
        ModelDefinition definition = new ModelDefinition();
        
//        SoftwareActor actorA = new SoftwareActor("A",1);        
//        SoftwareActor actorB = new SoftwareActor("B",1);
//        
//        MessageHandler handler_a1 = new MessageHandler();        
//        MessageHandler handler_b1 = new MessageHandler();
//        MessageHandler handler_b2 = new MessageHandler();
//
//        
//        actorA.AddMessageHandler("a1",handler_a1);
//        actorB.AddMessageHandler("b1",handler_b1);
//        actorB.AddMessageHandler("b2",handler_b2);
//        
//        handler_a1.AddStatement(new DelayStatement(1.0f));
//        handler_a1.AddStatement(new SendStatement(actorB,new NormalMessage(handler_b1)));
//        handler_a1.AddStatement(new SendStatement(actorB,new NormalMessage(handler_b2)));
//
//        handler_b1.AddStatement(new DelayStatement(1.0f));
//        handler_b1.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1)));
//        
//        handler_b2.AddStatement(new DelayStatement(0.5f));
//        handler_b2.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1)));
//       
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage(handler_b1)));
//        
//                
//        definition.AddActor(actorA);
//        definition.AddActor(actorB);
//        definition.SetMainBlock(mainBlock);
        
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
            
            for(Transition t : lts.GetOutTransitionsFor(state))
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
                
                List<Transition> outTrans = lts.GetOutTransitionsFor(state);
                List<Transition> inTrans = lts.GetInTransitionFor(state);
                
                
                boolean allTauLabled = true && outTrans.size() > 0;

                for(Transition outT : outTrans)
                    if(outT.GetLabel() instanceof SoftwareLabel == false)
                    {
                        allTauLabled = false;
                        break;
                    }
                
                if(allTauLabled)
                {
                    for(Transition outT: outTrans)
                        for(Transition inT : inTrans)
                        {
                            Label label = CreateLabelFor(inT.GetLabel(), outT.GetLabel());
                            lts.AddTransition(inT.GetOrign(), label, outT.GetDestination() );
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
    
    static public Label CreateLabelFor(Label firstLabel, Label secondLabel)
    {
        Map<Variable, Reset> resets = new HashMap<>();
        
        for(Reset re : (Set<Reset>)firstLabel.GetResets())
                resets.put(re.Variable(), re);
        
        for(Reset re : (Set<Reset>)secondLabel.GetResets())
                resets.put(re.Variable(), re);

        if(firstLabel instanceof SoftwareLabel)
            return new SoftwareLabel(new LinkedHashSet<>(resets.values()));
        else
          return new GuardedlLabel(((GuardedlLabel) firstLabel).GetGuard() ,new LinkedHashSet<>(resets.values()));  
    }
}

  
