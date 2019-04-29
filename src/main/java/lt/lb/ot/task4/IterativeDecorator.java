package lt.lb.ot.task4;

import java.math.BigInteger;

/**
 *
 * @author Lemmin
 */
public class IterativeDecorator implements FibComputer {

    public FibComputer comp;

    public IterativeDecorator(FibComputer comp) {
        this.comp = comp;
    }

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        while (currentIteration < iterations) {
            BigInteger newVal = comp.intermediate(0, 1, first, second);
            second = first;
            first = newVal;
            currentIteration++;
        }
        return first;
    }

}




