package com.zzxy.pj.common.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @version: java version 1.8
 * @Author: xun
 * @description:
 * @date: 2022-11-04 10:54
 */
@Aspect
@Slf4j
@Service //Component service controller ：效果一样，都是交给容器管理
public class SysTimeAspect {
    //切入点：做为一个标识
    //bean后面的面子要是：容器管理对象(目标类对象)的id
    //如果id有指定就是指定的，没有指定就是类名首字母小写
    @Pointcut("bean(sysUserServiceImpl)")
    public void doTime(){}

    @Before("doTime()") //前置通知 ,要指定切入点，在目标方法执行之前执行，在环绕之后执行
    public void doBefor(){
        System.out.println("time doBefore()");
    }

    @After("doTime()") //后置通知,要指定切入点，，在环绕之前执行，在返回通知之后执行,在异常通知前执行
     public void doAfter(){
        System.out.println("time doAfter");
    }

    //返回通知,要指定切入点，在后置通知之前
    //有异常通知不执行
    @AfterReturning("doTime()")
    public void doAfterReturning(){
        System.out.println("time doAfterReturning");
    }

    @AfterThrowing("doTime()")//异常通知,要指定切入点，有异常就执行，没有异常就不执行
    public void doAfterThrowing(){
        System.out.println("time doAfterThrowing");
    }

    @Around("doTime()")//环绕通知，在前置通知前，在后置之后执行
    //在前置通知前
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("time doBeforAround");
        Object proceed = jp.proceed();//调用目标方法，或者跳到下一个切入点
        log.info("time doAfterAround");
        return proceed;
    }
}
