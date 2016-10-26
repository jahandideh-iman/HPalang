/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.Message;
import HPalang.Statements.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyMessage implements Message
{
    @Override
    public Queue<Statement> GetMessageBody()
    {
        return new LinkedList<>();
    }
}
