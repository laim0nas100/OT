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
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
    
    

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
    
    

}
