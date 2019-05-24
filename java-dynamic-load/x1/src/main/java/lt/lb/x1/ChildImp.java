/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.x1;

import lt.lb.common.SharedInterface;

/**
 *
 * @author Laimonas Beniušis
 */
public class ChildImp implements SharedInterface{

    @Override
    public long doCalculation(long numb) {
        return numb * 2 + 5;
    }

    @Override
    public String getVersionString() {
        return "Child VERSION old";
    }
    
}
