/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring;

import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.ot.task6.spring.aspects.Cached;
import lt.lb.ot.task6.spring.aspects.Secured;


public class SecuredAction2 implements SecuredMethod {

    @Override
    @Secured
    @Cached
    public void securedAccess(String user, String account, Integer sum) {
        Log.print("Do secured action 2");
        F.unsafeRun(()->Thread.sleep(2500));
    }

}
