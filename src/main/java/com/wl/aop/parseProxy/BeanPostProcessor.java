package com.wl.aop.parseProxy;

public interface BeanPostProcessor {
    Object posetProcessBeforeInitialization(Object bean, String beanName);

    Object posetProcessAfterInitialization(Object bean, String beanName);
}
