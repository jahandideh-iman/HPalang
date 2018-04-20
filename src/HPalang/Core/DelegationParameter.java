/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class DelegationParameter extends Equalitable<DelegationParameter>
{
    private final String name;
    
    private final List<Variable.Type> paramterTypes = new LinkedList<>();
    
    private MessageParameters cachedParameters = null;
    
    public DelegationParameter(String name)
    {
        this(name, Collections.EMPTY_LIST);
    }
        
    public DelegationParameter(String name, Collection<Variable.Type> paramterTypes)
    {
        this.name = name;
        this.paramterTypes.addAll(paramterTypes);
    }
    
    public String Name()
    {
        return name;
    }
    
    @Override
    protected boolean InternalEquals(DelegationParameter other)
    {
        return name.equals(other.name) &&
                paramterTypes.equals(other.paramterTypes);
    }

    @Override
    protected int InternalHashCode()
    {
        return name.hashCode();
    }

    public MessageParameters Parameters()
    {
        
        // TODO: Refactor this crap.
        if(cachedParameters == null)
        {
            cachedParameters = new MessageParameters();
            for(int i =0; i< paramterTypes.size(); i++)
            {
                Variable variable = null;
                
                switch(paramterTypes.get(i))
                {
                    case floatingPoint:
                        variable = new FloatVariable(Integer.toString(i));
                        break;
                    case integer:
                        variable = new IntegerVariable(Integer.toString(i));
                        break;
                    case real:
                        variable = new RealVariable(Integer.toString(i));
                        break;
                            
                }
                cachedParameters.Add(new VariableParameter(variable));

            }
        }
        
        return cachedParameters;
    }

    public static Collection<Variable.Type> TypesFrom(Variable.Type ... types)
    {
        return Arrays.asList(types);
    }
        
}
