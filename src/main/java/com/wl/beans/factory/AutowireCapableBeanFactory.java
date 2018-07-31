package com.wl.beans.factory;

import com.wl.aop.parseProxy.BeanFactoryAware;
import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.beanDef.BeanRefrence;
import com.wl.beans.beanDef.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(BeanDefinition definition) throws Exception {
        //AspectJAwareAdvisorAutoProxyCreator----------abstractBeanFactory.getBeansForType
        if (definition.getBean() instanceof BeanFactoryAware) {
            ((BeanFactoryAware) definition.getBean()).setBeanFactory(this);
        }
        for(PropertyValue propertyValue:definition.getPropertyValues().getPropertyValues()){
            Object value=propertyValue.getValue();
            if (value instanceof BeanRefrence) {
                BeanRefrence beanReference = (BeanRefrence) value;
                value = getBean(beanReference.getName());
            }

            Method method=null;
            String methodName="set"+propertyValue.getName().substring(0,1).toUpperCase()
                    +propertyValue.getName().substring(1);
            try {

                method = definition.getBean().getClass().getDeclaredMethod(methodName, value.getClass());
                method.setAccessible(true);
                method.invoke(definition.getBean(),value);
            } catch (Exception e) {
                Field field = definition.getBean().getClass().getDeclaredField(propertyValue.getName());
                field.setAccessible(true);
                field.set(definition.getBean(), value);
            }
        }

    }
}
