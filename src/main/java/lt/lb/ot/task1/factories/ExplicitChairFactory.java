/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.util.function.Supplier;
import lt.lb.ot.task1.furniture.Chair;

/**
 *
 * @author Lemmin
 */
public class ExplicitChairFactory implements Supplier<Chair> {

    @Override
    public Chair get() {
        Chair chair = new Chair();
        chair.material = "wood";
        chair.price = 100;
        return chair;
    }

}




