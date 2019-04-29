/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Lemmin
 */
public interface ReflectiveVisitor extends Visitor {

    @Override
    public default void visit(Object ob) {
        Class<? extends ReflectiveVisitor> aClass = this.getClass();
        Class<? extends Object> type = ob.getClass();
        Method method;
        boolean ok = false;
        try {
            method = aClass.getDeclaredMethod("visit", type);
            method.invoke(this, ob);
            ok = true;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

        }
        if (ok) {
            return;
        }
        try {
            method = aClass.getMethod("visit", type);
            method.invoke(this, ob);
            ok = true;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

        }

    }

}

