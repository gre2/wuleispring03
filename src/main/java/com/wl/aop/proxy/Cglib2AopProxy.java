package com.wl.aop.proxy;

import com.wl.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


public class Cglib2AopProxy extends AbstractAopProxy {

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        super(advisedSupport);
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        Object enhanced = enhancer.create();
        return enhanced;
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private AdvisedSupport advisedSupport;
        //delegate委托
        private org.aopalliance.intercept.MethodInterceptor delegateMethodInterceptor;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
            this.delegateMethodInterceptor = advisedSupport.getMethodInterceptor();
        }

        public Object intercept(Object instance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return delegateMethodInterceptor.invoke(new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args, methodProxy));
        }

        private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
            private final MethodProxy methodProxy;

            public CglibMethodInvocation(Object targetSource, Method method, Object[] args, MethodProxy methodProxy) {
                super(targetSource, method, args);
                this.methodProxy = methodProxy;
            }

            @Override
            public Object proceed() throws Throwable {
                return this.methodProxy.invoke(this.target, this.args);
            }
        }
    }
}
