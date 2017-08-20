/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ModelDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;

/**
 *
 * @author Iman Jahandideh
 */
public class BouncingBallModel
{
    public ModelDefinition Create()
    {
        ModelDefinition definition = new ModelDefinition();
        
//        SoftwareActor ball = new SoftwareActor("Ball",1);
//        ContinuousVariable ball_y = new ContinuousVariable("y");
//        
//        //ball.AddContinuousVariable(ball_y, 0);
//        
//        MessageHandler handler_Fall = new MessageHandler();
//        
//        ball.AddMessageHandler("Fall",handler_Fall);
//        
//        handler_Fall.AddStatement(new ContinuousBehaviorStatement( 
//                new ContinuousBehavior("y>=0",new DifferentialEquation(ball_y, "9.8"), "y<=0",Statement.StatementsFrom(new SendStatement(ball, new NormalMessage(handler_Fall))))));
//      
//       
//        MainBlock mainBlock = new MainBlock();     
//        mainBlock.AddSendStatement(new SendStatement(ball, new NormalMessage(handler_Fall)));
//        
//                
//        definition.AddActor(ball);
//        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
}