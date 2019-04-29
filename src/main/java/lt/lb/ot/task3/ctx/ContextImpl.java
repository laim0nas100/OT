/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.ctx;

import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lt.lb.commons.F;
import lt.lb.commons.LineStringBuilder;
import lt.lb.commons.Log;
import lt.lb.commons.containers.BooleanValue;
import lt.lb.commons.containers.LazyValue;
import lt.lb.commons.containers.Value;
import lt.lb.commons.misc.NestedException;

/**
 *
 * @author Laimonas Beniušis
 */
public class ContextImpl {

    public static ContextImpl global = new ContextImpl();

    //for debugging
    public LineStringBuilder stack = new LineStringBuilder();

    //recursion stack
    private boolean inside = false;
    private Map<Set<Class>, Value<Object>> visitedClasses = new HashMap<>();

    //main map
    private Map<Class, Supplier> interfaceMap = new HashMap<>();

    private <T> Supplier<T> registerWire(Class<T> cls, Supplier<T> supp, boolean lazy) {
        return register(cls, wire(cls, supp, lazy));
    }

    private <T> Supplier<T> register(Class<T> cls, Supplier<T> sup) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException(cls.getName() + " is not an interface");
        }
        interfaceMap.put(cls, sup);
        return sup;
    }

    public <T> Supplier<T> registerConcreteFactory(Class<T> cls, Supplier<T> sup) {
        if (cls.isInterface()) {
            throw new IllegalArgumentException(cls.getName() + " is an interface");
        }
        interfaceMap.put(cls, sup);
        return sup;
    }

    public <T> Supplier<T> registerConcreteSingletonLazy(Class<T> cls, Supplier<T> sup) {
        return registerConcreteFactory(cls, new LazyValue(sup));
    }

    public <T> Supplier<T> registerConcreteSingleton(Class<T> cls, Supplier<T> sup) {
        Supplier<T> lazy = this.registerConcreteSingletonLazy(cls, sup);
        lazy.get();//init
        return lazy;
    }

    public <T> Supplier<T> registerSingletonLazy(Class<T> cls, Supplier<T> supp) {
        return this.register(cls, new LazyValue<>(wire(cls, supp, true)));
    }

    public <T> Supplier<T> registerSingleton(Class<T> cls, Supplier<T> supp) {
        Supplier<T> registerLazySingleton = this.registerSingletonLazy(cls, supp);
        registerLazySingleton.get(); // commence initialization
        return registerLazySingleton;
    }

    public <T> Supplier<T> registerFactory(Class<T> cls, Supplier<T> supp) {
        return this.registerWire(cls, supp, false);
    }

    public <T> Supplier<T> registerFactoryLazy(Class<T> cls, Supplier<T> supp) {
        return this.registerWire(cls, supp, true);
    }

    public <T> Supplier<T> getSupplierOf(Class<T> cls) {
        Supplier sup = interfaceMap.get(cls);
        if (sup == null) {
            throw new IllegalAccessError("No maping to type " + cls.getName() + " could be resolved");
        }
        return sup;
    }

    public boolean isRegistered(Class cls) {
        return interfaceMap.containsKey(cls);
    }

    public <T> T getOf(Class<T> cls) {
        return getSupplierOf(cls).get();
    }

    public <T> Optional<T> getIfRegisteredOf(Class<T> cls) {
        if (isRegistered(cls)) {
            return Optional.ofNullable(getOf(cls));
        }
        return Optional.empty();
    }

    public <T> Optional<Supplier<T>> getSupplierIfRegisteredOf(Class<T> cls) {
        if (isRegistered(cls)) {
            return Optional.ofNullable(getSupplierOf(cls));
        }
        return Optional.empty();
    }

    private <T> Supplier<T> wire(Class<T> cls, Supplier<T> supp, boolean lazy) {

        Supplier<T> wire = () -> {
            LazyValue<T> implementingObject = new LazyValue<>(supp);
            return createFinalWired(lazy, () -> wireDecorateInfo(implementingObject.get()), () -> wireDelegates(cls, implementingObject.get()), getInterfaces(cls), cls);
        };

        return wire;

    }

    private String getClassNames(Class[] interfaces) {
        return Stream.of(interfaces).map(m -> m.getName()).collect(Collectors.toList()).toString();
    }

    private void wireSetters(Value val, Class[] interfaces) {
        String names = getClassNames(interfaces);
        Log.print(stack, "Wire setters for ", val.get(), names);

        Class vCls = val.get().getClass();

        Stream<Method> methodStream = Stream.of(vCls.getMethods())
                .filter(p -> p.getName().startsWith("set"))
                .filter(p -> p.getParameterCount() == 1)
                .filter(p -> F.instanceOf(p.getParameterTypes()[0], Object.class));
        methodStream.forEachOrdered(method -> {
            Class parameterType = method.getParameterTypes()[0];
            Log.print(stack, "Try to wire ", parameterType.getName());
            Optional valueToSet = this.getIfRegisteredOf(parameterType);

            if (valueToSet.isPresent()) {
                try {
                    method.invoke(val.get(), valueToSet.get());
                    Log.print(stack, "Success");
                } catch (Exception ex) {
                    Log.print(stack, "Error", ex.getLocalizedMessage());
                }
            } else {
                Log.print(stack, "Not present");
            }

        });
    }

    private <T> DecorateInfo wireDecorateInfo(T val) {
        Class vCls = val.getClass();
        DecorateInfo info = new DecorateInfo();

        for (Annotation ann : vCls.getDeclaredAnnotationsByType(Decorate.class)) {
            Log.print(stack, "Found decorate:", ann);
            Decorate dec = F.cast(ann);
            Object decoratorObject = getOf(dec.clazz());

            info.before = createRunnablesFromMethod(dec.clazz(), decoratorObject, dec.beforeMethod());
            info.after = createRunnablesFromMethod(dec.clazz(), decoratorObject, dec.afterMethod());
        }
        return info;
    }

    private <T> Map<Class, Object> wireDelegates(Class interfaceClass, T defaultImplementation) {
        Map<Class, Object> delegateMap = new HashMap<>();
        Annotation[] delegatedAnnotations = interfaceClass.getDeclaredAnnotationsByType(Delegate.class);
        delegateMap.put(interfaceClass, defaultImplementation);// default case

        for (Annotation ann : delegatedAnnotations) {

            Log.print(stack, "Found delegate:", ann);
            Delegate del = (Delegate) ann;
            Class on = del.on();
            if (!on.isInterface()) {
                throw new IllegalArgumentException("@Delegate on=" + on.getName() + " is not an interface");
            }
            Object delegateObject = getOf(del.by());
            delegateMap.put(on, delegateObject);
        }
        return delegateMap;
    }

    private <T> Class[] getInterfaces(Class defaultInterfaceClass) {
        Annotation[] delegatedAnnotations = defaultInterfaceClass.getDeclaredAnnotationsByType(Delegate.class);
        List<Class> interfaceList = new ArrayList<>();
        interfaceList.add(defaultInterfaceClass);

        for (Annotation ann : delegatedAnnotations) {
            Delegate del = (Delegate) ann;
            interfaceList.add(del.on());
        }

        return interfaceList.stream().toArray(size -> new Class[size]);
    }

    private <T> T createFinalWired(boolean lazy,
            Supplier<DecorateInfo> decorateInfoSupp,
            Supplier<Map<Class, Object>> delegateInfoSupp,
            Class[] interfaces, Class defaultInterfaceClass) {

        Value completedImpl = new Value<>(); // completed implementation, pointer to safe in map, for local context

        if (lazy) {
            LazyValue<T> lazyProxy = new LazyValue<>(() -> createFinalWired(false, decorateInfoSupp, delegateInfoSupp, interfaces, defaultInterfaceClass));
            InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
                T implementation = lazyProxy.get(); // initialize on demand
                return method.invoke(implementation, args);
            };
            completedImpl.set(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, handler));
        } else {
            //recursion prevension, detect if root call and then emulate local context
            boolean wasTop = !inside;
            stack.append("--");
            if (!inside) {
                inside = true;
                visitedClasses.clear();
            }

            Set<Class> toSet = Sets.newHashSet(interfaces);
            if (visitedClasses.containsKey(toSet)) {
                Value<Object> get = visitedClasses.get(toSet);
                Log.print(stack, "Recursive duplication", getClassNames(interfaces));
                return (T) get.get();

            } else {
                Log.print(stack, "Save ", getClassNames(interfaces));
                visitedClasses.put(toSet, completedImpl);
            }

            BooleanValue initDone = new BooleanValue(false);
            DecorateInfo decorateInfo = decorateInfoSupp.get();
            Map<Class, Object> delegateMap = delegateInfoSupp.get();

            boolean needProxy = (!decorateInfo.isEmpty() || delegateMap.size() > 1); // has decorators and has implementations other than defaultImpl
            if (!needProxy) {
                T implementingObject = (T) delegateMap.get(defaultInterfaceClass);
                completedImpl.set(implementingObject);

            } else {
                T defaultCase = (T) delegateMap.get(defaultInterfaceClass);

                InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
                    if (initDone.get()) {
                        decorateInfo.before.forEach(Runnable::run);
                    }

                    Class key = method.getDeclaringClass();
                    Object methodImplementationObject = delegateMap.getOrDefault(key, defaultCase);

                    String prefix = initDone.not() ? "Before Init " : "";
                    Log.print(stack, prefix + "Invoke " + method + " with " + methodImplementationObject);
                    Object result = method.invoke(methodImplementationObject, args);

                    if (initDone.get()) {
                        decorateInfo.after.forEach(Runnable::run);
                    }

                    return result;
                };
                completedImpl.set(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, handler));

            }
            wireSetters(completedImpl, interfaces);
            initDone.setTrue();
            if (wasTop) {
                inside = false;
            }
            stack.removeFromEnd(2);

        }

        return (T) completedImpl.get();

    }

    private void printMap() {
        List<String> lines = new LinkedList<>();
        F.iterate(visitedClasses, (cl, val) -> {
            lines.add(getClassNames(cl.stream().toArray(size -> new Class[size])) + " -> " + val);

        });
        Log.printLines(lines);
    }

    private static List<Runnable> createRunnablesFromMethod(Class clazz, Object delegateObject, String[] methods) {
        List<Runnable> list = new LinkedList<>();
        try {

            for (String methodName : methods) {
                Method method = clazz.getMethod(methodName);
                Runnable run = () -> {
                    try {
                        method.invoke(delegateObject);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw NestedException.of(e);
                    }
                };
                list.add(run);
            }

        } catch (NoSuchMethodException | SecurityException e) {
            throw NestedException.of(e);
        }
        return list;
    }

}
