/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host.testcase;

import lt.lb.common.SharedInterface;
import static lt.lb.host.Host.JAR1_IMP_CLASS;
import static lt.lb.host.Host.JAR1_URL;
import static lt.lb.host.Host.JAR2_IMP_CLASS;
import static lt.lb.host.Host.JAR2_URL;
import static lt.lb.host.Host.closeLoaders;
import static lt.lb.host.Host.getJarLoader;

/**
 *
 * @author Laimonas Beniušis
 */
public class TestCase3 {
    /**
     * Aplikacija pakrauna ir naudoja dvi bibliotejos modulio versijas vienu
     * metu (Nurodymas: Naudoti atskirus klasių krovėjus kiekvienai bibliotekos
     * versijai.)
     *
     * @throws Exception
     */
    public static void main(String...args) throws Exception {
        System.out.println("TEST CASE 3");
        ClassLoader[] loader = new ClassLoader[]{getJarLoader(JAR1_URL), getJarLoader(JAR2_URL)};

        SharedInterface instance1 = (SharedInterface) loader[0].loadClass(JAR1_IMP_CLASS).newInstance();

        SharedInterface instance2 = (SharedInterface) loader[1].loadClass(JAR2_IMP_CLASS).newInstance();

        System.out.println("Both are used");

        System.out.println(instance1.doCalculation(instance2.doCalculation(10)));
        System.out.println(instance2.doCalculation(instance1.doCalculation(10)));
        System.out.println(instance1.getVersionString() + " " + instance2.getVersionString());

        closeLoaders(loader);
    }
}
