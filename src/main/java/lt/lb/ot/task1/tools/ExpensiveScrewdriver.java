/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.tools;

/**
 *
 * @author Lemmin
 */
public class ExpensiveScrewdriver extends Screwdriver {
    
    public ExpensiveScrewdriver(double price) {
        super(price);
    }

    @Override
    public double getPrice() {
        return super.getPrice()*2;
    }
    
    
    
}


