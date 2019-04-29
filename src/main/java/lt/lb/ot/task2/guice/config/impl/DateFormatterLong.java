/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lt.lb.ot.task2.guice.config.DateFormatter;

/**
 *
 * @author Lemmin
 */
public class DateFormatterLong implements DateFormatter{
    private DateFormat format;
    
    public static AtomicLong count = new AtomicLong(0);

    @Inject
    public DateFormatterLong(@Named("dateSep") String sep){
        count.incrementAndGet();
        format = new SimpleDateFormat("YYYY"+sep+"MM"+sep+"dd HH"+sep+"mm"+sep+"ss");
    }
    @Override
    public String format(Date date) {
        return format.format(date);
    }
}
