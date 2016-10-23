/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.NormalMessage;
import HPalang.Statements.DelayStatement;
import HPalang.Statements.SendStatement;

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
        HybridAutomatonGenerator hagenerator = new HybridAutomatonGenerator();
        
        ProgramDefinition definition = CreateProgramDefinition();
        
        ltsGenerator.Generate(definition);
        
        // Program Definition
        // 
    }
    
    static ProgramDefinition CreateProgramDefinition()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor actorA = new Actor("A",2);        
        Actor actorB = new Actor("B",2);
        
        MessageHandler hanlder_a1 = new MessageHandler();        
        MessageHandler hanlder_b1 = new MessageHandler();
        MessageHandler hanlder_b2 = new MessageHandler();

        
        actorA.AddMessageHandler("a1",hanlder_a1);
        actorB.AddMessageHandler("b2",hanlder_b2);
        actorB.AddMessageHandler("b1",hanlder_b1);
        
//        hanlder_a1.AddStatement(new DelayStatement(1.0f));
//        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage(actorA.GetMessageHandler("a1"))));
//        hanlder_a1.AddStatement(new SendStatement(actorB,new NormalMessage("b1")));
//
//        hanlder_b1.AddStatement(new DelayStatement(1.0f));
//        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage("b1")));
//        
//        hanlder_b1.AddStatement(new DelayStatement(0.5f));
//        hanlder_b1.AddStatement(new SendStatement(actorA,new NormalMessage("b1")));
//       
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(actorB, new NormalMessage("b1")));
//        
//                
//        definition.AddActor(actorA);
//        definition.AddActor(actorB);
//        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
    
}
