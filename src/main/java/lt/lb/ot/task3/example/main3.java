/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import lt.lb.commons.Log;
import lt.lb.ot.Def;
import lt.lb.ot.task3.ctx.ContextImpl;
import lt.lb.ot.task3.example.facade.IDashLog;
import lt.lb.ot.task3.example.facade.IDateLog;
import lt.lb.ot.task3.example.facade.impl.IDateLogImpl;
import lt.lb.ot.task3.example.facade.ILog;
import lt.lb.ot.task3.example.facade.impl.IDashLogImpl;
import lt.lb.ot.task3.example.facade.impl.ILogSystemOut;
import lt.lb.ot.task3.example.facade.impl.MethodInvocationCounter;

/**
 *
 * @author Laimonas Beniušis
 */
public class main3 {

    static ContextImpl ctx = ContextImpl.global;

    public static void main(String[] args) {
        Def.init.get();
//        Log.main().disable = true;

        ctx.registerFactory(IDateLog.class, IDateLogImpl::new);
        ctx.registerFactoryLazy(ILog.class, ILogSystemOut::new);
        ctx.registerFactory(IDashLog.class, IDashLogImpl::new);
        ctx.registerFactory(ContextRunner.class, ContextRunnerImpl::new);
        ctx.registerConcreteSingleton(MethodInvocationCounter.class, MethodInvocationCounter::new);
        ctx.registerConcreteSingleton(DateFormat.class, () -> new SimpleDateFormat("HH:mm:ss"));

        Log.print("Begin");
        for (int i = 0; i < 1; i++) {
            ContextRunner runner = ctx.getOf(ContextRunner.class);
            runner.execute();
        }

        Log.close();
    }
}
