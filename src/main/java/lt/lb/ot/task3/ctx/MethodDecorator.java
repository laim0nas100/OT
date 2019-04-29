/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.ctx;

/**
 *
 * @author Laimonas Beniušis
 */
public interface MethodDecorator extends Runnable {
    
    @Override
    public void run();
}
