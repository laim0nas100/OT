/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import lt.lb.commons.Log;
import lt.lb.commons.SafeOpt;
import lt.lb.ot.task5.visitables.Book;
import lt.lb.ot.task5.visitables.Cup;
import lt.lb.ot.task5.visitables.Pen;

/**
 *
 * @author Lemmin
 */
public class PrintVisitor implements ExplicitVisitor {

    @Override
    public void visit(Book book) {
        Log.print("Visiting book");
    }

    @Override
    public void visit(Cup cup) {
        Log.print("Vising cup");
    }

    @Override
    public void visit(Object ob) {
        Log.print("Visiting object: " + SafeOpt.ofNullable(ob).map(m -> m.getClass().getSimpleName()).orElse("null"));
    }

    @Override
    public void visit(Pen pen) {
        Log.print("Visiting pen");
    }

}


