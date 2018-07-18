package com.wl.beans.factory;

import com.wl.beans.beanDef.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractBeanFactory implements BeanFactory {
    private Map<String,BeanDefinition> ioc=new ConcurrentHashMap<String, BeanDefinition>();

    private List<String> beanNames=new ArrayList<String>();

    public Object getBean(String name) {
        BeanDefinition definition=ioc.get(name);
        if(definition==null){
            return null;
        }
        Object bean=definition.getBean();
        if(bean==null){
            doCreateBean(definition);
        }
        return definition.getBean();
    }

    private void doCreateBean(BeanDefinition definition) {
        try {
            Object bean=definition.getBeanClass().newInstance();
            definition.setBean(bean);
            applyPropertyValues(definition);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void applyPropertyValues(BeanDefinition definition) { }

    public void registryBeanDefinition(String key, BeanDefinition value) {
        ioc.put(key,value);
        beanNames.add(key);
    }

    public void preInstantiateSingletons() {
        for(String beanName:beanNames){
            getBean(beanName);
        }

    }
}
