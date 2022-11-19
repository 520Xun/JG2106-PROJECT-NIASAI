package com.zzxy.pj.sys.controller;

import com.github.pagehelper.PageHelper;
import com.zzxy.pj.common.annotaion.RequiredLog;
import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysUser;
import com.zzxy.pj.sys.service.SysUserService;
import net.sf.jsqlparser.Model;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserServiceImpl;


	@RequestMapping("doLogin")
	@RequiredLog ("登录操作")
	public  JsonResult doLogin(String username, String password, boolean isRememberMe){
		//当SecurityManage创建对象时在构造方法中设置了Subject对象
		Subject subject = SecurityUtils.getSubject ();
		//进行账号验证
		UsernamePasswordToken token=new UsernamePasswordToken (username,password);
		token.setRememberMe (isRememberMe);
		subject.login (token);
		return  new JsonResult ("登录成功！");
	}
	
	@RequestMapping("findUserDeptVo")
	public JsonResult doFindUser(String username,Integer curPage,Integer pageSize) {
		Pagination findUser = sysUserServiceImpl.findUserDeptVo(username, curPage, pageSize);
		return new JsonResult(findUser);
	}
	
	@RequestMapping("updateValId")
	public JsonResult updateValId(Integer id,Integer valId) {
		System.out.println(id+","+valId);
		int n = sysUserServiceImpl.updateValId(id, valId);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("修改成功！");
		return jr;
	}

	/**
	 * 添加用户
	 * @param user
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("saveUser")
	//@RequestParam("roleIds[]") 加了该注解必须传值，required为flase时可以不传值
	public JsonResult savetUser(SysUser user,@RequestParam("roleIds[]") Integer[] roleIds) {
		int n = sysUserServiceImpl.insertUser(user, roleIds);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("添加成功！");
		return jr;
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping("updateUser")
	public JsonResult updateUser(SysUser user,@RequestParam("roleIds[]") Integer[] roleIds){
		int n=sysUserServiceImpl.updateUser(user,roleIds);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("修改成功！");
		return  jr;
	}

	@RequestMapping("findRoleByUserId")
	public JsonResult findRoleByUserId(Integer userId){
		List<Integer> list=sysUserServiceImpl.findRoleByUserId(userId);
		return new JsonResult(list);
	}

	@RequestMapping("updatePassword")
	public JsonResult updatePassword(String password,String newPassword,String cfgPassword){
		Integer n = sysUserServiceImpl.updatePassWord(password, newPassword, cfgPassword);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("修改成功！");
		return  jr;
	}
	//获取登录用户名
//		@RequestMapping("getLoginUserName")
//		public  JsonResult getLoginUserName(){
//			return new JsonResult (ShiorUtil.getUserName ());
//		}

	public static void main(String[] args) {
		PageHelper pageHelper = new PageHelper();
		System.out.println(pageHelper);
		/*
		查看springboot版本
		String springVersion = SpringVersion.getVersion();
		String springBootVersion = SpringBootVersion.getVersion();
		System.out.println("Spring版本:"+springVersion+"\nSpringBoot版本:"+springBootVersion);*/

	}

}
