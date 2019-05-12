/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task5;

import java.util.Arrays;
import java.util.Collections;
import lt.lb.commons.ArrayOp;
import lt.lb.commons.Log;
import lt.lb.ot.Def;
import lt.lb.ot.task5.visitables.Book;
import lt.lb.ot.task5.visitables.Cup;
import lt.lb.ot.task5.visitables.Pen;
import lt.lb.ot.task5.visitables.Phone;
import lt.lb.ot.task5.visitables.Visitable;

/**
 *
 * @author Lemmin
 */
public class main5 {

    public static void main(String... args) throws Exception {
        Def.init.get();
        Log.main().disable = false;
        //populate an array of items
        Visitable[] merge = ArrayOp.replicate(4, Visitable.class, Book::new, Cup::new, Pen::new);
        Collections.shuffle(Arrays.asList(merge));
        //Phone is not registered for print

        Log.print("\nSimple case\n");
        ExplicitVisitor visitor = new PrintVisitor();
        for (Object v : merge) {
            Visitors.acceptVisitor(v, visitor);
        }

        Log.print("\nreflective case\n");
        Visitor printReflectiveVisitor = Visitor.printReflectiveVisitor();
        for (Visitable v : merge) {
            v.accept(printReflectiveVisitor);
        }
        Log.print("\nextendable case\n");
        ExtendableVisitor ext = new ExtendableVisitor();
        ext.addOperation(Object.class, b -> Log.print("EXT", "Unrecognized", b));
        ext.addOperation(Visitable.class, b -> Log.print("EXT", "Visiting unregistered visitable", b));
        ext.addOperation(Book.class, b -> Log.print("EXT", "Visiting book"));
        ext.addOperation(Cup.class, b -> Log.print("EXT", "Visiting Cup"));
        ext.addOperation(Cup.class, b -> Log.print("EXT", "Visiting Cup again"));

        for (Visitable v : merge) {
//            ext.visit(v);
            v.accept(ext);
        }

        Log.print("\n\n\n\n DynamicVisitor");

        
        
        //(predator, prey)
        //(lion, rabbit)
        
        
        //lion, prey
        
        Visitor dynamicVisitor = Visitors.createDynamicVisitor(ArrayOp.asArray(ExplicitVisitor.class), ArrayOp.asArray(new PrintVisitor()), ext);
        for (Object v : merge) {
            Visitors.acceptVisitor(v, dynamicVisitor);
        }
//        Visitor.combine(printReflectiveVisitor, ext);
        Log.close();
    }
}
