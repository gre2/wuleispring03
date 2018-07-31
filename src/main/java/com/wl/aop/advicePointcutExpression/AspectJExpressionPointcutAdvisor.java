package com.wl.aop.advicePointcutExpression;


import com.wl.aop.common.Pointcut;
import org.aopalliance.aop.Advice;

//advice+expression
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;


    public Pointcut getPointcut() {
        return pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setPointcut(AspectJExpressionPointcut pointcut) {
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }
}
