package com.wl.beans.factory;

import com.wl.aop.parseProxy.BeanFactoryAware;
import com.wl.beans.beanDef.BeanDefinition;
import com.wl.beans.beanDef.BeanRefrence;
import com.wl.beans.beanDef.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(Object bean,BeanDefinition mbd) throws Exception {
        //AspectJAwareAdvisorAutoProxyCreator----------abstractBeanFactory.getBeansForType
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanRefrence) {
                BeanRefrence beanReference = (BeanRefrence) value;
                value = getBean(beanReference.getName());
            }

            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);

                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }

    }
}
