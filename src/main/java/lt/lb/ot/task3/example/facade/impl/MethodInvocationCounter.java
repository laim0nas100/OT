/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade.impl;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Laimonas Beniušis
 */
public class MethodInvocationCounter{

    private AtomicLong counterEnd = new AtomicLong();
    private AtomicLong counterStart = new AtomicLong();

    public void end() {
        System.out.println("Execute end " + counterEnd.incrementAndGet());
    }
    
    public void start() {
        System.out.println("Execute start " + counterStart.incrementAndGet());
    }

}
