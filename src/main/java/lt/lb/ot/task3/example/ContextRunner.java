/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example;

import lt.lb.ot.task3.example.facade.ILog;

/**
 *
 * @author Laimonas Beniušis
 */
public interface ContextRunner {
    
    public void setILog(ILog logger);
    
    public void execute();
    
    
}
