/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Stream;
import lt.lb.host.testcase.TestCase1;
import lt.lb.host.testcase.TestCase2;
import lt.lb.host.testcase.TestCase3;
import lt.lb.host.testcase.TestCase4;

/**
 *
 * @author Laimonas Beniušis
 */
public class Host {

    public static String prefix = "lt.lb.";
    public static String COMMOM_CLASS = prefix + "common.CommonClass";
    public static String JAR1_COMMON_CLASS = prefix + "jar1.ChildCommonClass1";
    public static String JAR2_COMMON_CLASS = prefix + "jar2.ChildCommonClass2";
    public static String JAR1_IMP_CLASS = prefix + "jar1.ChildImp1";
    public static String JAR2_IMP_CLASS = prefix + "jar2.ChildImp2";
    public static String JAR1_VER_CLASS = prefix + "jar1.VerDep1";
    public static String JAR2_VER_CLASS = prefix + "jar2.VerDep2";
    public static String JAR1_SELF_CLASS = prefix + "jar1.ClassJar1";
    public static String JAR2_SELF_CLASS = prefix + "jar2.ClassJar2";

    public static String JAR1_URL = "file:libs/jar1.jar";
    public static String JAR2_URL = "file:libs/jar2.jar";
    public static String X1_OLD_URL = "file:libs/x1-old.jar";
    public static String X1_CURRENT_URL = "file:libs/x1-current.jar";

    public static ClassLoader getJarLoader(String... jarUrl) throws MalformedURLException {
//        return new JarClassLoader(urls(jarUrl), HostMain.class.getClassLoader());
        return new MyClassLoader(urls(jarUrl), Host.class.getClassLoader());
    }

    private static URL toUrl(String s) {
        try {
            return new URL(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static URL[] urls(String... urls) {
        return Stream.of(urls)
                .map(m -> toUrl(m))
                .toArray(s -> new URL[s]);
    }

    public static ParentLastURLClassLoader getChildJarLoader(String... jarUrl) throws MalformedURLException {
        return new ParentLastURLClassLoader(urls(jarUrl));
    }

    public static ParentLastClassLoader getChildJarLoader1(String... jarUrl) throws MalformedURLException, URISyntaxException, IOException {
        return new ParentLastClassLoader(Thread.currentThread().getContextClassLoader(), urls(jarUrl));
    }

   

    public static void main(String[] args) throws Exception {
        System.out.println("RUN ALL");
        TestCase1.main(args);
        TestCase2.main(args);
        TestCase3.main(args);
        TestCase4.main(args);
    }

    

    public static void closeLoaders(ClassLoader... loaders) throws IOException {
        for (ClassLoader loader : loaders) {
            if (loader instanceof Closeable) {
                ((Closeable) loader).close();
            }
        }
    }
}
