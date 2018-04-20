/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.RealVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WARNING!!! DO NOT USE THIS CLASS.
 * @author Iman Jahandideh
 */
public class SingleCommunicationRealVariablePool  extends Equalitable<SingleCommunicationRealVariablePool> implements RealVariablePool
{
    private static class ReceiverPacket extends Equalitable<ReceiverPacket>
    {
        private final Actor actor;
        private final Message message;
        public ReceiverPacket(Actor actor, Message message)
        {
            this.actor = actor;
            this.message = message;
        }
        @Override
        protected boolean InternalEquals(ReceiverPacket other)
        {
            return this.actor.equals(other.actor) && this.message.equals(other.message);
        }
        @Override
        protected int InternalHashCode()
        {
            return 1;
        }
    }
    
    private static class ReservedVariables extends Equalitable<ReservedVariables>
    {
        private boolean isAvailable;
        private final List<RealVariable> variables;

        private ReservedVariables(List<RealVariable> variables, boolean isAvailable)
        {
            this.isAvailable = isAvailable;
            this.variables = variables;
        }

        @Override
        protected boolean InternalEquals(ReservedVariables other)
        {
            return variables.equals(other.variables)
                    && isAvailable == other.isAvailable;
        }

        @Override
        protected int InternalHashCode()
        {
            return 1;
        }

        private void SetAvailable(boolean isAvailable)
        {
            this.isAvailable = isAvailable;
        }
    }
    
    private final Map<ReceiverPacket, ReservedVariables> pool = new HashMap<>();
    
    public void Reserve(Actor receiver, Message message, int size, String prefix)
    {
        List<RealVariable> variables = new ArrayList<>(size);
        for(int i = 0 ; i < size; i++)
            variables.add(new RealVariable(prefix+i));
        
        ReceiverPacket packet = new ReceiverPacket(receiver, message);
        ReservedVariables reservedVariables = new ReservedVariables(variables,true);
        pool.put(packet, reservedVariables);
    }
    @Override
    protected boolean InternalEquals(SingleCommunicationRealVariablePool other)
    {
        return this.pool.equals(other.pool);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }

    @Override
    public RealVariable Acquire()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<RealVariable> Acquire(Actor receiver, Message message)
    {
        ReservedVariables r = pool.get(new ReceiverPacket(receiver, message));
        r.SetAvailable(false);
        return r.variables;
    }


    public void Release(Actor receiver, Message message)
    {
        pool.get(new ReceiverPacket(receiver, message)).SetAvailable(true);
    }
    
    public boolean HasAvailableVariable(Actor recevier, Message message)
    {
        if(pool.containsKey(new ReceiverPacket(recevier, message))== false)
        {
            pool.containsKey(new ReceiverPacket(recevier, message));
            System.err.println("No Reserver variable " + recevier.Name()+ ":" + message.toString());
            for (Map.Entry<ReceiverPacket, ReservedVariables> entry : pool.entrySet()) 
            {
                System.out.println("Reserver variable " + entry.getKey().actor.Name()+ ":" + entry.getKey().message.toString());
            }
        }
        return pool.get(new ReceiverPacket(recevier, message)).isAvailable;
    }
    
    
    @Override
    public void Release(RealVariable variable)
    {
        for (Map.Entry<ReceiverPacket, ReservedVariables> entry : pool.entrySet()) 
        {
            if(entry.getValue().variables.contains(variable))
                entry.getValue().isAvailable = true;
        }
        
    }
    

    @Override
    public boolean HasAnyAvailableVariable()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean HasAvailableVariable(int number)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Has(RealVariable variable)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<RealVariable> AllVariables()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<RealVariable> AvailableVariables()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RealVariablePool DeepCopy()
    {
        SingleCommunicationRealVariablePool copy = new SingleCommunicationRealVariablePool();
        
        for (Map.Entry<ReceiverPacket, ReservedVariables> entry : pool.entrySet()) {
            copy.pool.put(
                    entry.getKey(),
                    new ReservedVariables(
                            entry.getValue().variables, 
                            entry.getValue().isAvailable));
        }
            
        return copy;
    }
}
