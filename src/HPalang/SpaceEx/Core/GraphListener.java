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
public interface GraphListener<NodeT extends Node>
{
    public void OnNodeAdded(NodeT node);
}
