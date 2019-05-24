/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Stream;
import lt.lb.common.CommonClass;
import lt.lb.common.SharedInterface;

/**
 *
 * @author Laimonas Beniušis
 */
public class HostMain {

    private static String prefix = "lt.lb.";
    private static String COMMOM_CLASS = prefix + "common.CommonClass";
    private static String JAR1_COMMON_CLASS = prefix + "jar1.ChildCommonClass1";
    private static String JAR2_COMMON_CLASS = prefix + "jar2.ChildCommonClass2";
    private static String JAR1_IMP_CLASS = prefix + "jar1.ChildImp1";
    private static String JAR2_IMP_CLASS = prefix + "jar2.ChildImp2";
    private static String JAR1_VER_CLASS = prefix + "jar1.VerDep1";
    private static String JAR2_VER_CLASS = prefix + "jar2.VerDep2";
    private static String JAR1_SELF_CLASS = prefix + "jar1.ClassJar1";
    private static String JAR2_SELF_CLASS = prefix + "jar2.ClassJar2";

    private static String JAR1_URL = "file:libs/jar1.jar";
    private static String JAR2_URL = "file:libs/jar2.jar";
    private static String X1_OLD_URL = "file:libs/x1-old.jar";
    private static String X1_CURRENT_URL = "file:libs/x1-current.jar";

    private static ClassLoader getJarLoader(String... jarUrl) throws MalformedURLException {
//        return new JarClassLoader(urls(jarUrl), HostMain.class.getClassLoader());
        return new MyClassLoader(urls(jarUrl), HostMain.class.getClassLoader());
    }
    
    private static URL toUrl(String s){
        try{
            return new URL(s);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private static URL[] urls(String...urls){
        return Stream.of(urls)
                .map(m -> toUrl(m))
                .toArray(s -> new URL[s]);
    }

    private static ParentLastURLClassLoader getChildJarLoader(String... jarUrl) throws MalformedURLException {
        return new ParentLastURLClassLoader(urls(jarUrl));
    }

    private static ParentLastClassLoader getChildJarLoader1(String... jarUrl) throws MalformedURLException, URISyntaxException, IOException {
        return new ParentLastClassLoader(Thread.currentThread().getContextClassLoader(),urls(jarUrl));
    }

    /**
     * test whether {@link CommonClass} in different jar is be loaded from same
     * loader
     */
    private static void testCommonClassIsSame() {

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
    private static void testChildCommonClassConvert() {

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
    private static void testJarSelfClass() {

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

    public static void main(String[] args) throws Exception {
//        testCommonClassIsSame();
//        System.out.println();
//        testChildCommonClassConvert();
//        System.out.println();
//        testJarSelfClass();

//        testCase1();
//        testCase2();
//        testCase3();
        testCase4();
    }

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
    public static void testCase1() throws Exception {
        System.out.println("\n\n\n\nTEST CASE 1");
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

    /**
     * Aplikacija dinamiškai atnaujina bibliotekos modulį ir naudojamą
     * objektą.(Nurodymas: pakrauti modulį iš naujo kitame klasių krovėjo
     * egzemplioriuje; senas objektas turi būti "pamirštamas", o senas
     * URLClassLoader uždaromas).
     *
     * @throws Exception
     */
    public static void testCase2() throws Exception {
        System.out.println("\n\n\n\nTEST CASE 2");
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

    /**
     * Aplikacija pakrauna ir naudoja dvi bibliotejos modulio versijas vienu
     * metu (Nurodymas: Naudoti atskirus klasių krovėjus kiekvienai bibliotekos
     * versijai.)
     *
     * @throws Exception
     */
    public static void testCase3() throws Exception {
        System.out.println("\n\n\n\nTEST CASE 3");
        ClassLoader[] loader = new ClassLoader[]{getJarLoader(JAR1_URL), getJarLoader(JAR2_URL)};

        SharedInterface instance1 = (SharedInterface) loader[0].loadClass(JAR1_IMP_CLASS).newInstance();

        SharedInterface instance2 = (SharedInterface) loader[1].loadClass(JAR2_IMP_CLASS).newInstance();

        System.out.println("Both are used");

        System.out.println(instance1.doCalculation(instance2.doCalculation(10)));
        System.out.println(instance2.doCalculation(instance1.doCalculation(10)));
        System.out.println(instance1.getVersionString() + " " + instance2.getVersionString());

        closeLoaders(loader);
    }

    /**
     * Aplikacija pakrauna ir naudoja dvi bibliotejos modulio versijas vienu
     * metu (Nurodymas: Naudoti atskirus klasių krovėjus kiekvienai bibliotekos
     * versijai.)
     *
     * @throws Exception
     */
    public static void testCase4() throws Exception {
        System.out.println("\n\n\n\nTEST CASE 4");
        ClassLoader[] loader = new ClassLoader[]{getChildJarLoader(JAR1_URL), getChildJarLoader(JAR2_URL, X1_OLD_URL)};

        SharedInterface instance1 = (SharedInterface) loader[0].loadClass(JAR1_VER_CLASS).newInstance();

        System.out.println("LOADED " + instance1);
//
        Class<?> loadClass = loader[1].loadClass(JAR2_VER_CLASS);
        SharedInterface instance2 = (SharedInterface) loadClass.newInstance();

        System.out.println("LOADED " + instance2);
        System.out.println("Both are used");

        System.out.println(instance1.doCalculation(instance2.doCalculation(10)));
        System.out.println(instance2.doCalculation(instance1.doCalculation(10)));
        System.out.println(instance1.getVersionString() + " " + instance2.getVersionString());

    }
    
    public static void closeLoaders(ClassLoader...loaders) throws IOException{
        for(ClassLoader loader:loaders){
            if(loader instanceof Closeable){
                ((Closeable) loader).close();
            }
        }
    }
}
