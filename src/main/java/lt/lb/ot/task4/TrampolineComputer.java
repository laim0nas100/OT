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
public class TrampolineComputer implements FibComputer {

    public FibComputer comp;

    public TrampolineComputer(FibComputer comp) {
        this.comp = comp;
    }

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        return call(currentIteration, iterations, first, second).resolve();
    }

    private Caller<BigInteger> call(long currentIteration, long iterations, BigInteger first, BigInteger second) {

        if (currentIteration < iterations) {
            BigInteger newVal = comp.intermediate(0, 1, first, second);
            return new CallerBuilder<BigInteger>()
                    .toCall(args -> call(currentIteration + 1, iterations, newVal, first));
        } else {
            return CallerBuilder.ofResult(first);
        }
    }

    @Override
    public long plus(long numb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
