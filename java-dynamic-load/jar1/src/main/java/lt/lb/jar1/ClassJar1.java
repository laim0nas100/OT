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
public class ClassJar1 {
    private CommonClass commonClass;

    public ClassJar1() {
        commonClass = new CommonClass("jar1");
        System.out.println("commonClass loader in ClassJar1:" + commonClass.getClass().getClassLoader());
    }

    public String getString() {
        return commonClass.getString();
    }
}
