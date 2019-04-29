/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task3.ctx;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Laimonas Beniušis
 */
class DecorateInfo {

    List<Runnable> before = new LinkedList<>();
    List<Runnable> after = new LinkedList<>();

    public boolean isEmpty() {
        return before.isEmpty() && after.isEmpty();
    }
}
