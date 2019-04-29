/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade;

import lt.lb.ot.task3.ctx.Delegate;

/**
 *
 * @author Laimonas Beniušis
 */
@Delegate(on = IDateLog.class, by = IDateLog.class)
public interface ILog {
    
    public void log(String str);
    
    public void setIDateLog(IDateLog log);
    
}
