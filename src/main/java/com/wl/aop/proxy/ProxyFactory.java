package com.wl.aop.proxy;

import com.wl.aop.AdvisedSupport;

public class ProxyFactory extends AdvisedSupport implements AopProxy {

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        return new Cglib2AopProxy(this);
    }
}
