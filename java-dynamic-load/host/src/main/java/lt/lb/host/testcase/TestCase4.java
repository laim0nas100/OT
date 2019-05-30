/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host.testcase;

import lt.lb.common.SharedInterface;
import lt.lb.host.Host;
import static lt.lb.host.Host.JAR1_URL;
import static lt.lb.host.Host.JAR1_VER_CLASS;
import static lt.lb.host.Host.JAR2_URL;
import static lt.lb.host.Host.JAR2_VER_CLASS;
import static lt.lb.host.Host.X1_OLD_URL;
import static lt.lb.host.Host.getChildJarLoader;

/**
 *
 * @author Laimonas Beniušis
 */
public class TestCase4 {

    /**
     * Bibliotekos modulis priklasuo kito modulio X. Nuo modulio X, tik kitos
     * versijos priklauso ir pagrindinė aplikacija. Pasiekti, kad pagrindinės
     * aplikacijos klasės ir bibliotekos klasės naudotų joms reikalingas X
     * versijas. Galima taikyti dvi technikas:
     *
     * Įterpti papildomą klasių krovėją, kuris krautų bibliotekos specifines
     * priklausomybes (reikaimos versijos modulį X) tarp bibliotekos klasių
     * krovėjo ir pagrindinės aplikacijos klasių krovėjo; klasių kovėjai veikia
     * standartiniu "parent first" režimu. Sukurti specializuotą URLClassLoader,
     * kad veiktų "parent last" režimu ir naudoti jį bibliotekos bei jai
     * reiklaingos versijos modulio X krovimui
     *
     *
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        System.out.println("TEST CASE 4");
        ClassLoader[] loader = new ClassLoader[]{getChildJarLoader(JAR1_URL), getChildJarLoader(JAR1_URL, X1_OLD_URL), getChildJarLoader(JAR2_URL, X1_OLD_URL), getChildJarLoader(JAR2_URL)};

        for (int i = 0; i < 4; i += 2) {
            String toLoad = i < 2 ? JAR1_VER_CLASS : JAR2_VER_CLASS;
            SharedInterface instance1 = (SharedInterface) loader[i].loadClass(toLoad).newInstance();

            System.out.println("LOADED " + instance1);
//
            Class<?> loadClass = loader[i + 1].loadClass(toLoad);
            SharedInterface instance2 = (SharedInterface) loadClass.newInstance();

            System.out.println("LOADED " + instance2);

            System.out.println(instance1.doCalculation(instance2.doCalculation(10)));
            System.out.println(instance2.doCalculation(instance1.doCalculation(10)));
            System.out.println(instance1.getVersionString() + " " + instance2.getVersionString());
        }

        Host.closeLoaders(loader);

    }
}
