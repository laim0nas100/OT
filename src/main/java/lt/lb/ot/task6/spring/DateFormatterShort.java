/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lt.lb.ot.task6.spring.aspects.Loggable;

/**
 *
 * @author Lemmin
 */
public class DateFormatterShort implements DateFormatter {

    private DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

    @Override
    @Loggable
    public String format(Date date) {
        return format.format(date);
    }

}
