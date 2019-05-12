/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Date;
import lt.lb.commons.Log;
import lt.lb.ot.task2.guice.config.DateFormatter;
import lt.lb.ot.task2.guice.config.OutputPrinter;

/**
 *
 * @author Lemmin
 */
public class OutputFileOut implements OutputPrinter{
    
    @Inject
    @Named("logFile")
    protected Log log;
    
    
    @Inject
    @Named("long")
    private DateFormatter dateFormat;

    public DateFormatter getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    
    @Override
    public void printDouble(Double d) {
        Log.print(log, d);
    }

    @Override
    public void printInt(Integer i) {
        Log.print(log, i);
    }

    @Override
    public void printString(String str) {
        Log.print(log, str);
    }

    @Override
    public void printDate(Date date) {
        Log.print(log, dateFormat.format(date));
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
    
    public void init(){
        Log.print("INIT",getClass());
    }
    
    public void denit(){
        Log.print("Close",getClass());
    }
    
    
}
