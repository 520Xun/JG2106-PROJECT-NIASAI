package com.zzxy.pj.common.Aspect;

import com.zzxy.pj.common.annotaion.RequiredLog;
import com.zzxy.pj.common.util.IPUtils;
import com.zzxy.pj.common.util.ShiorUtil;
import com.zzxy.pj.sys.dao.SysLogDao;
import com.zzxy.pj.sys.entity.SysLog;
import com.zzxy.pj.sys.service.SysLogService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import static com.github.pagehelper.util.MetaObjectUtil.method;

@Aspect//变成切面类
@Slf4j
@Component
public class LogAspect {
    //LogAspect: 代理类
    //sysUserServiceImpl:目标类,被代理类
    @Pointcut("bean(sysUserServiceImpl)")//切入点
    public void logPointCut() {}

@Autowired
  private SysLogService logService;

    @Pointcut("@annotation(com.zzxy.pj.common.annotaion.RequiredLog)")
    public void saveLog(){}

    @Around("saveLog()") //环绕通知 优先级最高
    //经过分析，保存日志要用细节
     //ProceedingJoinPoint：想要调用目标方法必须是ProceedingJoinPoint，是JoinPoint的子接口
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        try {
         long begin=System.currentTimeMillis();
            Object result=jp.proceed();//调用下一个切面方法或目标方法
            long end=System.currentTimeMillis();
            insertLog(end-begin,jp);
            return result;
        }catch(Throwable e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 保存日志
	 * @param time
	 * @param jp
	 */
    public void insertLog(long time, ProceedingJoinPoint jp) throws Exception {
        //获取方法的签名，包含了方法的签名信息
        //MethodSignature是Signature的儿子，是向下造型，必须强转
        //能点什么看引用，调用子类方法

        //jdk获取方法签名是接口的，CGLEB获取方法签名是实现类的
        //原因：jdk动态代理是实现接口，cgleb 是继承类实现的
        MethodSignature ms = (MethodSignature) jp.getSignature();
        String msName = ms.getName();
        //获取目标方法  这种方法CGLIB动态代理获取的是接口的方法，不能用
        // Method method = ms.getMethod();
        Object obj = jp.getTarget();//获取目标对象
        Class<?> clz = obj.getClass();//获取类对象
        //反射获取指定方法，第一个参数为方法名，第二个为参数类型
        //ms.getParameterTypes() ：获取方法参数类型
        Method method = clz.getDeclaredMethod(msName, ms.getParameterTypes());
        //jdk获取类型是接口方法的类型
        // String type = ms.getDeclaringTypeName();//获取类名称
        String type= clz.getName();
        String name = method.getName();//获取方法名
        String methodName=type+"."+name; //执行方法
        System.out.println(method);
        Object[] args = jp.getArgs();//获取参数的方法
        String params = Arrays.toString(args);
        System.out.println(params);
        String ip= IPUtils.getIpAddr();
        System.out.println("ip***************"+ip);
        RequiredLog annotation = method.getAnnotation(RequiredLog.class);//获取指定类型注解z
        String operation = annotation.value();
        SysLog log=new SysLog();
        log.setIp(ip);
        log.setCreatedTime(new Date());
        log.setMethod(methodName);
        log.setOperation(operation);
        log.setTime(time);
        log.setParams(params);
        String userName = ShiorUtil.getUserName ();
        log.setUsername(userName);
        System.out.println(log+"********存入日志");
        logService.insertLog(log);
    }

    // @Around("@annotation(com.zzxy.pj.common.annotaion.RequiredLog)")
    public Object  test(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕通知之前！！！");
        Object proceed = jp.proceed();
        System.out.println("环绕通知之后！！！");
        return proceed;
    }
}
