/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.Core.Statement;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public class NotImplementedLocation extends StatementLocation
{

    public NotImplementedLocation(Statement statement, String name)
    {
        super(null);
        loc = new Location(name + "_NotImplementated");
    }

    @Override
    public void ProcessInLabel(HybridLabel label)
    {

    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {

    }

}
