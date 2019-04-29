/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;
import lt.lb.commons.F;
import lt.lb.ot.task1.Product;

/**
 *
 * @author Lemmin
 */
public class ClassContructorFactory<T extends Product> implements Supplier<T> {

    private Constructor<T> con;

    public ClassContructorFactory(Class<T> cls) {
        con = F.unsafeCall(cls::getDeclaredConstructor);    // get default constructor with suppressed and wrapped excpetion
    }

    @Override
    public T get() {
        return F.unsafeCall(con::newInstance);
    }

}


