/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import java.util.concurrent.TimeUnit;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.containers.Value;
import lt.lb.commons.containers.tuples.Tuples;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 2 ways to store:
 * Prototype scope (create new for each object)
 * Singleton scope, store target with key
 */
@Aspect
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CacheAspect {

    Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();
    private static final Object dud = new Object();

    @Around("cachedMethods()")
    public Object cacheMe(ProceedingJoinPoint jp) throws Throwable {
        Signature sig = jp.getSignature();
        if (sig instanceof MethodSignature) {
            Value<Throwable> th = new Value<>();
            Object key = getKeySingleton(jp);
            Object result = cache.get(key, () -> {
                try {

                    Log.print("Recalculate", key, key.hashCode());
                    return F.nullWrap(jp.proceed(), dud);
                } catch (Throwable ex) {
                    th.set(ex);
                    return dud;
                }
            });
            if (th.isNotNull()) {
                throw th.get();
            } else {
                return result == dud ? null : result;
            }
        } else {
            throw new IllegalArgumentException("Only applicable in method join points");
        }
    }
    
    public Object getKeySingleton(ProceedingJoinPoint jp){
        return Tuples.create(jp.getTarget(), jp.getSignature().toLongString(), Lists.newArrayList(jp.getArgs()));
    }
    
    public Object getKeyPrototype(ProceedingJoinPoint jp){
        return Tuples.create(jp.getSignature().toLongString(), Lists.newArrayList(jp.getArgs()));
    }

    @Pointcut("@annotation(Cached)")
    public void cachedMethods() {

    }
}
