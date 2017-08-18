/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.DifferentialEquation;
import HPalang.Core.Mode;
import HPalang.Core.Statement;
import static HPalang.Core.Statement.StatementsFrom;
import Mocks.EmptyStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeBuilder
{
    private String inv = "inv";
    private String name = "mode";
    
    public Mode Build()
    {
        return new Mode(name,inv, Mode.EquationsFrom(DifferentialEquation.Empty()), "guard", StatementsFrom(new EmptyStatement()));
    }

    public ModeBuilder WithName(String name)
    {
        this.name = name;
        return this;
    }
    
    public ModeBuilder WithInvarient(String inv)
    {
        this.inv = inv;
        return this;
    }
}
