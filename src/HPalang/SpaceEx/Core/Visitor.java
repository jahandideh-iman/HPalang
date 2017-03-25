/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

/**
 *
 * @author Iman Jahandideh
 */
public interface Visitor
{
    public void Visit(SpaceExModel model);
    public void Visit(NetworkComponent component);
    public void Visit(BaseComponent component);
    public void Visit(ComponentInstance instance);
    public void Visit(RealParameter realParam);
    public void Visit(LabelParameter labelParam);
    public void Visit(Location location);
    public void Visit(HybridTransition transition);
}
