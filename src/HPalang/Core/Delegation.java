/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class Delegation extends Equalitable<Delegation>
{
    public Delegation(Actor actor, MessageHandler messageHandler)
    {
    }

    @Override
    protected boolean InternalEquals(Delegation other)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int InternalHashCode()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
