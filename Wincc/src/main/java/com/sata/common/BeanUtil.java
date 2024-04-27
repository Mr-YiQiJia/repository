package com.sata.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private static String defaultName(Class<?> beanClass) {
        return StrUtlis.toLowerFirst(beanClass.getSimpleName());
    }

    public static <T> T getBean(Class<T> beanClass) {
        return (T) getBean(defaultName(beanClass));
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
}
