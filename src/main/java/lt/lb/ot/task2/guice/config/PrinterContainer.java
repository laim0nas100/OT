/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import lt.lb.ot.task2.guice.config.impl.PrinterContainerImpl;



/**
 *
 * @author Lemmin
 */
@ImplementedBy(PrinterContainerImpl.class)
public interface PrinterContainer {
    
    
    //stub method
    public OutputPrinter getDefaultPrinter();
}
