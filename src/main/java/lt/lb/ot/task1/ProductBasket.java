/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1;

import java.util.function.Supplier;
import lt.lb.commons.containers.LazyValue;

/**
 *
 * Client
 *
 * @author Lemmin
 */
public class ProductBasket {

    private final LazyValue<Product[]> products;

    public ProductBasket(int amount, Supplier<? extends Product> productFactory) {
        products = new LazyValue<>(() -> {
            Product[] pr = new Product[amount];
            for (int i = 0; i < amount; i++) {
                pr[i] = productFactory.get();
            }
            return pr;
        });
    }

    public Product[] getProducts() {
        return products.get();
    }
    
}






