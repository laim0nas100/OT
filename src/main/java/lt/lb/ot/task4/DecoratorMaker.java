/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.SafeOpt;
import lt.lb.commons.containers.tuples.Tuple;
import lt.lb.commons.containers.tuples.Tuples;
import lt.lb.commons.reflect.Refl;
import lt.lb.ot.task4.proxy.DecoratorContext;
import lt.lb.ot.task4.proxy.relfdec.MethodDescriptor;

/**
 *
 * @author Lemmin
 */
public class DecoratorMaker {

    private ClassLoader loader;

    private Object defaultObject;
    private Class[] interfaces;

    private Object decoratorObject;
    private Map<MethodDescriptor, Tuple<DecoratorContext, Method>> decMap = new HashMap<>();
    private Map<MethodDescriptor, Method> defMap = new HashMap<>();
    private DecoratorMaker next;
    private Object instance;

    private DecoratorMaker prev;

    public <T> T resolveInstance() {
        DecoratorMaker node = this;
        while (node.prev != null) {
            node = node.prev;
        }
        return F.cast(node.instance);
    }

    public static class DecorateableMethod {

        List<Class> paramTypes = new ArrayList<>();
        Class returnType;
        String name;
    }

    public DecoratorMaker(Object object, Class... interfaces) {
        this(object.getClass().getClassLoader(), object, interfaces, null);
    }

    public DecoratorMaker(ClassLoader loader, Object object, Class[] interfaces, Map<MethodDescriptor, Method> defaultMap) {
        this.defaultObject = object;
        this.interfaces = interfaces;
        this.loader = loader;
        if (interfaces.length == 0) {
            throw new IllegalArgumentException("Empty interface array");
        }
        if (defaultMap == null) {
            for (Method method : Refl.getMethodsOf(defaultObject.getClass(), m -> true)) {
                this.defMap.put(new MethodDescriptor(method), method);
            }
        } else {
            this.defMap = defaultMap;
        }
    }

    public DecoratorMaker decorate(Object decoratorObject) {
        DecoratorMaker me = this;
        this.decoratorObject = decoratorObject;
        List<Method> methods = Refl.getMethodsOf(decoratorObject.getClass(), method -> {
            return SafeOpt.ofNullable(method)
                    .map(m -> m.getParameterTypes())
                    .map(m -> m[0])
                    .map(m -> F.instanceOf(m, DecoratorContext.class))
                    .orElse(false);
        });

        for (Method method : methods) {
            Class[] typesWithoutFirst = ArrayOp.removeByIndex(method.getParameterTypes(), 0);
            MethodDescriptor desc = new MethodDescriptor(method.getName(), typesWithoutFirst, method.getReturnType());
            Tuple<DecoratorContext, Method> create = Tuples.create(null, method);
            decMap.put(desc, create);

            DecoratorContext decoratorContext = new DecoratorContext() {
                @Override
                public <T> T proceed(Object... args) {
                    DecoratorMaker next = me.next;
                    if (next.decMap.containsKey(desc)) {
                        Tuple<DecoratorContext, Method> get = next.decMap.get(desc);
                        Object[] newArgs = ArrayOp.addAt(args, 0, get.g1);
                        Log.println("", "Proceed dec", get.g2, next.decoratorObject, "with", Arrays.asList(newArgs));
                        return F.unsafeCall(() -> F.cast(get.g2.invoke(next.decoratorObject, newArgs)));
                    } else {
                        Log.println("", "Proceed def", next.defMap.get(desc), next.defaultObject, "with", Arrays.asList(args));
                        return F.unsafeCall(() -> F.cast(next.defMap.get(desc).invoke(next.defaultObject, args)));
                    }

                }
            };
            create.setG1(decoratorContext);

        }

        me.instance = Proxy.newProxyInstance(this.loader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodDescriptor desc = new MethodDescriptor(method);
                boolean invokeFound = false;
                DecoratorMaker node = me;
                while (!invokeFound) {
                    if (node.decMap.containsKey(desc)) {
                        Tuple<DecoratorContext, Method> tuple = node.decMap.get(desc);
                        Method decoratedMethod = tuple.g2;
                        if (tuple.g1 != null && node.next.instance != null) {
                            invokeFound = true;
                            Object[] newArgs = ArrayOp.addAt(args, 0, tuple.g1);
                            Log.println("", "Invoke dec", decoratedMethod, node.decoratorObject, "with", Arrays.asList(newArgs));
                            return decoratedMethod.invoke(node.decoratorObject, newArgs);
                        }

                    }
                    node = node.next;
                    if(node == null){
                        break;
                    }
                }

                if (!invokeFound) {
                    Log.println("", "Invoke", method, me.defaultObject, "with", Arrays.asList(args));
                    return method.invoke(me.defaultObject, args);
                }
                throw new Error("Unreachable was reached");

            }
        });

        DecoratorMaker maker = new DecoratorMaker(me.loader, me.defaultObject, me.interfaces, me.defMap);
        me.next = maker;
        maker.prev = me;
        maker.instance = me.defaultObject;
        return maker;
    }

}
