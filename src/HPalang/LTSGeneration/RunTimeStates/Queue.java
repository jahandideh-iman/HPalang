/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Iman Jahandideh
 */

// TODO: Rename Push and Enqueu
public class Queue<T> extends EqualitableAndClonable<Queue<T>> implements Iterable<T>
{
    private final Deque<T> items = new LinkedList<>();
    
    public T Head()
    {
        return items.peek();
    }

    public void Dequeue()
    {
        items.poll();
    }

    public void Enqueue(T item)
    {
        items.add(item);
    }
    
    public void Push(T item)
    {
        items.addFirst(item);
    }
    
    public void Push(java.util.Queue<T> items)
    {
        for(T item : items)
            Push(item);
    }
    
    public void Enqueue(java.util.Queue<T> items)
    {
        for(T item : items)
            Enqueue(item);
    }
    
    public void Enqueue(Queue<T> items)
    {
        for(T item : items)
            Enqueue(item);
    }
    
    public void Clear()
    {
        items.clear();
    }
    
    public void Remove(T item)
    {
        items.remove(item);
    }

    public boolean IsEmpty()
    {
        return items.isEmpty();
    }

    @Override
    public Iterator<T> iterator()
    {
        return items.iterator();
    }
    
    public int Size()
    {
        return items.size();
    }

    @Override
    protected boolean InternalEquals(Queue<T> other)
    {
        return other.items.equals(this.items);
    }

    @Override
    protected int InternalHashCode()
    {
        return items.hashCode();
    }

    @Override
    public Queue<T> DeepCopy()
    {
        Queue<T> copy = new Queue<>();
        
        copy.Enqueue(this);
        
        return copy;
    }
}
