/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.util.function.Function;
import lt.lb.commons.SafeOpt.UnsafeFunction;

/**
 *
 * @author Laimonas Beniušis
 */
public class MethodDecorator {

    public UnsafeFunction<Object[], Object> function;
    public Function<Object[], Object[]> paramDecorator = Function.identity();
    public Function<Object, Object> resultDecorator = Function.identity();

    public Object apply(Object[] param) throws Exception {
        Object[] decParams = paramDecorator.apply(param);
        Object result = function.apply(decParams);
        return resultDecorator.apply(result);
        
    }
    
    public MethodDecorator(UnsafeFunction<Object[], Object> function){
        this.function = function;
    }
}
