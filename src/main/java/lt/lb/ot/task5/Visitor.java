/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lt.lb.commons.ArrayOp;

/**
 *
 * @author Lemmin
 */
public interface Visitor {
    public void visit(Object ob);
    
    //combine several Visitors to make one objects
    public static Visitor combine(Visitor first, Visitor... rest) {
        return (Visitor) Proxy.newProxyInstance(Visitor.class.getClassLoader(), ArrayOp.asArray(Visitor.class), (Object proxy, Method method, Object[] args) -> {
            Object res = method.invoke(first, args);
            for (Visitor r : rest) {
                res = method.invoke(r, args);
            }
            return res;
        });
    }

    public static ExplicitVisitor printVisitor() {
        return new PrintVisitor();
    }
    
    public static Visitor printReflectiveVisitor(){
        return new ReflectivePrintVisitor();
    }
}













