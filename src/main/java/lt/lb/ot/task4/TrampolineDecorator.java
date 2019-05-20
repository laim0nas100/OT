/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;
import lt.lb.commons.Caller;
import lt.lb.commons.Caller.CallerBuilder;

/**
 *
 * @author Lemmin
 */
public abstract class TrampolineDecorator extends AbstractFibComputerDecorator {

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        
        return call(currentIteration, iterations, first, second).resolve();
    }

    private Caller<BigInteger> call(long currentIteration, long iterations, BigInteger first, BigInteger second) {

        if (currentIteration < iterations) {
            BigInteger newVal = delegate().intermediate(0, 1, first, second);
            return new CallerBuilder<BigInteger>().
                    toCall(() -> call(currentIteration + 1, iterations, newVal, first));
        } else {
            return CallerBuilder.ofResult(first);
        }
    }

}


