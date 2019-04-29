/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import lt.lb.commons.SafeOpt.UnsafeFunction;

/**
 *
 * @author Laimonas Beniušis
 */
public class ConcreteDecoratorHandler<T> extends DecoratorHandler {

    private Map<MethodDescriptor, MethodDecorator> methodMap = new HashMap<>();

    public ConcreteDecoratorHandler(T comp) {
        super(comp);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodDescriptor desc = new MethodDescriptor(method);
        if (methodMap.containsKey(desc)) { // if has such implementation, then use it, otherwise use default delegate
            return methodMap.get(desc).apply(args);
        }
        return super.invoke(proxy, method, args);
    }

    public void addMethod(MethodDescriptor desc, UnsafeFunction<Object[], Object> func) {
        this.addMethod(desc, new MethodDecorator(func));
    }

    public void addMethod(MethodDescriptor desc, MethodDecorator methDec) {
        this.methodMap.put(desc, methDec);
    }

    public static <I, T extends I> ConcreteDecoratorHandler<T> constructFromClasses(T comp, Class<I> interfaceClass, Object[] implementations) {
        ConcreteDecoratorHandler handler = new ConcreteDecoratorHandler(comp);
        
        Set<MethodDescriptor> descriptors = new LinkedHashSet<>(); // preserve order
            
        
        for (Method m : interfaceClass.getDeclaredMethods()) {
            descriptors.add(new MethodDescriptor(m));
        }

        for (MethodDescriptor desc : descriptors) {
            for (Object ob : implementations) {

                try {
                    //try to find method with such signature, look at only public methods
                    Method method = ob.getClass().getMethod(desc.name, desc.argTypes);
                    //found such method signature, proceed to next method descriptor
                    handler.addMethod(desc, args -> method.invoke(ob, args));
                    break;
                } catch (NoSuchMethodException | SecurityException ex) {
                    
                }
            }
        }

        return handler;
    }

}
