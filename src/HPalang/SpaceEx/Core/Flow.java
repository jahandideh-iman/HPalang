/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import HPalang.Core.DefferentialEquation;

/**
 *
 * @author Iman Jahandideh
 */
public class Flow
{
    private String flow;
    public Flow(String flow)
    {
        this.flow = flow;
    }
    
    public Flow(DefferentialEquation equation)
    {
        this.flow = equation.GetVariable().Name() + "' == " + equation.GetEquation();
    }
    
    @Override
    public String toString()
    {
        return flow;
    }
}
