/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host;

import java.lang.reflect.Method;
import lt.lb.common.CommonClass;
import static lt.lb.host.Host.*;

/**
 *
 * @author Laimonas Beniušis
 */
public class OtherTests {
     /**
     * test whether {@link CommonClass} in different jar is be loaded from same
     * loader
     */
    public static void testCommonClassIsSame() {

        try {
            ClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(COMMOM_CLASS);

            ClassLoader jar2Loader = getJarLoader(JAR2_URL);
            Class<?> classJar2 = jar2Loader.loadClass(COMMOM_CLASS);

            if (jar1Loader.equals(jar2Loader)) {
                System.out.println("common class jar loader equals");
            } else {
                System.out.println("common class jar loader not equals");
            }
            if (classJar1.equals(classJar2)) {
                System.out.println("common class equals");
            } else {
                System.out.println("common class not equals");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test child common class can be converted to {@link CommonClass} directly
     */
    public static void testChildCommonClassConvert() {

        try {
            ClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(JAR1_COMMON_CLASS);
            Method method1 = classJar1.getMethod("getString");
            Object classJar1Obj = classJar1.newInstance();
            System.out.println("string1 before convert:" + method1.invoke(classJar1Obj));

            ClassLoader jar2Loader = getJarLoader(JAR2_URL);
            Class<?> classJar2 = jar2Loader.loadClass(JAR2_COMMON_CLASS);
            Method method2 = classJar2.getMethod("getString");
            Object classJar2Obj = classJar2.newInstance();
            System.out.println("string2 before convert:" + method2.invoke(classJar2Obj));

            CommonClass commonClass1 = (CommonClass) classJar1Obj;
            System.out.println("string1 after convert:" + commonClass1.getString());

            CommonClass commonClass2 = (CommonClass) classJar2Obj;
            System.out.println("string2 after convert:" + commonClass2.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test class contain {@link CommonClass} in different jar
     */
    public static void testJarSelfClass() {

        try {
            ClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(JAR1_SELF_CLASS);
            Method method1 = classJar1.getMethod("getString");
            Object classJar1Obj = classJar1.newInstance();
            System.out.println(method1.invoke(classJar1Obj));

            ClassLoader jar2Loader = getJarLoader(JAR2_URL);
            Class<?> classJar2 = jar2Loader.loadClass(JAR2_SELF_CLASS);
            Method method2 = classJar2.getMethod("getString");
            Object classJar2Obj = classJar2.newInstance();
            System.out.println(method2.invoke(classJar2Obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
