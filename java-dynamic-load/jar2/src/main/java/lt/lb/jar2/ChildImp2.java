/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jar2;

import lt.lb.common.SharedInterface;

/**
 *
 * @author Laimonas Beniušis
 */
public class ChildImp2 implements SharedInterface{
    @Override
    public long doCalculation(long numb) {
        return numb * 5 + 2;
    }

    @Override
    public String getVersionString() {
        return "VERSION 2";
    }
}
