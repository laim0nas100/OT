/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jar2;

import lt.lb.common.CommonClass;

/**
 *
 * @author Laimonas Beniušis
 */
public class ChildCommonClass2 extends CommonClass{
    @Override
    public String getString() {
        return s == null ? "ChildCommonClass2" : s;
    }

    public ChildCommonClass2(String s) {
        super(s);
    }

    public ChildCommonClass2() {
        super(null);
    }
}
