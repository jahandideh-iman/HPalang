/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.ContinuousBehavior;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousState implements Cloneable
{
    private List<ContinuousBehavior> continuousBehaviors = new ArrayList<>();

    public void AddBehavior(ContinuousBehavior behavior)
    {
        continuousBehaviors.add(behavior);
    }

    public List<ContinuousBehavior> GetBehaviors()
    {
        return continuousBehaviors;
    }

    public void RemoveBehavior(ContinuousBehavior behavior)
    {
        continuousBehaviors.remove(behavior);
    }

    public ContinuousState Clone()
    {
        try {
            ContinuousState copy = (ContinuousState) clone();
            
            copy.continuousBehaviors = copy.continuousBehaviors.getClass().newInstance();
            copy.continuousBehaviors.addAll(this.continuousBehaviors);
            
            return copy;
        } catch (CloneNotSupportedException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ContinuousState.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!ContinuousState.class.isAssignableFrom(other.getClass()))
            return false;
            
        ContinuousState otherState = (ContinuousState) other;
        
        return Arrays.equals(this.continuousBehaviors.toArray(), otherState.continuousBehaviors.toArray());
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.continuousBehaviors);
        return hash;
    }
}

