/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.util.Date;
import lt.lb.commons.Log;
import lt.lb.ot.task2.guice.config.BasicModule;
import lt.lb.ot.task2.guice.config.OutputPrinter;
import lt.lb.ot.task2.guice.config.PrinterContainer;

/**
 *
 * @author Lemmin
 */
public class Main {
    public static void main(String... args){
        Log.print("Hello");
        Injector inj = Guice.createInjector(new BasicModule());
        
        
        
        
        PrinterContainer bb = inj.getInstance(PrinterContainer.class);
        bb.getDefaultPrinter().printDouble(12d);
        bb.getDefaultPrinter().printDate(new Date());
//        OutputPrinter bean = inj.getInstance(Key.get(OutputPrinter.class,Names.named("std")));
//        Log.print(bean);
//        bean.printDate(new Date());
        Log.print(new Date());
        
        Log.close(inj.getInstance(Key.get(Log.class, Names.named("logStd"))));
        Log.close(inj.getInstance(Key.get(Log.class, Names.named("logFile"))));
        Log.close();
    }
}
