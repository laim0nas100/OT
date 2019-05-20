/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;
import lt.lb.commons.Caller;
import lt.lb.commons.Caller.CallerBuilder;
import lt.lb.ot.task4.proxy.DecoratorContext;

/**
 *
 * @author Lemmin
 */
public class TrampolineDecoratorDetached {

    public BigInteger intermediate(DecoratorContext ctx, long currentIteration, long iterations, BigInteger first, BigInteger second) {

        return call(ctx, currentIteration, iterations, first, second).resolve();
    }

    //Metode paduoti kaip parametra ar kažkaip kitaip delegate pointa.
    //Pakartoti parašą Function<....,Result>
    //Tada nebepaveldėti iš bazinės klasės
    private Caller<BigInteger> call(DecoratorContext ctx, long currentIteration, long iterations, BigInteger first, BigInteger second) {

        if (currentIteration < iterations) {

            BigInteger newVal = ctx.proceed(0, 1, first, second);
            return new CallerBuilder<BigInteger>().
                    toCall(() -> call(ctx, currentIteration + 1, iterations, newVal, first));
        } else {
            return CallerBuilder.ofResult(first);
        }
    }

}
