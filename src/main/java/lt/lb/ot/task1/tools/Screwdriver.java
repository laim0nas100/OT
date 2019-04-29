/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.tools;

import java.util.ArrayList;
import java.util.List;
import lt.lb.commons.F;
import lt.lb.commons.reflect.DefaultFieldFactory;
import lt.lb.commons.reflect.FieldFactory;
import lt.lb.ot.task1.Tool;
import lt.lb.ot.task1.factories.Creator;

/**
 *
 * @author Lemmin
 */
public class Screwdriver implements Tool {

    public static Creator<Screwdriver> screwdriverCreator = (Object... params) -> {
        double price = F.cast(params[0]);
        if (price > 30) {
            return new ExpensiveScrewdriver(price);
        } else {
            return new Screwdriver(30);
        }
    };

    public static FieldFactory fieldFactory = new DefaultFieldFactory();

    public List<Double> priceHistory = new ArrayList<>();

    public Screwdriver(double price) {
        this.priceHistory.add(price);
    }

    //using reflection clone
    @Override
    public Screwdriver copy() {
        return F.unsafeCall(() -> fieldFactory.reflectionClone(this));
    }

    @Override
    public double getPrice() {
        int i = priceHistory.size() - 1;
        return priceHistory.get(i);
    }

}

