/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;
import lt.lb.commons.CallOrResult;
import lt.lb.commons.F;

/**
 *
 * @author Lemmin
 */
public class TrampolineDecorator implements FibComputer {

    public FibComputer comp;

    public TrampolineDecorator(FibComputer comp) {
        this.comp = comp;
    }

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        return F.unsafeCall(() -> CallOrResult.iterative(-1, call(currentIteration, iterations, first, second)).get());
    }

    private CallOrResult<BigInteger> call(long currentIteration, long iterations, BigInteger first, BigInteger second) {

        if (currentIteration < iterations) {
            BigInteger newVal = comp.intermediate(0, 1, first, second);
            return CallOrResult.returnIntermediate(newVal, () -> {
                return call(currentIteration + 1, iterations, newVal, first);
            });
        } else {
            return CallOrResult.returnValue(first);
        }
    }

}


