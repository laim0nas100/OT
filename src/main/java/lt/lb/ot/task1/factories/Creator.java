/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1.factories;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;
import lt.lb.commons.F;

/**
 *
 * @author Lemmin
 */
public interface Creator<T> extends Supplier<T> {

    public T create(Object... params);

    public default T get() {
        return create();
    }

    public static <T> Creator<T> genericCreator(Class<T> cls, Class... types) {
        return (args) -> {
            return F.unsafeCall(() -> {
                Constructor<T> declaredConstructor = cls.getDeclaredConstructor(types);
                return declaredConstructor.newInstance(args);
            });
        };
    }
}

