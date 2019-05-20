/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import lt.lb.ot.task4.proxy.DecoratorContext;

/**
 *
 * @author Lemmin
 */
public class PlusDecorator {
    
    public long plus(DecoratorContext ctx, long numb){
        return ctx.proceed(numb * 2);
    }
    
}
