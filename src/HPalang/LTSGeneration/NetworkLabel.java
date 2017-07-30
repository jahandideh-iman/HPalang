/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

/**
 *
 * @author Iman Jahandideh
 */
public class NetworkLabel extends Label<NetworkLabel>
{

    @Override
    protected boolean InternalEquals(NetworkLabel other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 13;
    }
   
}
