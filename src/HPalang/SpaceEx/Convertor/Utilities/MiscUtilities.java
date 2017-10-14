/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.Utilities;

/**
 *
 * @author Iman Jahandideh
 */
public class MiscUtilities
{
    public static String Prefix(String prefix, String value)
    {
        return String.format("%s_%s", prefix, value);
    }
}
