/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5.visitables;

import lt.lb.ot.task5.ExplicitVisitor;
import lt.lb.ot.task5.Visitor;

/**
 *
 * @author Lemmin
 */
public interface Visitable {
    public void accept(ExplicitVisitor visitor);
    public default void accept(Visitor vis){
        vis.visit(this);
    }
}





