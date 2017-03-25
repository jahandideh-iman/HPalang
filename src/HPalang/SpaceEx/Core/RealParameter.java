/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import HPalang.Core.ContinuousVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class RealParameter extends Parameter
{

    public RealParameter(String name, boolean isLocal)
    {
        super(name, isLocal);
    }
    
    @Override
    public void Accept(Visitor visitor)
    {
        visitor.Visit(this);
    }
    
    public static RealParameter From(ContinuousVariable var)
    {
        return new RealParameter(var.Name(), false);
    }
}
