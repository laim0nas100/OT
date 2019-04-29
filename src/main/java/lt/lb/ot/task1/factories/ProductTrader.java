/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.util.List;
import lt.lb.commons.F;

/**
 *
 * @author Lemmin
 */
public class ProductTrader<Product> {

    private List<Creator<Product>> creators;

    public ProductTrader(List<Creator<Product>> creators) {
        this.creators = creators;
    }

    public Product get(Object... args) {
        for (Creator<Product> cr : creators) {
            try {
                return cr.create(args);

            } catch (Exception ex) {

            }
        }
        throw new IllegalStateException("No creator was accepted");
    }
}


