/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.Actor;
import HPalang.Core.MainBlock;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;

/**
 *
 * @author Iman Jahandideh
 */
public class BouncingBallModel
{
    public ProgramDefinition Create()
    {
        ProgramDefinition definition = new ProgramDefinition();
        
        Actor ball = new Actor("Ball",1);        
        
        MessageHandler handler_Fall = new MessageHandler();
        
        ball.AddMessageHandler("Fall",handler_Fall);
        
        handler_Fall.AddStatement(new ContinuousBehaviorStatement( 
                new ContinuousBehavior("y>=0", "y'' := -9.8", "y<=0",Statement.StatementsFrom(new SendStatement(ball, new NormalMessage(handler_Fall))))));
      
       
        MainBlock mainBlock = new MainBlock();     
        mainBlock.AddSendStatement(new SendStatement(ball, new NormalMessage(handler_Fall)));
        
                
        definition.AddActor(ball);
        definition.SetMainBlock(mainBlock);
        
        return definition;
    }
}