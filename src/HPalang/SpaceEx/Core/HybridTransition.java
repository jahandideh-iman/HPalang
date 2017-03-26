/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridTransition extends Transition<Location, HybridLabel> implements Visitable
{
    public HybridTransition(Location origin, HybridLabel label, Location destination)
    {
        super(origin, label, destination);
    }

    @Override
    public void Accept(Visitor visitor)
    {
        visitor.Visit(this);
    }
    
    static public HybridTransition CreateEmpty(Location origin, Location destination)
    {
        return new HybridTransition(origin, new HybridLabel(), destination);
    }
}
