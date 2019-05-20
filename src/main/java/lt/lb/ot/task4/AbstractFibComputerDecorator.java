/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;

/**
 *
 * @author Laimonas Beniušis
 */
public abstract class AbstractFibComputerDecorator implements FibComputer {

    protected abstract FibComputer delegate();
    
    

    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        return delegate().intermediate(currentIteration, iterations, first, second);
    }
    
}
