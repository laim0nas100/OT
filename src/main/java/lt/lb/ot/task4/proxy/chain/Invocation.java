/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.chain;

import java.lang.reflect.Method;

/**
 *
 * @author Laimonas Beniušis
 */
public interface Invocation {
    Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable;
}
