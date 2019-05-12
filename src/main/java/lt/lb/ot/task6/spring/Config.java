/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring;

import java.io.IOException;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.Log.LogStream;
import lt.lb.ot.task6.spring.aspects.CountIntroducer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Lemmin
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class Config {

//    @Autowired
//    @Qualifier("short")
//    DateFormatter df;

    @Bean(name = "long")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DateFormatter dateFormatterLong() {
        return new DateFormatterLong("-");
    }

    public PrinterContainer pc() {
        return new PrinterContainer() {
            @Override
            public OutputPrinter requestPrinter() {
                return F.unsafeCall(() -> filePrinter());
            }
        };
    }

    //@Qualifier("name")
    @Bean(name = "file", autowire = Autowire.BY_NAME, initMethod = "init", destroyMethod = "denit")
    public OutputPrinter filePrinter() throws IOException {
        OutputFileOut out = new OutputFileOut();
        out.setLog(this.logFile());
        return out;
    }

    @Bean
    @Scope("singleton")//default
    @Qualifier("logFile")
    public Log logFile() throws IOException {
        Log log = new Log();
        log.display = false;
        Log.changeStream(log, LogStream.FILE, "log.txt");
        return log;
    }
    
    @Bean
    @Scope("singleton")//default
    @Qualifier("logSTD")
    public Log logSTD() throws IOException {
        Log log = new Log();
        Log.changeStream(log, LogStream.STD_OUT);
        return log;
    }

    @Bean(name = "fileShort")
    public OutputPrinter fileShortPrinter(@Qualifier("short") DateFormatter formatter, @Qualifier("logFile") Log log) throws IOException {
        OutputFileOut out = new OutputFileOut();
        out.setLog(log);
        out.setDateFormat(formatter);
        return out;
    }

    @Bean(name = "fileLong")
    public OutputPrinter fileLongPrinter(@Qualifier("long") DateFormatter formatter, @Qualifier("logFile") Log log) throws IOException {
        OutputFileOut out = new OutputFileOut();
        out.setLog(log);
        out.setDateFormat(formatter);
        return out;
    }

    @Bean
    @Qualifier("short")
    public FactoryBean<DateFormatter> getFactoryBean() {
        return new FactoryBean<DateFormatter>() {
            @Override
            public DateFormatter getObject() throws Exception {
                return new DateFormatterShort();
            }

            @Override
            public Class<?> getObjectType() {
                return DateFormatter.class;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }
        };
    }

//    @Bean(name = "file1", initMethod = "init", destroyMethod = "denit")
//    public OutputPrinter filePrinter1(@Qualifier("short") DateFormatter form) throws IOException {
//        OutputFileOut out = new OutputFileOut();
//        out.setDateFormat(form);
//        out.setLog(this.logFile());
//        return out;
//    }
//
    @Primary
    @Bean(name = "std", autowire = Autowire.BY_NAME, initMethod = "init", destroyMethod = "denit")
    public OutputPrinter stdPrinter() throws IOException {
        OutputSTDOut out = new OutputSTDOut();
        out.setLog(this.logSTD());
        return out;
    }
    
    @Bean(name = "act1")
    public SecuredMethod act1(){
        return new SecuredAction1();
    }
    
    @Bean(name = "act2")
    public SecuredMethod act2(){
        return new SecuredAction2();
    }
    
    @Bean
    public CountIntroducer intrpduceCount(){
        return new CountIntroducer();
    }

}
