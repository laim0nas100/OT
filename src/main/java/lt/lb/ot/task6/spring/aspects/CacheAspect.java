/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.containers.Value;
import lt.lb.commons.containers.tuples.Tuple;
import lt.lb.commons.containers.tuples.Tuples;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lemmin
 */
@Aspect
@Component
public class CacheAspect {

    Cache<Tuple<String, List>, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();
    private static final Object dud = new Object();

    @Around("cachedMethods()")
    public Object cacheMe(ProceedingJoinPoint jp) throws Throwable {
        Signature sig = jp.getSignature();
        if (sig instanceof MethodSignature) {
            Value<Throwable> th = new Value<>();
            Tuple<String, List> key = Tuples.create(sig.toLongString(), Lists.newArrayList(jp.getArgs()));
            Object result = cache.get(key, () -> {
                try {
                    
                    Log.print("Recalculate", key, key.hashCode());
                    return F.nullWrap(jp.proceed(),dud);
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

    @Pointcut("@annotation(Cached)")
    public void cachedMethods() {

    }
}
