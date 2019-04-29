/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.util.function.Supplier;
import lt.lb.ot.task1.Tool;

/**
 *
 * @author Lemmin
 */
public class CloneFactory<T extends Tool> implements Supplier<T>{

    private T template;
    public CloneFactory(T template){
        this.template = template;
    }

    @Override
    public T get() {
        return (T) template.copy();
    }
    
}



