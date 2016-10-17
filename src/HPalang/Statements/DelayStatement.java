/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatement extends Statement
{
    private float delay;
    
    public DelayStatement(float delay)
    {
        this.delay = delay;
    }
}
