/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ModelDefinition;

/**
 *
 * @author Iman Jahandideh
 */
public class SimpleModel
{
     public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
//        
//        SoftwareActor actorA = new SoftwareActor("A",1);        
//        SoftwareActor actorB = new SoftwareActor("B",1);
//        
//        ContinuousVariable A_timer = new ContinuousVariable("timer");
////        actorA.AddContinuousVariable(A_timer, 0);
//        
//        MessageHandler handler_a1 = new MessageHandler();  
//        MessageHandler handler_a2 = new MessageHandler();        
//        
//        MessageHandler handler_b1 = new MessageHandler();
//
//        
//        actorA.AddMessageHandler("a1",handler_a1);   
//        //actorA.AddMessageHandler("a2",handler_a2);
//
//        actorB.AddMessageHandler("b1",handler_b1);
//        
//        handler_a1.AddStatement(new DelayStatement(0.5f));
//        handler_a1.AddStatement(new ContinuousAssignmentStatement(A_timer, new ConstantContinuousExpression(0.0f)));
//        handler_a1.AddStatement(new ContinuousBehaviorStatement(
//                        new ContinuousBehavior(
//                                "timer<=2", 
//                                new DifferentialEquation(A_timer, "1"), 
//                                "timer == 2", 
//                                Statement.StatementsFrom(new SendStatement(actorB,new NormalMessage(handler_b1))
//                                        ,new ContinuousAssignmentStatement(A_timer, new ConstantContinuousExpression(0.0f))))));
//        //handler_a1.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1)));
//        handler_a1.AddStatement(new ContinuousAssignmentStatement(A_timer, new ConstantContinuousExpression(0.0f)));
//        handler_a1.AddStatement(new ContinuousAssignmentStatement(A_timer, new ConstantContinuousExpression(0.0f)));
//
//        handler_b1.AddStatement(new DelayStatement(2f));
//        handler_b1.AddStatement(new SendStatement(actorA,new NormalMessage(handler_a1))); 
//
//        
//     
//        MainBlock mainBlock = new MainBlock();     
//                
//        definition.AddActor(actorA);
//        definition.AddActor(actorB);
//        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
}
