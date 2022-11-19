package com.zzxy.pj.common.Aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: xun
 * @description:
 * @date: 2022-11-04 17:02
 */
@Aspect
@Service
@Slf4j
@Data
//菜单切面代理类
public class SysMenuAspect {

    private  List<Map<String, Object>> menuList;//缓存菜单数据



    @Pointcut("@annotation(com.zzxy.pj.common.annotaion.RequiredCache)")
    public void  findMenu(){}

    @Around("findMenu()")
    //ProceedingJoinPoint：想要调用目标方法必须是ProceedingJoinPoint，是JoinPoint的子接口
    public  Object findMenuCaChe(ProceedingJoinPoint jp) throws Throwable {
        log.info("start:"+System.currentTimeMillis());
        if(menuList!=null) {
            return menuList;//有缓存直接返回
        }
        //如果缓存为空
        menuList = (List<Map<String, Object>>) jp.proceed();
        log.info("after:"+System.currentTimeMillis());
        return menuList;
    }
}
