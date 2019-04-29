/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Laimonas Beniušis
 */
public class MethodDescriptor {

    public MethodDescriptor(Method method) {
        this(method.getName(), method.getParameterTypes());
    }

    public MethodDescriptor(String name, Class[] argTypes) {
        this.name = name;
        this.argTypes = argTypes;
    }

    public final String name;
    public final Class[] argTypes;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodDescriptor other = (MethodDescriptor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.argTypes, other.argTypes)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Arrays.deepHashCode(this.argTypes);
        return hash;
    }

}
