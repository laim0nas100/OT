/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.spring.config;

import java.util.UUID;
import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author Lemmin
 */
public class UUIDFactoryBean implements FactoryBean<String>{

    
    
    @Override
    public String getObject() throws Exception {
        return UUID.randomUUID().toString();
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
}
