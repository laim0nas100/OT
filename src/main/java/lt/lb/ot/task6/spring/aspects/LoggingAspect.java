/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import lt.lb.commons.Log;
import lt.lb.commons.Timer;
import lt.lb.ot.task6.spring.OutputPrinter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lemmin
 */

/*
        Prieš vykdymą aspektas turi išvesti į failą vykdomo metodo pavadinimą ir sistemos laiką,
        po metodo vykdymo - metodo pavadinimą ir sistemos laiką ir trukmę milisekundėmis, kiek laiko užtruko metodo vykdymas. 
 */
@Aspect
@Component
public class LoggingAspect {

    @Autowired
    @Qualifier("fileLong")
    private OutputPrinter printer;

    @Around("loggableMethods()")
    public void logAround(ProceedingJoinPoint jp) throws Throwable {
        printer.printString("Running method " + jp.getSignature().getName() + " @" + systemTime());
        Timer t = new Timer();
        jp.proceed();
        long timeMillis = t.stopMillis();
        printer.printString("Done running method:" + jp.getSignature().getName() + " @ " + systemTime() + " took:" + timeMillis + "(ms)");

    }

    private String systemTime() {
        return Log.getZonedDateTime("HH:mm:ss.SSS");
    }
    
    @Pointcut("@annotation(Loggable)")
    public void loggableMethods(){
        
    }

}
