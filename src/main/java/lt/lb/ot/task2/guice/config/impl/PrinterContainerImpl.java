/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import lt.lb.ot.task2.guice.config.OutputPrinter;
import lt.lb.ot.task2.guice.config.PrinterContainer;

public class PrinterContainerImpl implements PrinterContainer {

    @Inject
    @Named("std")
    private Provider<OutputPrinter> printer;

    @Override
    public OutputPrinter getDefaultPrinter() {
        return printer.get();
    }

}
