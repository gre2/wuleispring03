package com.wl.aop.advicePointcutExpression;

import com.wl.aop.common.ClassFilter;
import com.wl.aop.common.MethodMatcher;
import com.wl.aop.common.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private PointcutParser pointcutParser;

    private String expression;

    private PointcutExpression pointcutExpression;

    private static final Set<PointcutPrimitive> primitive = new HashSet<PointcutPrimitive>();

    static {
        primitive.add(PointcutPrimitive.EXECUTION);
    }

    public AspectJExpressionPointcut() {
        this(primitive);
    }

    public AspectJExpressionPointcut(Set<PointcutPrimitive> primitive) {
        pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(primitive);
    }

    public boolean matches(Class clazz) {
        //用pointcutParser和expression构建pointcutExpression对象
        checkReadyToMatch();
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    private void checkReadyToMatch() {
        if (pointcutExpression == null) {
            pointcutExpression = buildPointcutExpression();
        }
    }

    private PointcutExpression buildPointcutExpression() {
        return pointcutParser.parsePointcutExpression(expression);
    }

    public ClassFilter getClassFilter() {
        return this;
    }

    public MethodMatcher getMethodMatcher() {
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
