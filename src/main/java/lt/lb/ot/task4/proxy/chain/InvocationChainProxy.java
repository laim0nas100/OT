/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.chain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author Laimonas Beniušis
 */
public class InvocationChainProxy implements InvocationHandler{
    
    private Object proxied;
    InvocationChain chain;

    public InvocationChainProxy(Object proxied, InvocationChain chain) {
        this.proxied = proxied;
        this.chain = chain;
    }
    
    

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return chain.invoke(proxied, method, args);
    }
    
}
