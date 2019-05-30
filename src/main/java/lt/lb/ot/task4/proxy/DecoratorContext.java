/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy;

/**
 *
 * @author Lemmin
 */
public interface DecoratorContext {
    
    public <T> T proceed(Object...args);
    
}
