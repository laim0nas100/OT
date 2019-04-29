/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import lt.lb.ot.task5.visitables.Book;
import lt.lb.ot.task5.visitables.Cup;
import lt.lb.ot.task5.visitables.Pen;

/**
 *
 * @author Lemmin
 */
public interface ExplicitVisitor extends Visitor {

    public void visit(Book book);

    public void visit(Cup cup);
    
    public void visit(Pen pen);

}







