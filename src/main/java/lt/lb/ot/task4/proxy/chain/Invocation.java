/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.chain;

import java.lang.reflect.Method;
import lt.lb.ot.task4.proxy.relfdec.MethodDecorator;

/**
 *
 * @author Laimonas Beniušis
 */
public interface Invocation {
    Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable;
    
    
    public static Invocation of(MethodDecorator dec){
        return new Invocation(){
            @Override
            public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable {
                
                Object[] decoratedArgs = dec.paramDecorator.apply(args);
                //Decorator  method
                Object result = dec.function.apply(decoratedArgs);
                if(result == null){
                    return dec.resultDecorator.apply(chain.invoke(callee, method, decoratedArgs));
                }else{
                    return dec.resultDecorator.apply(result);
                }
            }
        };
    }
}
