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

        //找到AspectJExpressionPointcutAdvisor的父类或者接口的集合
        List<AspectJExpressionPointcutAdvisor> advisors = abstractBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource( bean.getClass(),bean, bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);

                return advisedSupport.getProxy();
            }
        }
        return bean;
    }
}
