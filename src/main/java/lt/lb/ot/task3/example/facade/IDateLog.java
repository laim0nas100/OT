/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Laimonas Beniušis
 */
public interface IDateLog {
    
    public void setFormatter(DateFormat format);
    
    public void log(Date date);
    
    public void setILog(ILog log);
    
}
