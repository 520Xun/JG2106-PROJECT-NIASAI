package com.zzxy.pj.common.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version: java version 1.8
 * @Author: xun
 * @description:
 * @date: 2022-11-04 17:04
 */
@Retention(RetentionPolicy.RUNTIME)//运行期生效
@Target(ElementType.METHOD)//作用于方法
public @interface RequiredCache {

}
