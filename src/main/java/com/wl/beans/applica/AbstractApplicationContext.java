package com.wl.beans.applica;

import com.wl.beans.factory.AbstractBeanFactory;

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
        onRefresh();
    }

    private void onRefresh() {
        abstractBeanFactory.preInstantiateSingletons();
    }

    protected abstract void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory);
}
