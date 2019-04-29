/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

import java.math.BigInteger;
import lt.lb.commons.Log;
import lt.lb.commons.Timer;

/**
 *
 * @author Lemmin
 */
public class FibTimerComputerDecorator implements FibComputer {

    public FibComputer comp;

    public FibTimerComputerDecorator(FibComputer comp) {
        this.comp = comp;
    }


    @Override
    public BigInteger intermediate(long currentIteration, long iterations, BigInteger first, BigInteger second) {
        Timer timer = new Timer();
        BigInteger compute = comp.intermediate(currentIteration, iterations, first, second);
        Log.print("Computed in (nanos):" + timer.stopNanos());
        return compute;
    }

}








