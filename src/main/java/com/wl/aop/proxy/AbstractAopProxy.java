package com.wl.aop.proxy;

import com.wl.aop.AdvisedSupport;

public abstract class AbstractAopProxy implements AopProxy {
    protected AdvisedSupport advisedSupport;

    public AbstractAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
}
