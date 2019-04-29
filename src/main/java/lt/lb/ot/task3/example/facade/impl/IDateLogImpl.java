/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade.impl;

import java.text.DateFormat;
import java.util.Date;
import lt.lb.ot.task3.example.facade.IDateLog;
import lt.lb.ot.task3.example.facade.ILog;

public class IDateLogImpl implements IDateLog {

    private DateFormat format;
    private ILog iLog;

    @Override
    public void setFormatter(DateFormat format) {
        this.format = format;
    }

    @Override
    public void setILog(ILog log) {
        iLog = log;
    }

    @Override
    public void log(Date date) {
        System.out.println(format.format(date));
    }

}
