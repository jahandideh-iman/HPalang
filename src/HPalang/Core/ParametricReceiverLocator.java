/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ParametricReceiverLocator implements SendStatement.ReceiverLocator
{

    public ParametricReceiverLocator(InstanceParameter instanceParameter)
    {
        
    }
    
    @Override
    public SoftwareActor GetActor()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
