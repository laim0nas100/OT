/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 * @author Laimonas Beniušis
 */
public class ProxyDecorator {
    
    private InvocationHandler handler;
    public ProxyDecorator(InvocationHandler ih){
        handler = ih;
    }
    
    public static void construct(Class cls){
        Proxy.getProxyClass(cls.getClassLoader(), cls);
    }
}
