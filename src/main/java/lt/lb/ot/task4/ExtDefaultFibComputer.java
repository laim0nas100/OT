/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4;

/**
 *
 * @author Laimonas Beniušis
 */
public class ExtDefaultFibComputer extends DefaultFibComputer implements ExtFibComputer {

    @Override
    public String catString() {
        return this.getString() +" "+ this.getString();
    }

    @Override
    public String getString() {
        return "Unmodified";
    }
    
}