/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import lt.lb.commons.Log;
import lt.lb.ot.task5.visitables.Book;
import lt.lb.ot.task5.visitables.Cup;
import lt.lb.ot.task5.visitables.Phone;

/**
 *
 * @author Laimonas Beniušis
 */
public class ReflectivePrintVisitor implements ReflectiveVisitor {

    void visit(Book book) {
        Log.print("REF", "Visit book");
    }

    void visit(Cup cup) {
        Log.print("REF", "Visit cup");
    }

    void visit(Phone phone) {
        Log.print("REF", "Visit phone");
    }
}


