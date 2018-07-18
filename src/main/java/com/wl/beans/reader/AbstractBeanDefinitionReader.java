package com.wl.beans.reader;

import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.resour.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    //todo  为了给factory的ioc用，多余
    private Map<String,BeanDefinition> registry;
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry=new HashMap<String, BeanDefinition>();
        this.resourceLoader=resourceLoader;
    }

    public ResourceLoader getResourceLoader(){return resourceLoader;}

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

}
