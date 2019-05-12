/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config;

import com.google.inject.PrivateModule;
import java.lang.annotation.Annotation;

/**
 *
 * @author Lemmin
 */
public abstract class LogModule extends PrivateModule{
    
    private Annotation annotation;

    public LogModule(Annotation ann){
        this.annotation = ann;
    }
    
    @Override
    protected void configure() {
        
        bind(OutputPrinter.class).annotatedWith(annotation).to(OutputPrinter.class);
        expose(OutputPrinter.class).annotatedWith(annotation);
        this.bindConcreteLog();
    }
    
    abstract void bindConcreteLog();
    
}
