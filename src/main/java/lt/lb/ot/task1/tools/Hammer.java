/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.tools;

import lt.lb.commons.F;
import lt.lb.ot.task1.Tool;

/**
 *
 * @author Lemmin
 */
public class Hammer implements Tool, Cloneable {

    public double price = 5; // default price

    
    // useing Java default clone method
    @Override
    public Hammer copy() {
        return F.cast(F.unsafeCall(this::clone));
    }

    @Override
    public double getPrice() {
        return price;
    }

}





