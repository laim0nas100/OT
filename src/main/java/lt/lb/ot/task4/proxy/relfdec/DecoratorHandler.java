/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author Laimonas Beniušis
 */
public abstract class DecoratorHandler<T> implements InvocationHandler{
    
    protected T comp;
    public DecoratorHandler(T comp){
        this.comp = comp;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokeComp(method, args);
    }
    
    protected Object invokeComp(Method method, Object[] args) throws Throwable{
        return method.invoke(comp, args);
    }
}
