/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lemmin
 */
@Component
@Aspect
public class CountIntroducer {
    @DeclareParents(value = "lt.lb.ot.task6.spring.SecuredMethod+", defaultImpl = AtomicCountable.class)
    public static Countable countable;
}
