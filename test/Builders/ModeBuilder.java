/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import static HPalang.Core.CreationUtilities.CreateTrivialFlaseGuard;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.Mode;
import HPalang.Core.Statement;
import static HPalang.Core.Statement.StatementsFrom;
import static TestUtilities.CoreUtility.*;
import Mocks.EmptyStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeBuilder
{
    private Invarient inv = new Invarient(new TrueConst());
    private String name = "mode";
    
    public Mode Build()
    {
        return new Mode(name,inv, Mode.EquationsFrom(DifferentialEquation.Empty()), CreateTrivialFlaseGuard(), StatementsFrom(new EmptyStatement()));
    }

    public ModeBuilder WithName(String name)
    {
        this.name = name;
        return this;
    }
    
    public ModeBuilder WithInvarient(Invarient inv)
    {
        this.inv = inv;
        return this;
    }
}
