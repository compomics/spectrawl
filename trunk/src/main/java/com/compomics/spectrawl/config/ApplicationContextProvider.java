/*
 *

 */
package com.compomics.spectrawl.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Niels Hulstaert
 */
public class ApplicationContextProvider {

    private ApplicationContext applicationContext;
    private static final ApplicationContextProvider APPLICATION_CONTEXT_PROVIDER = new ApplicationContextProvider();

    //private no-arg constructor
    private ApplicationContextProvider() {
    }

    public synchronized static ApplicationContextProvider getInstance() {
        return APPLICATION_CONTEXT_PROVIDER;
    }

    public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("The application context is not set yet.");
        }
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setDefaultApplicationContext() throws BeanCreationException {
        this.applicationContext = new ClassPathXmlApplicationContext("spectrawl-context.xml");        
    }

    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }
}
