package com.wl.beans.factory;

import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.beanDef.BeanRefrence;
import com.wl.beans.beanDef.PropertyValue;

import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(BeanDefinition definition) {
        for(PropertyValue propertyValue:definition.getPropertyValues().getPropertyValues()){
            Object value=propertyValue.getValue();
            boolean flag = false;
            if (value instanceof BeanRefrence) {
                BeanRefrence beanReference = (BeanRefrence) value;
                value = getBean(beanReference.getName());
                flag = true;
            }

            Method method=null;
            String methodName="set"+propertyValue.getName().substring(0,1).toUpperCase()
                    +propertyValue.getName().substring(1);
            try {
                if (flag) {
                    method = definition.getBean().getClass().getDeclaredMethod(methodName, value.getClass().getInterfaces());
                } else {
                    method = definition.getBean().getClass().getDeclaredMethod(methodName, value.getClass());
                }

                method.setAccessible(true);
                method.invoke(definition.getBean(),value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
