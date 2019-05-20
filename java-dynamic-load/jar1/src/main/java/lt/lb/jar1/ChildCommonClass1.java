/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jar1;

import lt.lb.common.CommonClass;

/**
 *
 * @author Laimonas Beniušis
 */
public class ChildCommonClass1 extends CommonClass{
    @Override
    public String getString() {
        return s == null ? "ChildCommonClass1" : s;
    }

    public ChildCommonClass1(String s) {
        super(s);
    }

    public ChildCommonClass1() {
        super(null);
    }
}
