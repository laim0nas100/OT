/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot;

import lt.lb.commons.Log;
import lt.lb.commons.containers.LazyValue;

/**
 *
 * @author Lemmin
 */
public class Def {
    public static LazyValue<Void> init = new LazyValue<>(()->{
//        Log.main().surroundString = false;
//        Log.main().threadName = false;
//        Log.main().timeStamp = false;
//        Log.main().stackTrace = false;

//        Log.main().disable = true;
        Log.main().async = false;
        Log.print("INIT DONE");
        return null;
    });
    
    /**
     * 
     * 
     * 
     * 
     */
}




