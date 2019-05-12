/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.spring;

import java.util.Date;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.ot.task2.spring.config.Config;
import lt.lb.ot.task2.spring.config.OutputPrinter;
import lt.lb.ot.task2.spring.config.PrinterContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Lemmin
 */
public class Main {
    public static void main(String...args){
        Log.print("Hello");
        
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        PrinterContainer bb = ctx.getBean(PrinterContainer.class);
        
        for(int i = 0; i < 10; i++){
            bb.requestPrinter().printDate(new Date());
        }
//        OutputPrinter bean = ctx.getBean(OutputPrinter.class);
//        Log.print(bean);
//        bean.printDate(new Date());
        Log.print(new Date());
        ConfigurableApplicationContext cctx = F.cast(ctx);
        cctx.close();
    }
}
