/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.containers.tuples.Tuple3;
import lt.lb.commons.containers.tuples.Tuples;
import lt.lb.commons.iteration.ReadOnlyIterator;
import lt.lb.commons.reflect.proxy.InvocationHandlers;
import lt.lb.commons.reflect.proxy.ProxyListenerBuilder;
import lt.lb.ot.Def;
import lt.lb.ot.task4.proxy.GenericCachingProxy;
import lt.lb.ot.task4.proxy.chain.InvocationChain;
import lt.lb.ot.task4.proxy.chain.InvocationChainBuilder;
import lt.lb.ot.task4.proxy.chain.InvocationChainHandlers;
import lt.lb.ot.task4.proxy.chain.InvocationChainProxy;

/**
 *
 * @author Lemmin
 */
public class main4 {

    public static void main(String... args) throws Exception {
        Def.init.get();
        Log.main().disable = false;

        //base case
        FibComputer c = new FibTimerComputerDecorator(new IterativeDecorator(new DefaultFibComputer()));

        //dont change method behaviour, juts add hooks
        ClassLoader classLoader = main4.class.getClassLoader();
        AtomicLong counter = new AtomicLong(0);
        ProxyListenerBuilder pb = new ProxyListenerBuilder(classLoader)
                .addNameInvocationHandler("intermediate", InvocationHandlers.ofRunnable(() -> {
                    counter.incrementAndGet(); // count how many times method "intermediate" was invoked
                }));

        FibComputer methodListener = pb.ofInterfaces(new DefaultFibComputer(), FibComputer.class);
        methodListener = new TrampolineComputer(methodListener);
        methodListener = new FibTimerComputerDecorator(methodListener);

        //change method behaviour, cache results for specific method
        FibComputer defFib = new IterativeDecorator(new DefaultFibComputer());
        FibComputer cached = (FibComputer) Proxy.newProxyInstance(classLoader, ArrayOp.asArray(FibComputer.class), new GenericCachingProxy(defFib));
        cached = new FibTimerComputerDecorator(cached);

        //compute results
        long iterations = 200000;
        BigInteger[] arr = new BigInteger[9];
        int i = 0;
        arr[i++] = cached.compute(iterations);
        arr[i++] = methodListener.compute(iterations);
        arr[i++] = c.compute(iterations);

        arr[i++] = cached.compute(iterations);
        arr[i++] = methodListener.compute(iterations);
        arr[i++] = c.compute(iterations);

        arr[i++] = cached.compute(iterations / 4);
        arr[i++] = methodListener.compute(iterations / 4);
        arr[i++] = c.compute(iterations / 4);

        Log.printLines(ReadOnlyIterator.of(arr));

        //count how many times was intermediate invoked
        Log.print("Intermediate was invoked:" + counter.get());

        //Chain approach
        InvocationChain chain = InvocationChainBuilder.of(
                InvocationChainHandlers.loggingInvocation(s -> Log.print(s)),
                InvocationChainHandlers.timerInvocation(),
                InvocationChainHandlers.finalInvocation()
        );
        FibComputer newProxyInstance = (FibComputer) Proxy.newProxyInstance(
                classLoader,
                ArrayOp.asArray(FibComputer.class),
                new InvocationChainProxy(new DefaultFibComputer(), chain)
        );

        Log.print("WITH CHAIN");
        newProxyInstance.compute(100);
        newProxyInstance.compute(1000);

        Log.close();

    }
}
