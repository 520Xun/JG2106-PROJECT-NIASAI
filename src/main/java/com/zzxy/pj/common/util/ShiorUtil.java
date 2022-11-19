package com.zzxy.pj.common.util;

import com.zzxy.pj.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @version: java version 1.8
 * @Author: xun
 * @description:
 * @date: 2022-11-15 11:17
 */
public class ShiorUtil {
        public static SysUser getLoginUser(){
            //获取Subject实现对象  管理主体对象即登录对象
            Subject subject = SecurityUtils.getSubject ();
            //获取身份  获取登录对象
            return (SysUser) subject.getPrincipal ();
        }

       public static String  getUserName(){
            return getLoginUser ().getUsername ();
        }
}
