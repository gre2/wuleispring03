package com.wl.beans.applica;

import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.factory.AbstractBeanFactory;
import com.wl.beans.factory.AutowireCapableBeanFactory;
import com.wl.beans.reader.XmlBeanDefinitionReader;
import com.wl.beans.resour.ResourceLoader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String location;

    public ClassPathXmlApplicationContext(String location){
        this(location,new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String location, AbstractBeanFactory abstractBeanFactory) {
        super(abstractBeanFactory);
        this.location=location;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory abstractBeanFactory) {
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(new ResourceLoader());
        //abstractBeanDefinitionReader类中的registry属性，数据和ioc的一样
        reader.loadBeanDefinitions(location);
        for(Map.Entry<String,BeanDefinition> entry:reader.getRegistry().entrySet()){
            abstractBeanFactory.registryBeanDefinition(entry.getKey(),entry.getValue());
        }
    }
}
