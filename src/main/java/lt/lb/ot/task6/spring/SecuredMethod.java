/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring;

/**
 *
 * @author Lemmin
 */
public interface SecuredMethod {
    public void securedAccess(String user, String account, Integer sum);
}
