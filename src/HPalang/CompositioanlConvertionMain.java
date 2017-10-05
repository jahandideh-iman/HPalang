/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.ModelDefinition;
import HPalang.SpaceEx.Convertor.HPalangToCompositionalSXConvertor;
import HPalang.SpaceEx.Convertor.SpaceExToXMLConvertor;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class CompositioanlConvertionMain
{
    public static void main(String[] args) 
    {
        ModelDefinition definition = HRToHAExample1.Create();
        
        HPalangToCompositionalSXConvertor spaceExConvertor = new HPalangToCompositionalSXConvertor();
        SpaceExToXMLConvertor xmlConvertor = new SpaceExToXMLConvertor();
        FileWriter writer = new FileWriter();
        
        spaceExConvertor.Convert(definition);
        SpaceExModel spaceExModel = spaceExConvertor.GetConvertedModel();
 
        
        
        writer.Write("output_SpaceEx.xml", xmlConvertor.Convert(spaceExModel));
    }
}
