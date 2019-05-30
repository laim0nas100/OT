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
public class TestCase2 {
    /**
     * Aplikacija dinamiškai atnaujina bibliotekos modulį ir naudojamą
     * objektą.(Nurodymas: pakrauti modulį iš naujo kitame klasių krovėjo
     * egzemplioriuje; senas objektas turi būti "pamirštamas", o senas
     * URLClassLoader uždaromas).
     *
     * @throws Exception
     */
    public static void main(String...args) throws Exception {
        System.out.println("TEST CASE 2");
        ClassLoader loader = getJarLoader(JAR1_URL);

        Class<?> cls = loader.loadClass(JAR1_IMP_CLASS);

        SharedInterface instance = (SharedInterface) cls.newInstance();

        //senas classloader uždatomas
        closeLoaders(loader);

        System.out.println(instance.doCalculation(10));
        System.out.println(instance.getVersionString());

        loader = getJarLoader(JAR2_URL);

        Class<?> new_cls = loader.loadClass(JAR2_IMP_CLASS);
        //senas objektas "pamirštamas"
        instance = (SharedInterface) new_cls.newInstance();

        System.out.println("New");

        System.out.println(instance.doCalculation(10));
        System.out.println(instance.getVersionString());

        closeLoaders(loader);

    }
}
