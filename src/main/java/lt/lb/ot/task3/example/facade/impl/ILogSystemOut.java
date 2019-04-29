/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade.impl;

import lt.lb.ot.task3.example.facade.IDateLog;
import lt.lb.ot.task3.example.facade.ILog;

public class ILogSystemOut implements ILog {

    private IDateLog iDateLog;
    @Override
    public void setIDateLog(IDateLog log) {
        iDateLog = log;
    }

    
    @Override
    public void log(String str) {
        System.out.println(str);
    }

}
