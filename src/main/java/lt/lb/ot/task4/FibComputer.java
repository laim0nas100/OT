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
 */
public interface FibComputer {

    public default BigInteger compute(long iterations) {
        return intermediate(0, iterations, BigInteger.ONE, BigInteger.ONE);
    }

    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second);
}

