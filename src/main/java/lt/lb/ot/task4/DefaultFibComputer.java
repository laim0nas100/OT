/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;

/**
 *
 * @author Lemmin
 *
 */
public class DefaultFibComputer implements FibComputer {

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        if (currentIteration >= iterations) {
            return first;
        }
        return intermediate(currentIteration + 1, iterations, first.add(second), first);
    }

}


