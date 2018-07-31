package com.wl.beans.applica;

import com.wl.aop.parseProxy.BeanPostProcessor;
import com.wl.beans.factory.AbstractBeanFactory;

import java.util.List;

public abstract class AbstractApplicationContext implements ApplicationContext{

    private AbstractBeanFactory abstractBeanFactory;

    public AbstractApplicationContext(AbstractBeanFactory abstractBeanFactory){
        this.abstractBeanFactory=abstractBeanFactory;
    }

    public Object getBean(String name) {
        return abstractBeanFactory.getBean(name);
    }


    protected void refresh() {
        loadBeanDefinitions(abstractBeanFactory);
        //aop start
        registerBeanPostProcessors(abstractBeanFactory);
        //aop end
        onRefresh();
    }

    protected void registerBeanPostProcessors(AbstractBeanFactory abstractBeanFactory) {
        //找到ioc里面实现BeanPostProcessor这个接口的类,放factory的beanPostProcessor属性里面
        List<BeanPostProcessor> beanPostProcessors = abstractBeanFactory.getBeansForType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            abstractBeanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void onRefresh() {
        abstractBeanFactory.preInstantiateSingletons();
    }

    protected abstract void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory);
}
