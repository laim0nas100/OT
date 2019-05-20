/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.Log;
import lt.lb.commons.reflect.proxy.InvocationHandlers;
import lt.lb.commons.reflect.proxy.ProxyListenerBuilder;
import lt.lb.ot.Def;
import lt.lb.ot.task4.proxy.GenericCachingProxy;
import lt.lb.ot.task4.proxy.chain.InvocationChainBuilder;
import lt.lb.ot.task4.proxy.chain.InvocationChainHandlers;
import lt.lb.ot.task4.proxy.relfdec.MethodDecorator;

/**
 *
 * @author Lemmin
 */
public class main4 {

    public static void main(String... mainArgs) throws Exception {
        Def.init.get();
        Log.main().disable = false;
        Log.main().async = false;

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

        
        
        FibComputer comp = new DecoratorMaker(new DefaultFibComputer(), FibComputer.class)
                .decorate(new MemoizerDecorator())
                .decorate(new TrampolineDecoratorDetached())
                .decorate(new PlusDecorator())
                .resolveInstance();

        
        FibComputer toInstance = chainBuilder.toInstance(comp, FibComputer.class);
        
        
        Log.print(FibComputer.computer(toInstance, 2));
        Log.print(toInstance.plus(3));
        Log.close();

    }
}
