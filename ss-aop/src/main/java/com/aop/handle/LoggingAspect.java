package com.aop.handle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect

/*
1.同一个方法切点的不同切面中，使用Order来控制切面的执行顺序   ，若不定义的话默认是使用类名的首字母顺序来决定的

2.也可以使用类来实现Ordered接口来实现getOrder接口，从而控制切面的执行顺序
*/

@Order(1)
@Component
public class LoggingAspect implements Ordered {
    /*
    除了使用@Order注解之外还可以实现接口来实现对切面顺序的控制
    */
    @Override
    public int getOrder() {
        return 0;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     *       定义切点(Pointcut)
     *              @Pointcut 定义切点，切点表达式   //切点的参数可以设置为execution() 表达式，也可以使用@annotation基于注解的切点
     *              使用时直接使用@注解即可实现切面
     */

    /**
     * 引用切点方法
     */
    @Pointcut("execution(* com.aop.biz.*.*(..)) && @annotation(com.aop.handle.Loggable)")  //execution([修饰符] 返回类型 包.类.方法(参数))
    public void bizMethods() {}


    /**
     * 已有注解
     */
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void bizMethods1() {}

    /**
     * 自定义注解
     */
    @Pointcut("@annotation(com.aop.handle.Loggable)")    //所有注解为@Loggable的方法都会在切面中执行
    public void loggableMethods() {}
    /**
     * 2.3 定义通知（Advice）               //通知的参数可以设置为execution() 表达式，也可以引用切点方法
     *             Spring支持五种通知类型：
     *             @Before：目标方法执行前执行。
     *             @After：目标方法执行后执行（无论是否异常）。
     *             @AfterReturning：目标方法正常返回后执行
     *             @AfterThrowing：目标方法抛出异常后执行。
     *             @Around：包裹目标方法，可控制是否执行方法。
     */
    @Before("loggableMethods()")
    public void beforeBizMethods(){
        logger.info("==== 目标方法执行前执行 ====");
    }

    @After("loggableMethods()")
    public void beforeBizMethods1(){
        logger.info("==== 目标方法执行后执行（无论是否异常） ====");
    }

    @AfterReturning("loggableMethods()")
    public void beforeBizMethods2(){
        logger.info("==== 目标方法正常返回后执行 ====");
    }

    @AfterThrowing("loggableMethods()")
    public void beforeBizMethods3(){
        logger.info("==== 目标方法抛出异常后执行 ====");
    }

    @Around("loggableMethods()")
    public Object beforeBizMethods4(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        logger.info("方法 {} 花费 {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }

}
