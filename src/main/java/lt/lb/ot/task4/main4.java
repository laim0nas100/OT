/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import com.google.common.collect.Lists;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.Log;
import lt.lb.commons.SafeOpt;
import lt.lb.commons.containers.Value;
import lt.lb.commons.iteration.ReadOnlyIterator;
import lt.lb.commons.reflect.proxy.InvocationHandlers;
import lt.lb.commons.reflect.proxy.ProxyListenerBuilder;
import lt.lb.ot.Def;
import lt.lb.ot.task4.proxy.GenericCachingProxy;
import lt.lb.ot.task4.proxy.chain.Invocation;
import lt.lb.ot.task4.proxy.chain.InvocationChain;
import lt.lb.ot.task4.proxy.chain.InvocationChainBuilder;
import lt.lb.ot.task4.proxy.chain.InvocationChainHandlers;
import lt.lb.ot.task4.proxy.chain.InvocationChainProxy;
import lt.lb.ot.task4.proxy.relfdec.ConcreteDecoratorHandler;
import lt.lb.ot.task4.proxy.relfdec.MethodDecorator;

/**
 *
 * @author Lemmin
 */
public class main4 {

    public static void main(String... mainArgs) throws Exception {
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

        //NEW
        MethodDecorator dec = new MethodDecorator(args -> {
            String argString = Optional.ofNullable(args).map(m -> Arrays.asList(m)).orElse(Arrays.asList()).toString();
            Log.print("ARGS:", argString);
            return null;
        });

        //for all methods
        InvocationChainBuilder chainBuilder = new InvocationChainBuilder()
                .with(InvocationChainHandlers.loggingInvocation(s -> Log.print(s)))
                .with(InvocationChainHandlers.timerInvocation())
                .with(dec.toInvocation())
                .with(InvocationChainHandlers.finalInvocation());

        ExtFibComputer main = new ExtDefaultFibComputer();

        ExtraMethods extra1 = new ExtraMethods() {
            @Override
            public String catString() {
                return "Modified x2";
            }

            @Override
            public String getString() {
                return "Modified";
            }
        };
        FibComputer tramp = new TrampolineDecorator() {
            @Override
            protected FibComputer delegate() {
                return main;
            }

        };

        FibComputer memoizer = new AbstractFibComputerDecorator() {
            HashMap<List, BigInteger> cache = new HashMap<>();

            @Override
            protected FibComputer delegate() {
                return tramp;
            }

            @Override
            public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
                List list = Arrays.asList(currentIteration, iterations, first, second);
                return cache.computeIfAbsent(list, key -> {
                    return delegate().intermediate(currentIteration, iterations, first, second);
                });
            }
        };

        Object[] implementations = {
            memoizer, extra1

        };

        //contruct decorator with default implementation and decorative implementations
        ExtFibComputer constructFromClasses = ConcreteDecoratorHandler.constructFromClasses(main, ExtFibComputer.class, implementations);
        //apply generic decorator methods
        ExtFibComputer finalComp = chainBuilder.toInstance(ExtFibComputer.class, constructFromClasses);

        Log.print("WITH CHAIN");
        BigInteger compute0 = FibComputer.computer(finalComp, 100);
        BigInteger compute1 = FibComputer.computer(finalComp, 20000);
        BigInteger compute2 = FibComputer.computer(finalComp, 20000); // should be fast, if memoized
        Log.println("", compute0, compute1, compute2, finalComp.getString(), finalComp.catString());

        Log.close();

    }
}
