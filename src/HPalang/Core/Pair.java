/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class Pair<A, B> extends Equalitable<Pair<A,B>>
{
    private final A a;
    private final B b;
    
    public Pair(A a, B b)
    {
        this.a = a;
        this.b = b;
    }
    
    public A A()
    {
        return a;
    }
    
    public B B()
    {
        return b;
    }
    
    @Override
    protected boolean InternalEquals(Pair<A, B> other)
    {
        return this.a.equals(other.a) &&
                this.b.equals(other.b);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
