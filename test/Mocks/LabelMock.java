/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.Label;

/**
 *
 * @author Iman Jahandideh
 */
public class LabelMock extends Label<LabelMock>
{
    private final String id;
    
    public LabelMock(String id)
    {
        this.id = id;
    }
    
    @Override
    protected boolean InternalEquals(LabelMock other)
    {
        return this.id.equals(other.id);
    }
    
    @Override
    protected int InternalHashCode()
    {
        return this.id.hashCode();
    }
    
}
