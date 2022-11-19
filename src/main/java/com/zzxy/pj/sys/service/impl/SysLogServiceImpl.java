package com.zzxy.pj.sys.service.impl;

import java.beans.Transient;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzxy.pj.common.annotaion.RequiredLog;
import com.zzxy.pj.common.util.IPUtils;
import com.zzxy.pj.common.util.ShiorUtil;
import com.zzxy.pj.sys.entity.pageProperties;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.SysLogDao;
import com.zzxy.pj.sys.entity.SysLog;
import com.zzxy.pj.sys.service.SysLogService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xun
 */
@Service
public class SysLogServiceImpl implements SysLogService{
	
	@Autowired
	private SysLogDao dao; 

	@Autowired
	private pageProperties pp;

	public Pagination findLogByUsername(String username, Integer curPage, Integer pageSize){
		pageSize=pageSize==0? pp.getPageSize():pageSize;
		Page<SysLog> page = PageHelper.startPage(curPage, pageSize);
		List<SysLog> list =dao.findLogObjectByUserName(username);//用pageHelper对象不需要自己写limit
		//page.getTotal()：PageHelper对象的startPage() 会自动查找数据总条数，返回值为Long
		Pagination pageObj = new Pagination(curPage, (int) page.getTotal(), pageSize);
		pageObj.setPageData(list);
		/*
		int rowCount = dao.getRowCount(username);
		Pagination pageObj = new Pagination(curPage, rowCount, pageSize);
		//跳过多少条=(当前页-1) * 每页条数
		int start = (pageObj.getCurPage() - 1) * pageObj.getPageSize();
		List<SysLog> list = dao.findLogObject(username, start, pageObj.getPageSize());
		pageObj.setPageData(list);
		return pageObj;//返回分页信息*/
		return pageObj;
	}

	@Transactional
	//开始事务
	public int doDeleteLog(Integer[] ids) {
		Assert.isEmpty(ids == null || ids.length == 0, "请选择要删除的数据");
		int n = dao.deleteLogByIds(ids);
		Assert.isEmpty(n == 0, "数据已被删除！");
		return n;
	}

	//开启一个新事务 Propagation.REQUIRES_NEW
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Async  //异步注解，交给另外一个注解去执行
	public void insertLog(SysLog log) {
		int n=dao.insertLog(log);
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}

	/**
	 * 保存日志
	 * @param time
	 * @param jp
	 */
	//开启一个新事务 Propagation.REQUIRES_NEW
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Async  //异步注解，交给另外一个注解去执行
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
		String username = ShiorUtil.getUserName ();//设置用户名
		System.out.println ("**************存入日志角色*******************"+username);
		log.setUsername(username);
		System.out.println(log+"********存入日志");
		dao.insertLog(log);
	//Thread.sleep(5000);
	}
}
