/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import lt.lb.commons.SafeOpt;
import lt.lb.commons.containers.collections.RelationMap;

/**
 *
 * @author Lemmin
 */
public class ExtendableVisitor implements Visitor {

    /**
     * Relation inheritance map. Root case (Object.class) does nothing, unless such operation was registered. Diamond relations are not permitted
     */
    private RelationMap<Class, List<Consumer>> operations = RelationMap.newTypeMapRootObject(new LinkedList());

    public ExtendableVisitor() {

    }

    @Override
    public void visit(Object ob) {
        Class<? extends Object> cls = SafeOpt.ofNullable(ob).map(m -> m.getClass()).orElse(null);

        if (cls != null) {
            operations.getBestFit(cls).forEach(consumer -> consumer.accept(ob));
        }
    }

    public <T> ExtendableVisitor addOperation(Class<T> cls, Consumer<T> operation) {
        operations.computeIfAbsent(cls, c -> new LinkedList<>()).add(operation);
        return this;
    }
}










