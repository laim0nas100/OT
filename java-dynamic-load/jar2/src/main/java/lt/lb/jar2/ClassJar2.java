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
public class ClassJar2 {
    private CommonClass commonClass;

    public ClassJar2() {
        commonClass = new CommonClass("jar1");
        System.out.println("commonClass loader in ClassJar2:" + commonClass.getClass().getClassLoader());
    }

    public String getString() {
        return commonClass.getString();
    }
}
