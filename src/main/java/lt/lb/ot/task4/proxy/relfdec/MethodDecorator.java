/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.util.function.Function;
import lt.lb.commons.func.unchecked.UnsafeFunction;
import lt.lb.ot.task4.proxy.chain.Invocation;

/**
 *
 * @author Laimonas Beniušis
 */
public class MethodDecorator {

    public UnsafeFunction<Object[], Object> function = args -> null;
    public Function<Object[], Object[]> paramDecorator = Function.identity();
    public Function<Object, Object> resultDecorator = Function.identity();

    public Object apply(Object[] param) throws Exception {
        return resultDecorator.apply(function.apply(paramDecorator.apply(param)));
    }
    
    public MethodDecorator(UnsafeFunction<Object[], Object> function){
        this.function = function;
    }
    
    public MethodDecorator(){}
    
    public Invocation toInvocation(){
        return Invocation.of(this);
    }
}
