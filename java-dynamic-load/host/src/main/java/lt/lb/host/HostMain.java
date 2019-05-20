/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import lt.lb.common.CommonClass;

/**
 *
 * @author Laimonas Beniušis
 */
public class HostMain {

    private static String prefix = "lt.lb.";
    private static String COMMOM_CLASS = prefix + "common.CommonClass",
            JAR1_COMMON_CLASS = prefix + "jar1.ChildCommonClass1",
            JAR2_COMMON_CLASS = prefix + "jar2.ChildCommonClass2",
            JAR1_SELF_CLASS = prefix + "jar1.ClassJar1",
            JAR2_SELF_CLASS = prefix + "jar2.ClassJar2";

    private static String JAR1_URL = "file:libs/jar1.jar";
    private static String JAR2_URL = "file:libs/jar2.jar";

    private static JarClassLoader getJarLoader(String jarUrl) throws MalformedURLException {
        return new JarClassLoader(new URL[]{new URL(jarUrl)}, HostMain.class.getClassLoader());
    }

    /**
     * test whether {@link CommonClass} in different jar is be loaded from same
     * loader
     */
    private static void testCommonClassIsSame() {

        try {
            JarClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(COMMOM_CLASS);

            JarClassLoader jar2Loader = getJarLoader(JAR2_URL);
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
    private static void testChildCommonClassConvert() {

        try {
            JarClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(JAR1_COMMON_CLASS);
            Method method1 = classJar1.getMethod("getString");
            Object classJar1Obj = classJar1.newInstance();
            System.out.println("string1 before convert:" + method1.invoke(classJar1Obj));

            JarClassLoader jar2Loader = getJarLoader(JAR2_URL);
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
    private static void testJarSelfClass() {

        try {
            JarClassLoader jar1Loader = getJarLoader(JAR1_URL);
            Class<?> classJar1 = jar1Loader.loadClass(JAR1_SELF_CLASS);
            Method method1 = classJar1.getMethod("getString");
            Object classJar1Obj = classJar1.newInstance();
            System.out.println(method1.invoke(classJar1Obj));

            JarClassLoader jar2Loader = getJarLoader(JAR2_URL);
            Class<?> classJar2 = jar2Loader.loadClass(JAR2_SELF_CLASS);
            Method method2 = classJar2.getMethod("getString");
            Object classJar2Obj = classJar2.newInstance();
            System.out.println(method2.invoke(classJar2Obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testCommonClassIsSame();
        System.out.println();
        testChildCommonClassConvert();
        System.out.println();
        testJarSelfClass();
    }
}
