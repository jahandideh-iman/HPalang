/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.MainBlock;
import HPalang.Core.ProgramDefinition;

/**
 *
 * @author Iman Jahandideh
 */
public class ProgramDefinitionBuilder
{
    public static ProgramDefinition EmptyProgram()
    {
        ProgramDefinition emptyProgram = new ProgramDefinition();
        emptyProgram.SetMainBlock(new MainBlock());
        
        return emptyProgram;
    }
}
