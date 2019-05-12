/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import lt.lb.ot.task4.proxy.relfdec.MethodDecorator;
import lt.lb.ot.task4.proxy.relfdec.MethodDescriptor;

/**
 *
 * @author Laimonas Beniušis
 */
public class Visitors {

    public static void acceptVisitor(Object ob, Visitor visitor) throws Exception {
        Class<? extends Object> visitorClass = visitor.getClass();
        try {
            Method m = visitorClass.getDeclaredMethod("visit", ob.getClass());
            m.invoke(visitor, ob);
            //todo 
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            Method m = visitorClass.getMethod("visit", ob.getClass());
            m.invoke(visitor, ob);
        }
    }

    public static Visitor createDynamicVisitor(Class[] interfaces, Visitor[] implementations, Visitor primary) {
        Set<MethodDescriptor> descriptors = new LinkedHashSet<>(); // preserve order
        Map<MethodDescriptor, MethodDecorator> decMap = new HashMap<>();
        Set<MethodDescriptor> primaryMethods = new HashSet<>();

        for (Method m : primary.getClass().getDeclaredMethods()) {
            primaryMethods.add(new MethodDescriptor(m));
        }

        for (Class cl : interfaces) {
            for (Method m : cl.getDeclaredMethods()) {
                descriptors.add(new MethodDescriptor(m));
            }
        }

        for (MethodDescriptor desc : descriptors) {
            boolean found = false;
            for (Object ob : implementations) {

                try {
                    //try to find method with such signature, look at only public methods
                    Method method = ob.getClass().getMethod(desc.name, desc.argTypes);
                    //found such method signature, proceed to next method descriptor
                    decMap.put(desc, new MethodDecorator(args -> method.invoke(ob, args)));
                    found = true;
                    break;
                } catch (NoSuchMethodException | SecurityException ex) {

                }
            }

            if (!found) {
                throw new IllegalArgumentException(desc.name + " (" + Arrays.asList(desc.argTypes) + ") is not implemented anywhere");
            }
        }

        return (Visitor) Proxy.newProxyInstance(Visitors.class.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodDescriptor des = new MethodDescriptor(method);
                if (primaryMethods.contains(des)) { // default implementation
                    return method.invoke(primary, args);
                }
                if (decMap.containsKey(des)) {
                    return decMap.get(des).apply(args);
                } else {
                    throw new IllegalStateException("No such method found in any class" + method);
                }
            }
        });
        
        
    }
}
