/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.tools;

import lt.lb.commons.parsing.StringOp;
import lt.lb.ot.task1.Tool;

/**
 *
 * @author Lemmin
 */
public class Drill implements Tool {

    public String brand;
    public double power;
    public double price;
 
    
    public Drill(){
        brand = "";
        power = 15;
        price = 25;
    }
    
    //explicit clone implementetion
    
    @Override
    public Drill copy() {
        Drill drill = new Drill();
        drill.power = this.power;
        drill.brand = this.brand;
        drill.price = this.price;
        return drill;
    }

    @Override
    public double getPrice() {
        if (StringOp.isEmpty(brand)) {
            return price;
        } else {
            return price * 1.5;
        }
    }

}




