/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;


import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class BaseComponent extends Component implements GraphListener<Location>
{
    private final SimpleGraph<Location, HybridLabel, HybridTransition> automaton = new SimpleGraph<>();
    private int locCounter = 0;

    public BaseComponent(String id)
    {
        super(id);
        automaton.AddListener(this); 
    }
    
    public void AddLocation(Location loc)
    {
        automaton.AddNode(loc);
    }
    
    @Override
    public void OnNodeAdded(Location loc)
    {
        loc.SetId(String.valueOf(locCounter));
        locCounter++;
    }
    
    public void AddTransition(Location origin, HybridLabel label, Location destination)
    {
        automaton.AddTransition(new HybridTransition(origin, label, destination));
    }
    
    public void AddTransition(HybridTransition trans)
    {
        automaton.AddTransition(trans);
    }
    
    
    public Collection<Location> GetLocations()
    {
        return automaton.GetNodes();
    }
    
    public Collection<HybridTransition> GetTransitions()
    {
        return automaton.GetTransitions();
    }

    @Override
    public void Accept(Visitor visitor)
    {
        visitor.Visit(this);
    }

}
