package com.wl.aop.parseProxy;

import com.wl.aop.TargetSource;
import com.wl.aop.advicePointcutExpression.AspectJExpressionPointcutAdvisor;
import com.wl.aop.proxy.ProxyFactory;
import com.wl.beans.factory.AbstractBeanFactory;
import com.wl.beans.factory.BeanFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

public class AspectJAwareAdvisorAutoProxyCreator implements BeanFactoryAware, BeanPostProcessor {
    private AbstractBeanFactory abstractBeanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.abstractBeanFactory = (AbstractBeanFactory) beanFactory;
    }

    public Object posetProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object posetProcessAfterInitialization(Object bean, String beanName) {
        //AspectJExpressionPointcutAdvisor=expresion+advice
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodInterceptor) {
            return bean;
        }

        List<AspectJExpressionPointcutAdvisor> advisors = abstractBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            //判断bean.getClass是否在expression包范围
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory proxyFactory = new ProxyFactory();
                proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                TargetSource targetSource = new TargetSource(bean.getClass(), bean, bean.getClass().getInterfaces());
                proxyFactory.setTargetSource(targetSource);
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }
}
