/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example;

import java.util.Date;
import lt.lb.commons.F;
import lt.lb.ot.task3.ctx.Decorate;
import lt.lb.ot.task3.example.facade.IDateLog;
import lt.lb.ot.task3.example.facade.ILog;
import lt.lb.ot.task3.example.facade.impl.MethodInvocationCounter;

@Decorate(clazz = MethodInvocationCounter.class, beforeMethod = {"start"}, afterMethod = {"end","end"})
public class ContextRunnerImpl implements ContextRunner {

    private ILog log;

    @Override
    public void execute() {

        log.log("New String line");
        log.log("The following is date line");
        
        IDateLog dlog = F.cast(log);
        dlog.log(new Date());
    }

    @Override
    public void setILog(ILog logger) {
        log = logger;
    }

}
