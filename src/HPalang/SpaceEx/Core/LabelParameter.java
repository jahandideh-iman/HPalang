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
public class LabelParameter extends Parameter
{

    public LabelParameter(String name, boolean isLocal)
    {
        super(name, isLocal);
    }
    
    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }
}
