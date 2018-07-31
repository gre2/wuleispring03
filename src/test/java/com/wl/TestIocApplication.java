package com.wl;

import com.wl.beans.applica.ApplicationContext;
import com.wl.beans.applica.ClassPathXmlApplicationContext;
import org.junit.Test;

public class TestIocApplication {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

}
