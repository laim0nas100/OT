/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.example.facade.impl;

import java.util.Date;
import lt.lb.ot.task3.example.facade.IDashLog;


public class IDashLogImpl implements IDashLog {

    
    public IDashLogImpl(){
        System.out.println("INIT DASH LOG");
    }
    @Override
    public void log(String str) {
        System.out.println("--"+str+"---");
    }

    @Override
    public void log(Date date) {
        System.out.println("--"+date+"---");
    }
    
}
