/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.furniture;

import lt.lb.ot.task1.Furniture;

/**
 *
 * @author Lemmin
 */
public class Bed extends Furniture {

    private double price;

    public Bed(String type, double price) {
        this.material = type;
        this.price = price;
    }


    @Override
    public double getPrice() {
        return price;
    }

}


