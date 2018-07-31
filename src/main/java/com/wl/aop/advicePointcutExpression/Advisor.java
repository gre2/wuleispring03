package com.wl.aop.advicePointcutExpression;

import org.aopalliance.aop.Advice;

//顾问
public interface Advisor {
    Advice getAdvice();
}
