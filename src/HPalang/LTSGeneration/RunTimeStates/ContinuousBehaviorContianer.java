/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorContianer extends EqualitableAndClonable<ContinuousBehaviorContianer> implements Iterable<ContinuousBehavior>
{
    private List<ContinuousBehavior> continuousBehaviors = new ArrayList<>();

    public void Add(ContinuousBehavior behavior)
    {
        continuousBehaviors.add(behavior);
    }

    public void Remove(ContinuousBehavior behavior)
    {
        continuousBehaviors.remove(behavior);
    }

    @Override
    public ContinuousBehaviorContianer DeepCopy()
    {
        try {
            ContinuousBehaviorContianer copy = (ContinuousBehaviorContianer) clone();
            
            copy.continuousBehaviors = copy.continuousBehaviors.getClass().newInstance();
            copy.continuousBehaviors.addAll(this.continuousBehaviors);
            
            return copy;
        } catch (CloneNotSupportedException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ContinuousBehaviorContianer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!ContinuousBehaviorContianer.class.isAssignableFrom(other.getClass()))
            return false;
            
        ContinuousBehaviorContianer otherState = (ContinuousBehaviorContianer) other;
        
        return Arrays.equals(this.continuousBehaviors.toArray(), otherState.continuousBehaviors.toArray());
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.continuousBehaviors);
        return hash;
    }

    @Override
    protected boolean InternalEquals(ContinuousBehaviorContianer other)
    {
        return this.continuousBehaviors.equals(other.continuousBehaviors);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.continuousBehaviors);
        return hash;
    }
    
    public int Size()
    {
        return continuousBehaviors.size();
    }

    @Override
    public Iterator<ContinuousBehavior> iterator()
    {
        return continuousBehaviors.iterator();
    }
}

