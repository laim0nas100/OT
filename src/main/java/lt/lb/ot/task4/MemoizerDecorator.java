/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lt.lb.ot.task4.proxy.DecoratorContext;

/**
 *
 * @author Lemmin
 */
public class MemoizerDecorator {

    private Map<Object, BigInteger> map = new HashMap<>();

    public BigInteger intermediate(DecoratorContext ctx, long currentIteration, long iterations, BigInteger first, BigInteger second) {
        List list = Arrays.asList(currentIteration, iterations, first, second);
        return map.computeIfAbsent(list, key -> {
            return ctx.proceed(currentIteration, iterations, first, second);
        });

    }
}
