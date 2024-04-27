package com.sata.yqj.cqdxer.common;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
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

    @SneakyThrows
    public static <T> T reload(Class<T> beanClass) {
        T old = getBean(beanClass);
        if (old != null) {
            DefaultListableBeanFactory defaultFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
            defaultFactory.destroySingleton(defaultName(beanClass));
            defaultFactory.registerSingleton(defaultName(beanClass), old.getClass().newInstance());
        }
        return old;
    }
}
