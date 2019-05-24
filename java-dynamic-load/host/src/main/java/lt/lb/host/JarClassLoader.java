/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.host;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Laimonas Beniušis
 */
public class JarClassLoader extends URLClassLoader {

    public static String PREFIX_COMMON_CLASS = "lt.lb.common";
    
    public JarClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    // common lib class loader to load common class
    private static ClassLoader COMMON_LIB_LOADER;

    public static ClassLoader getCommonLibLoader() {
        if (COMMON_LIB_LOADER == null) {
            synchronized (JarClassLoader.class) {
                if (COMMON_LIB_LOADER == null) {
                    COMMON_LIB_LOADER = JarClassLoader.class.getClassLoader();
                }
            }
        }
        return COMMON_LIB_LOADER;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isLibClass(String className) {
        return (className != null && className.startsWith(PREFIX_COMMON_CLASS));
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        if (isLibClass(className)) {
            try {
                Class<?> clazz = JarClassLoader.getCommonLibLoader().loadClass(className);
                if (clazz != null) {
                    return clazz;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.loadClass(className, resolve);
    }
    
    
}
