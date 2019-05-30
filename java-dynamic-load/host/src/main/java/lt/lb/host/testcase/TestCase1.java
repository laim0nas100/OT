/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host.testcase;

import java.lang.reflect.Method;
import lt.lb.common.SharedInterface;
import static lt.lb.host.Host.JAR1_IMP_CLASS;
import static lt.lb.host.Host.JAR1_URL;
import static lt.lb.host.Host.closeLoaders;
import static lt.lb.host.Host.getJarLoader;

/**
 *
 * @author Laimonas Beniušis
 */
public class TestCase1 {
    /**
     * Aplikacija pakarauna JAR modulį ("biblioteką") iš dinamiškai nurodyto
     * failo (vykdymo metu įvedamas pavadinimas). Aplikacija sukuria bibliotekos
     * klasės objektą ir jį panaudoja. Objekto panaudojimui pademonstruoti dvi
     * technikas:
     *
     * - panaudojimas per refleksyvumo API; - pagrindinis aplikacija statiškai
     * naudoja ir (krauna - turi klasių krovimo kelyje) interfeisą, kurį
     * relaizuoja modulio klasė. Sukurtą objektą aplikacija naudoja per statinį
     * interfeisą. (Pastaba: tolimenėse užduoties dalyse taikyti tik šią
     * techniką)
     *
     * @throws Exception
     */
    public static void main(String...args) throws Exception {
        System.out.println("TEST CASE 1");
        ClassLoader loader = getJarLoader(JAR1_URL);

        Class<?> cls = loader.loadClass(JAR1_IMP_CLASS);

        Object reflInstance = cls.getDeclaredConstructor().newInstance();
        Method calcMethod = cls.getMethod("doCalculation", long.class);
        Method verMethod = cls.getMethod("getVersionString");

        System.out.println(calcMethod.invoke(reflInstance, 10L));
        System.out.println(verMethod.invoke(reflInstance));

        SharedInterface newInstance = (SharedInterface) cls.getDeclaredConstructor().newInstance();
        System.out.println(newInstance.doCalculation(10));
        System.out.println(newInstance.getVersionString());

        closeLoaders(loader);

    }
}
