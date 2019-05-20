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

        SecuredMethod action1 = ctx.getBean("act1", SecuredMethod.class);
        if (action1 instanceof Countable) {
            Countable c = F.cast(action1);
            c.inc();
            Log.print("Count:", c.getCount());
        }
        for (int i = 0; i < 3; i++) {
            action1.securedAccess("admin", "1337", 10);
            action1.securedAccess("adminas", "7331", 12);
        }

        SecuredMethod action2 = ctx.getBean("act2", SecuredMethod.class);
        if (action2 instanceof Countable) {
            Countable c = F.cast(action2);
            c.inc();
            Log.print("Count:", c.getCount());
        }
        for (int i = 0; i < 3; i++) {
            action2.securedAccess("admin", "1337", 10);
            action2.securedAccess("adminas", "7331", 12);
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
