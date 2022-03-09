package com.haohan.platform.service.sys.modules.xiaodian.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author shenyu
 * @create 2019/3/26
 */
public class SpringApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        assertApplicationContext();
        return context;
    }

    public static Object getBean(String beanName){
        assertApplicationContext();
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return context.getBean(requiredType);
    }


    public static void assertApplicationContext(){
        if (null == context){
            throw new RuntimeException("application context inject fail");
        }
    }

}
