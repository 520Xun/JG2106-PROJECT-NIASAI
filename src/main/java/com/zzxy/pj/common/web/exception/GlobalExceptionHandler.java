package com.zzxy.pj.common.web.exception;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zzxy.pj.common.entity.JsonResult;

//全局异常处理类
//@ControllerAdvice//全局异常处理注解
@RestControllerAdvice//相当于@ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {
	
	//统一处理运行时异常
	@ExceptionHandler(RuntimeException.class)
	//@ResponseBody//把返回值当数据处理
	public JsonResult doHandleRuntimeException(RuntimeException e){
		e.printStackTrace();//控制台的打印机
		e = new RuntimeException("服务器繁忙！");
		return new JsonResult(e);
	}
	
	@ExceptionHandler(ServiceException.class)
	public JsonResult handlerServiceException(ServiceException e) {
		return new JsonResult(e);
	}

	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
//		JsonResult r = new JsonResult ();
//		r.setState(0);
//		if(e instanceof UnknownAccountException) {
//			r.setMessage("账户不存在");
//		}else if(e instanceof LockedAccountException) {
//			r.setMessage("账户已被禁用");
//		}else if(e instanceof IncorrectCredentialsException) {
//			r.setMessage("密码不正确");
//		}else if(e instanceof AuthorizationException) {
//			r.setMessage("没有此操作权限");
//		}else {
//			r.setMessage("系统维护中");
//		}
//		e.printStackTrace();
		if (e.getClass ().equals (UnknownAccountException.class)) {
			e = new UnknownAccountException ("账号不存在！");
		} else if (e instanceof LockedAccountException) {
			e = new LockedAccountException ("账户已被禁用");
		} else if (e instanceof AuthorizationException) {
			e = new AuthorizationException ("无此权限！");
		}
		return new JsonResult (e);
	}
}
