/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.util.function.Supplier;
import lt.lb.ot.task1.Furniture;
import lt.lb.ot.task1.Tool;

/**
 *
 * @author Lemmin
 */
public class ProductTypeFactory<T extends Furniture, E extends Tool> {

    private Supplier<? extends T> furnitureFactory;
    private Supplier<? extends E> toolFactory;

    public ProductTypeFactory(Supplier<? extends T> furnitureFactory, Supplier<? extends E> toolFactory) {
        this.furnitureFactory = furnitureFactory;
        this.toolFactory = toolFactory;
    }

    public T getFurniture() {
        return furnitureFactory.get();
    }

    public E getTool() {
        return toolFactory.get();
    }

}


