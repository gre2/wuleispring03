package com.wl.beans.factory;

import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.beanDef.PropertyValue;

import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(BeanDefinition definition) {
        for(PropertyValue propertyValue:definition.getPropertyValues().getPropertyValues()){
            Object value=propertyValue.getValue();
            Method method=null;
            String methodName="set"+propertyValue.getName().substring(0,1).toUpperCase()
                    +propertyValue.getName().substring(1);
            try {
                method=definition.getBean().getClass().getDeclaredMethod(methodName,value.getClass());
                method.setAccessible(true);
                method.invoke(definition.getBean(),value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
