/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task6.spring.aspects;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;
import lt.lb.commons.Log;
import lt.lb.commons.containers.tuples.Tuple3;
import lt.lb.commons.containers.tuples.Tuples;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lemmin
 */

/*
 * Sukurti prieigos kontrolės aspektą.
    Aspektas turi būti pritaikomas metodams su trim argumentais: String (naudotojo vardas), String (sąskaitos Nr.) ir int (suma).
    Aspektas pagal jo konfigūracijoje nusstatomas taisykles prieš metodo iškvietimą patikrina, ar veiksmas gali būti vykdomas.
    Jei ne, aspektas turi generuoti išimtį ir nevykdyti adaptuojamo metodo. (Panaudokite "before advice"). 
 */
@Aspect
@Component
public class ControlAspect {

    Predicate<Tuple3<String, String, Integer>> predicate = t -> true;

    @Before("securedMethods() && args(user,account,sum)")
    public void checkAccess(JoinPoint jp, String user, String account, Integer sum) throws Throwable {
        if (!allowed(user, account, sum)) {
            throw new IllegalAccessException("Not allowed access " + account + " with " + user);
        }
    }
    
    @Pointcut("@annotation(Secured)")
    public void securedMethods(){
        
    }
    
    public boolean allowed(String user, String account, Integer sum){
        // add some rules
        if(sum > 100 || sum <= 0){
            return false;
        }
        LocalTime now = LocalTime.now();
        if(now.getHour() < 8 && now.getHour() > 18){
            return false;
        }
        return true;
    }
}
