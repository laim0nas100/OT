/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.spring.config;

import java.io.IOException;
import lt.lb.commons.Log;
import lt.lb.commons.Log.LogStream;
import lt.lb.ot.task2.spring.config.impl.DateFormatterLong;
import lt.lb.ot.task2.spring.config.impl.DateFormatterShort;
import lt.lb.ot.task2.spring.config.impl.OutputFileOut;
import lt.lb.ot.task2.spring.config.impl.OutputSTDOut;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Lemmin
 */
@Configuration
@ComponentScan
public class Config {

    @Primary
    @Bean(name = "short")
    public DateFormatter dateFormatterShort() {
        return new DateFormatterShort();
    }

    @Bean(name = "long")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DateFormatter dateFormatterLong() {
        return new DateFormatterLong("-");
    }

    @Bean(name = "file", autowire = Autowire.BY_TYPE, initMethod = "init", destroyMethod = "denit")
    public OutputPrinter filePrinter() throws IOException {
        OutputFileOut out = new OutputFileOut();
        Log log = new Log();
        log.display = false;
        Log.changeStream(log, LogStream.FILE, "log.txt");
        out.setLog(log);
        return out;
    }

    @Primary
    @Bean(name = "std", autowire = Autowire.BY_TYPE, initMethod = "init", destroyMethod = "denit")
    public OutputPrinter stdPrinter() throws IOException {
        OutputSTDOut out = new OutputSTDOut();
        Log log = new Log();// just use default
        out.setLog(log);
        return out;
    }

}
