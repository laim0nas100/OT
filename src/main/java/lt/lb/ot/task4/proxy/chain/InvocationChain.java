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
public interface InvocationChain extends InvocationHandler{
    public static InvocationChain empty(){
        return new InvocationChain() {
            @Override
            public Object invoke(Object callee, Method method, Object[] args) throws Throwable {
                return null;
            }
        };
    };
    
    @Override
    public Object invoke(Object callee, Method method, Object[] args) throws Throwable;
}
