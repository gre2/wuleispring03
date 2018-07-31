package com.wl;

import com.wl.aop.AdvisedSupport;
import com.wl.aop.TargetSource;
import com.wl.aop.TimerInterceptor;
import com.wl.aop.proxy.Cglib2AopProxy;
import com.wl.beans.applica.ApplicationContext;
import com.wl.beans.applica.ClassPathXmlApplicationContext;
import org.junit.Test;

public class TestAop {
    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
//        helloWorldService.helloWorld();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(HelloWorldServiceImpl.class, helloWorldService, HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);

        TimerInterceptor timerInterceptor = new TimerInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(advisedSupport);
        HelloWorldService helloWorldServiceProxy = (HelloWorldService) cglib2AopProxy.getProxy();
        helloWorldServiceProxy.helloWorld();
    }
}
