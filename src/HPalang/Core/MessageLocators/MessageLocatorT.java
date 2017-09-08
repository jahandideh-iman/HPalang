/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.MessageLocators;

import HPalang.Core.Equalitable;
import HPalang.Core.MessageLocator;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class MessageLocatorT<T extends MessageLocator> extends Equalitable<T> implements MessageLocator
{
    
}
