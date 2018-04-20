/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Utilities;

/**
 *
 * @author Iman Jahandideh
 */
public class StopWatch
{
    private long startTime;
    public void Start()
    {
        startTime = System.nanoTime();
    }
    
    public void Reset()
    {
        this.Start();
    }
    
    public long ElapsedTime()
    {
        return System.nanoTime() - startTime;
    }
    
    public float ElaspedTimeSeconds()
    {
        return ElapsedTime()/1000000000f;
    }
}
