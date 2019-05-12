/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicCountable implements Countable {

    private AtomicInteger count = new AtomicInteger();
    @Override
    public int inc() {
        return count.incrementAndGet();
    }

    @Override
    public int dec() {
        return count.decrementAndGet();
    }

    @Override
    public int getCount() {
        return count.get();
    }
    
}
