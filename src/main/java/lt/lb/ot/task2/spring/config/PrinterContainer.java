/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.spring.config;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lemmin
 */
@Component
public class PrinterContainer {
    
    
    //stub method
    @Lookup
    public OutputPrinter getDefaultPrinter(){
        return null;
    }
}
