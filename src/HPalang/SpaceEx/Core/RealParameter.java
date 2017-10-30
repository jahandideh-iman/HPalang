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

    public enum Dynamic {Const,Any};
    
    private final Dynamic dynamic;
    
    public RealParameter(String name, boolean isLocal)
    {
        super(name, isLocal);
        this.dynamic = Dynamic.Any;
    }
    
    public RealParameter(String name, boolean isLocal, Dynamic dynamic)
    {
        super(name, isLocal);
        this.dynamic = dynamic;
    }
    
    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }
    
    public static RealParameter From(ContinuousVariable var)
    {
        return new RealParameter(var.Name(), false);
    }

    public Dynamic GetDynamic()
    {
        return dynamic;
    }
}
