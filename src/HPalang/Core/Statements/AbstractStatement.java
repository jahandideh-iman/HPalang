/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Equalitable;
import HPalang.Core.Statement;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class AbstractStatement<T> extends Equalitable<T> implements Statement
{   
    @Override
    public boolean Is(Class<? extends Statement> cc)
    {
        return this.getClass().equals(cc);
    }
}
