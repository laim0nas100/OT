/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.chain;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.containers.Value;

/**
 *
 * @author Laimonas Beniušis
 */
public class InvocationChainBuilder {

    private List<Invocation> invocations = new ArrayList<>();
    public InvocationChainBuilder(){
        
    }
    
    public InvocationChainBuilder with(Invocation inv){
        this.invocations.add(inv);
        return this;
    }
    
    public InvocationChain toChain(){
        return new SimpleInvocationChain(invocations.toArray(s -> new Invocation[s]));
    }
    
    public <I> I toInstance(Class<I> mainInter){
        return (I) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                ArrayOp.asArray(mainInter),
                new InvocationChainProxy(null, this.toChain())
        );
    }
    
    public <T extends I,I> I toInstance(Class<I> mainInter, T impl){
        return (I) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                ArrayOp.asArray(mainInter),
                new InvocationChainProxy(impl, this.toChain())
        );
    }
    
    public static InvocationChain of(Invocation... invocations) {
        return new SimpleInvocationChain(invocations);
    }

    public static class SimpleInvocationChain implements InvocationChain {

        Iterator<Invocation> iterator;
        boolean reinit;
        Value resultStore = new Value<>();

        Invocation[] invocations;
        
        public SimpleInvocationChain(Invocation... inv){
            invocations = inv;
        }

        @Override
        public Object invoke(Object callee, Method method, Object[] args) throws Throwable {
            if(iterator == null){
                return this.invokeNew(callee, method, args);
            }
            if (iterator.hasNext()) {
                    Object result = iterator.next().invoke(callee, method, args, this);
                    if (resultStore.isEmpty()) {
                        resultStore.set(result);
                    }
                }
                return resultStore.get();
        }

        private Object invokeNew(Object callee, Method method, Object[] args) throws Throwable {
            //reset
            
            iterator = Stream.of(invocations).iterator();
            resultStore.set(null);
            Throwable ex = null;
            Object res = null;
            try{
                res = this.invoke(callee, method, args);
            }catch(Throwable th){
                ex = th;
            }
            
            iterator = null;
            if(ex != null){
                throw ex;
            }
            return res;
        }

    }
}
