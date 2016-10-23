/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.ContinuousBehavior;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousState
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
}

