/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.util.function.Supplier;
import lt.lb.ot.task1.Product;

/**
 *
 * @author Lemmin
 */
public class CreatorFactory<T extends Product> implements Supplier<T>{

    
    private Creator<T> creator;
    private Object[] params;
    public CreatorFactory(Creator<T> creator, Object...params){
        this.creator = creator;
        this.params = params;
    }
    
    @Override
    public T get() {
        return creator.create(params);
    }
    
}






