/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Convertors.HybridAutomatonToSXConvertor;
import HPalang.Convertors.LTSToAUTConvertor;
import HPalang.Convertors.LTSToFSMConvertor;
import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
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
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
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
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        ModelDefinition definition;
        if(args.length ==0)
            definition = BrakeByWireModel.Create();
        else
            definition = new Parser().ParseModel(Read(args[0]));
        
        LTSGenerator tierOneLTSGenerator = CreateTierOneLTSGenrator();
        
        HybridAutomatonGenerator hybridAutomatonGenerator = new HybridAutomatonGenerator();
        hybridAutomatonGenerator.AddSOSRule(new ConversionRule());
        
         
        ModeDefinitionToGlobalStateConvertor convertor = new ModeDefinitionToGlobalStateConvertor();
        LabeledTransitionSystem lts =  tierOneLTSGenerator.Generate(convertor.Convert(definition));
           
        FileWriter writer = new FileWriter("output/");
        
        OutputLTS("FineLTS",lts, writer);
        OutputLTS("ReducedLTS",new LTSReducer().Reduce(lts), writer);
        
        
        
        //PrioritizeTauActions(lts);
        //RemoveUnreachableStates(lts);
        //RemoveTauLabels(lts);
        
        //LabeledTransitionSystem reduceLTS =  new LTSReducer().Reduce(lts);
        //OutputLTS("ReducedLTS", reduceLTS, writer);
        
        //HybridAutomaton automaton = hybridAutomatonGenerator.Generate(lts, definition);
//        
//        writer.Write("output_LTS.xml", new LTSToXMLConvertor().Convert(lts));
        //writer.Write("output_HA.xml", new HybridAutomatonToSXConvertor().Convert(automaton));
//        
//        System.out.println("LTS(A) Pruning States : " + lts.States().size());
//        System.out.println("LTS(A) Pruning Transition : " + lts.Transitions().size());
//        
//        System.out.println("HA Locations : " + automaton.GetLocations().size());
//        System.out.println("HA Transition : " + automaton.Transitions().size());
    }

    private static void OutputLTS(String prefix, LabeledTransitionSystem lts, FileWriter writer)
    {
        System.out.println(prefix + " States  : " + lts.States().size());
        System.out.println(prefix+ " Transition : " + lts.Transitions().size());
        
        writer.Write(prefix +"_aut.aut", new LTSToAUTConvertor().Convert(lts));
        writer.Write(prefix +"_fsm.fsm", new LTSToFSMConvertor().Convert(lts));
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
        genetator.AddSOSRule(new PhysicalActorFIFOMessageTake());
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
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.States()))
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
            for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.States()))
            {
                List<Transition> outTrans = lts.GetOutTransitionsFor(state);
                List<Transition> inTrans = lts.GetInTransitionsFor(state);
                
                if(inTrans.size() == 1 && outTrans.size() == 1
                        && inTrans.get(0).GetLabel() instanceof SoftwareLabel
                        && state.equals(lts.InitialState()) == false)
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
//        for(GlobalRunTimeState state : lts.States())
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
//        for(GlobalRunTimeState state : new ArrayList<GlobalRunTimeState>(lts.States()))
//        {
//            List<LabeledTransitionSystem.Transition> fromTrans = lts.GetOutTransitionsFor(state);
//            List<LabeledTransitionSystem.Transition> toTrans = lts.GetInTransitionsFor(state);
//            
//            if(toTrans.size() == 0 && fromTrans.size() == 0)
//                lts.RemoveState(state);
//           
//        }
//        
    }


    private static void RemoveUnreachableStates(LabeledTransitionSystem lts)
    {
        List<GlobalRunTimeState> reachableStates = new LinkedList<>();
        Queue<GlobalRunTimeState> notVisitedStates = new LinkedList<>();
        Queue<GlobalRunTimeState> visitedStates = new LinkedList<>();
        
        notVisitedStates.add(lts.InitialState());
        
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
        
        for(GlobalRunTimeState state : new ArrayList<>(lts.States()))
            if(reachableStates.contains(state) == false)
                lts.RemoveState(state);
        
    }

    private static void RemoveTauLabels(LabeledTransitionSystem lts)
    {
        while(true)
        {
            boolean change = false;
            for(GlobalRunTimeState state : new ArrayList<>(lts.States()))
            {
                if(state.equals(lts.InitialState()))
                    continue;
                
                List<Transition> outTrans = lts.GetOutTransitionsFor(state);
                List<Transition> inTrans = lts.GetInTransitionsFor(state);
                
                
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
        
        Guard newGuard;
        
        for(Reset re : (Set<Reset>)firstLabel.Resets())
                resets.put(re.Variable(), re);
        
        for(Reset re : (Set<Reset>)secondLabel.Resets())
                resets.put(re.Variable(), re);

   
        newGuard = new Guard(new BinaryExpression(firstLabel.Guard().Expression(), new LogicalAndOperator(), secondLabel.Guard().Expression()));
        if(firstLabel instanceof SoftwareLabel)
            return new SoftwareLabel(newGuard ,new LinkedHashSet<>(resets.values()));
        else
          return new ContinuousLabel(newGuard ,new LinkedHashSet<>(resets.values()));  
    }
}

  
