/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.chain;

import java.lang.reflect.Method;
import java.util.function.Consumer;
import lt.lb.commons.Log;
import lt.lb.commons.Timer;

/**
 *
 * @author Laimonas Beniušis
 */
public class InvocationChainHandlers {

    public static Invocation loggingInvocation(Consumer<String> cons) {
        return new Invocation() {
            @Override
            public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable {
                cons.accept(method.getName() + "() execution logged!");
                chain.invoke(callee, method, args);
                return null;
            }
        };
    }

    public static Invocation timerInvocation() {
        return new Invocation() {
            @Override
            public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable {
                Timer t = new Timer();
                chain.invoke(callee, method, args);
                long stopNanos = t.stopNanos();

                Log.print("Timer: execution took " + stopNanos / 1e6 + "ms");

                return null;
            }
        };
    }

    public static Invocation finalInvocation() {
        return new Invocation() {
            @Override
            public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) throws Throwable {
                return method.invoke(callee, args);
            }
        };

    }
}
