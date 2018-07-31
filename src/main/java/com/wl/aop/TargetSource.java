package com.wl.aop;

public class TargetSource {

    //被代理类的基类
    private Class<?> targetClass;

    private Class<?>[] interfaces;

    private Object target;

    public TargetSource(Class<?> targetClass, Object target, Class<?>... interfaces) {
        this.targetClass = targetClass;
        this.interfaces = interfaces;
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public Object getTarget() {
        return target;
    }
}

