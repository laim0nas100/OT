/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Laimonas Beniušis
 */
public class GenericCachingProxy implements InvocationHandler {

    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private final Object real;
    private final Object STUB = new Object();

    public GenericCachingProxy(Object object) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        real = object;
        for (PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {
            cache.put(desc.getReadMethod().getName(), STUB);
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (cache.containsKey(method.getName())) {
            Object o = cache.get(method.getName());
            if (o == STUB) {
                Object returned = method.invoke(real, args);
                cache.put(method.getName(), returned);
                return returned;
            } else {
                return o;
            }
        }
        return method.invoke(real,args);
    }

    public static <I, T extends I> I decorate(T t, Class<I> interfaceClass) {
        try {
            GenericCachingProxy cacheableDecorator = new GenericCachingProxy(t);
            return (I) Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class[]{interfaceClass},
                    cacheableDecorator
            );
        } catch (IntrospectionException ex) {
            return null;
        }

    }
}
