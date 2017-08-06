/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousVariablePool extends Equalitable<ContinuousVariablePool>
{
    private final Set<ContinuousVariable> variables = new HashSet<>();
    
    public ContinuousVariablePool(int size)
    {
        for(int i = 0; i< size; i++)
            variables.add(new ContinuousVariable("timer_" + i ));
    }
    
    public ContinuousVariablePool(ContinuousVariablePool other)
    {
        variables.addAll(other.variables);
    }
    
    public ContinuousVariable Acquire()
    {
        if(variables.isEmpty())
            return null;
        else
        {
            ContinuousVariable var = variables.iterator().next();
            variables.remove(var);
            return var;
        }
    }
        
    public void Release(ContinuousVariable variable)
    {
        variables.add(variable);
    }
    
    public boolean Has(ContinuousVariable variable)
    {
        return variables.contains(variable);
    }
    public int Size()
    {
        return variables.size();
    }
    
    @Override
    protected boolean InternalEquals(ContinuousVariablePool other)
    {
        return variables.equals(other.variables);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
   
}
