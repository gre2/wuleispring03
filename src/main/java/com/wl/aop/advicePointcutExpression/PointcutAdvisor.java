package com.wl.aop.advicePointcutExpression;

import com.wl.aop.common.Pointcut;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
