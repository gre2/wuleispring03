package com.wl.beans.factory;

import com.wl.aop.parseProxy.BeanPostProcessor;
import com.wl.beans.beanDef.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractBeanFactory implements BeanFactory {
    private Map<String,BeanDefinition> ioc=new ConcurrentHashMap<String, BeanDefinition>();

    private List<String> beanNames=new ArrayList<String>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();


    public Object getBean(String name) {
        BeanDefinition definition=ioc.get(name);
        if(definition==null){
            return null;
        }
        Object bean=definition.getBean();
        if(bean==null){
            bean = doCreateBean(definition);
            //aop 产生代理对象
            bean = initializeBean(bean, name);
            definition.setBean(bean);
        }
        return definition.getBean();
    }

    private Object initializeBean(Object bean, String name) {
        for (BeanPostProcessor before : beanPostProcessors) {
            before.posetProcessBeforeInitialization(bean, name);
        }
        for (BeanPostProcessor after : beanPostProcessors) {
            after.posetProcessAfterInitialization(bean, name);
        }
        //after之后bean变成代理对象了
        return bean;
    }

    private Object doCreateBean(BeanDefinition definition) {
        try {
            Object bean=definition.getBeanClass().newInstance();
            //之前doLoadDefinitions里面只是把对应关系存储在beanDefinition里面了，但是其实没有对每个属性赋值，此时赋值
            applyPropertyValues(definition);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void applyPropertyValues(BeanDefinition definition) throws Exception {
    }

    public void registryBeanDefinition(String key, BeanDefinition value) {
        ioc.put(key,value);
        beanNames.add(key);
    }

    public void preInstantiateSingletons() {
        for(String beanName:beanNames){
            getBean(beanName);
        }

    }

    //找到clazz的父类或者接口的集合
    public List getBeansForType(Class clazz) {
        List beans = new ArrayList<Object>();
        for (String beanName : beanNames) {
            //clazz 是否是后面的类，或者是否是其父类或者接口 返回true
            if (clazz.isAssignableFrom(ioc.get(beanName).getBeanClass())) {
                beans.add(getBean(beanName));
            }
        }
        return beans;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }
}
