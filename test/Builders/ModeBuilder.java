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
    
    public Mode Build()
    {
        return new Mode(inv, DifferentialEquation.Empty(), "guard", StatementsFrom(new EmptyStatement()));
    }

    public ModeBuilder WithInvarient(String inv)
    {
        this.inv = inv;
        return this;
    }
}
