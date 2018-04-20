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
public class HybridTransition extends  HPalang.SpaceEx.Core.Transition<Location, HybridLabel> implements Visitable
{
    private final boolean isASAP;
    public HybridTransition(Location origin, HybridLabel label, Location destination, boolean isASAP)
    {
        super(origin, label, destination);
        this.isASAP = isASAP;
    }

    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }
    
    static public HybridTransition CreateEmpty(Location origin, Location destination)
    {
        return new HybridTransition(origin, new HybridLabel(), destination, false);
    }

    public boolean IsASAP()
    {
        return isASAP;
    }
}
