/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task1;

import java.util.function.Supplier;
import java.util.stream.Stream;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.SafeOpt;
import lt.lb.commons.iteration.ReadOnlyIterator;
import lt.lb.ot.Def;
import lt.lb.ot.task1.factories.ClassContructorFactory;
import lt.lb.ot.task1.factories.Creator;
import lt.lb.ot.task1.factories.CreatorFactory;
import lt.lb.ot.task1.factories.ExplicitChairFactory;
import lt.lb.ot.task1.furniture.Bed;
import lt.lb.ot.task1.furniture.Chair;
import lt.lb.ot.task1.furniture.Table;
import lt.lb.ot.task1.tools.Drill;
import lt.lb.ot.task1.tools.ExpensiveScrewdriver;
import lt.lb.ot.task1.tools.Hammer;
import lt.lb.ot.task1.tools.Screwdriver;

/**
 *
 * @author Lemmin
 */
public class main1 {
    
    public static void main(String... args) {
        Def.init.get();
        
        
        //Initialization
        SafeOpt<String[]> arg = SafeOpt.ofNullable(args);
        
        Log.print(args);
        Class cls;
        SafeOpt<Class<?>> map = arg.map(m -> m[0]).map(cl -> main1.class.getClassLoader().loadClass(cl));
        if (!map.isPresent() && map.getError().isPresent()) {
            Log.print(map.getError().get().getCause());
            cls = Chair.class;
        } else {
            cls = map.get();
        }
        
        Supplier<? extends Product> productFactory = null;
        
        if (F.instanceOf(cls, Chair.class)) {
            productFactory = new ExplicitChairFactory();
        } else if (F.instanceOf(cls, Bed.class, Table.class)) {
            productFactory = new ClassContructorFactory<>(cls);
        } else if (F.instanceOf(cls, Tool.class)) {
            
            if (F.instanceOf(cls, Drill.class, Hammer.class)) {
                Creator genericCreator = Creator.genericCreator(cls);
                productFactory = new CreatorFactory<>(genericCreator);
            } else {
                //concrete price
                productFactory = new CreatorFactory<>(Screwdriver.screwdriverCreator, 31d);
            }
            
        }

        //Client case
        ProductBasket basket = new ProductBasket(10, productFactory);
        printBasket(basket);
        
        Log.close();
    }
    
    public static void printBasket(ProductBasket basket) {
        Stream<Product> of = Stream.of(basket.getProducts());
        double fullPrice = of.mapToDouble(p -> p.getPrice()).sum();
        Log.printLines(ReadOnlyIterator.of(Stream.of(basket.getProducts()).map(m -> m.getClass().getSimpleName())));
        Log.print("Item count:" + basket.getProducts().length + " Price:" + fullPrice);
    }
    
}



