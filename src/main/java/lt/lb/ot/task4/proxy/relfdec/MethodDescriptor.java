/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task4.proxy.relfdec;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Laimonas Beniušis
 */
public class MethodDescriptor {

    public MethodDescriptor(Method method) {
        this(method.getName(), method.getParameterTypes(), method.getReturnType());
    }

    public MethodDescriptor(String name, Class[] argTypes, Class returnType) {
        this.name = name;
        this.argTypes = argTypes;
        this.returnType = returnType;
    }

    public final Class returnType;
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
        if (!Objects.equals(this.returnType, other.returnType)) {
            return false;
        }
        if (!Arrays.deepEquals(this.argTypes, other.argTypes)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.returnType);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Arrays.deepHashCode(this.argTypes);
        return hash;
    }

    @Override
    public String toString() {
        List<String> collect = Stream.of(argTypes).map(m->m.getSimpleName()).collect(Collectors.toList());
        return "MethodDescriptor{" + "returnType=" + returnType.getSimpleName() + ", name=" + name + ", argTypes=" + collect + '}';
    }


}
