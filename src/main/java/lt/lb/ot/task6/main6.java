/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6;

import java.util.Date;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.ot.Def;
import lt.lb.ot.task6.spring.Config;
import lt.lb.ot.task6.spring.PrinterContainer;
import lt.lb.ot.task6.spring.SecuredMethod;
import lt.lb.ot.task6.spring.aspects.Countable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Lemmin
 */
public class main6 {

    public static void main(String[] args) {
        Def.init.get();

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        PrinterContainer bb = ctx.getBean(PrinterContainer.class);

        for (int i = 0; i < 10; i++) {
            bb.requestPrinter().printDate(new Date());
        }
        SecuredMethod action = ctx.getBean("act2", SecuredMethod.class);
        if(action instanceof Countable){
            Countable c = F.cast(action);
            c.inc();
            Log.print("Count:",c.getCount());
        }
        for (int i = 0; i < 3; i++) {
            action.securedAccess("admin", "1337", 10);
        }
        
//        OutputPrinter bean = ctx.getBean(OutputPrinter.class);
//        Log.print(bean);
//        bean.printDate(new Date());
        Log.print(new Date());
        ConfigurableApplicationContext cctx = F.cast(ctx);
        cctx.close();
//        Log.close();
    }
}
