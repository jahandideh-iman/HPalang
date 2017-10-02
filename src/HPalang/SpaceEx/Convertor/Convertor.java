/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Convertor
{
    protected final SpaceExModel model;
    
    public Convertor(SpaceExModel model)
    {
        this.model = model;
    }
    
    public abstract void Convert();
}
