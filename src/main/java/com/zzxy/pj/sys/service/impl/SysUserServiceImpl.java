package com.zzxy.pj.sys.service.impl;

import java.util.List;
import java.util.UUID;

import com.zzxy.pj.common.annotaion.RequiredLog;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.SysUserDao;
import com.zzxy.pj.sys.dao.SysUserRoleDao;
import com.zzxy.pj.sys.entity.SysUser;
import com.zzxy.pj.sys.service.SysUserService;
import com.zzxy.pj.sys.vo.SysUserDeptVo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
//Isolation: 指定隔离级别
//		未提交读 READ_UNCOMMITTED
//		已提交读 oracle默认 READ_COMMITTED
//		可重复读 REPEATABLE_READ mysql默认
//		串行化 SERIALIZABLE
//timeout：事务超时，秒为单位
//rollbackFor ：指定生效的异常
//noRollbackFor ：指定不回滚的异常
// propagation :  指定事务传播机制
//		readOnly: 设置是否只读   默认可以读写
//@Transactional : 代表整个类当作一个事务
@Transactional(isolation = Isolation.READ_COMMITTED,
	timeout = 30,
		rollbackFor = RuntimeException.class,
		readOnly = false
	)
//select * from acc for update  	 查询数据加了行锁
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao userDao;
	
	@Autowired
	private SysUserRoleDao urDao;
	@RequiredLog("查询操作")//自定义注解，细腻度，只作用于某一个方法上面
	@RequiresPermissions ("sys:user:view")
	//@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)  //当类和方法上都有事务注解时 方法的优先级高
	public Pagination findUserDeptVo(String username, Integer curPage, Integer pageSize) {
		//1.验证curPage，pageSize是否有值
		Assert.isEmpty(curPage == null || pageSize == null, "请选择当前页码或每页条数！");
		//2.得到用户的总条数
		int count = userDao.getCountByUser(username);
		//3.创建分页对象，算出所有属性
		Pagination pageObj = new Pagination(curPage, count, pageSize);
		//得到当前页
		curPage = pageObj.getCurPage();
		pageSize = pageObj.getPageSize();//得到每页条数
		int start = (curPage - 1) * pageSize;//分页跳过条数=（当前页-1）* 每页条数
		//4.根据参数找用户部门Vo
		List<SysUserDeptVo> list = userDao.findUserDeptVo(username, start, pageSize);
		//5.验证第4步的结果是否为null
		Assert.isEmpty(list == null || list.size() == 0, "数据不存在！");
		pageObj.setPageData(list);//设置分页数据
		return pageObj;
	}

	@RequiredLog("状态操作")
	@RequiresPermissions ("sys:user:update")
	public int updateValId(Integer id, Integer valId) {
		Assert.isEmpty(id==null||id==0, "请选择用户");
		Assert.isEmpty(valId==null, "操作有误！");
		int n = userDao.updateValId(id, valId);
		Assert.isEmpty(n==0, "修改失败");
		return n;
	}

	@RequiredLog("新增用户")
	public int insertUser(SysUser user,Integer[] roleIds) {
		Assert.isEmpty(user==null||user.getUsername()==null, "请填写用户信息！");
		Assert.isEmpty(user.getPassword()==null, "请填写密码！");
		Assert.isEmpty(roleIds==null||roleIds.length==0,"请至少选择至少一个角色！");
		//UUID是uitL的包
		String salt=UUID.randomUUID().toString();//加盐，32位的随机盐值
		// 加密方式 原密码 盐 加密次数
		SimpleHash sh = new SimpleHash("MD5", user.getPassword(), salt, 1);
		String password = sh.toHex();//转为32位的字符串
		user.setPassword(password);
		user.setSalt(salt);
		//判断用户是否存在
		SysUser u=userDao.findUserByName(user.getUsername());
		Assert.isEmpty(u!=null, "用户已存在！");
		//插入用户
		int n=userDao.insertUser(user);
		Assert.isEmpty(n==0, "用户插入失败！");
		urDao.insertUserRole(user.getId(),roleIds);
		return n;
	}

	@Override
	public List<Integer> findRoleByUserId(Integer userId) {
		Assert.isEmpty(userId==null||userId==0,"请选择要修改的用户！！");
		List<Integer> list=urDao.findRoleByUserId(userId);
		return list;
	}

	@Override
	public Integer updatePassWord(String password, String newPassword, String cfgPassword) {
		Assert.isEmpty(password==null||password.equals(""),"请填写原密码！");
		Assert.isEmpty(newPassword==null||newPassword.equals(""),"请填写密码！");
		Assert.isEmpty(cfgPassword==null||cfgPassword.equals(""),"请填确定密码！");

		//获取登录的用户
		SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipals();
		SimpleHash sh = new SimpleHash("MD5", password, user.getSalt(), 1);
		Assert.isEmpty(!user.getPassword().equals(sh.toHex()),"原密码不正确");
		int n = userDao.updatePassword(sh.toHex(), user.getSalt(), user.getId());
		Assert.isEmpty(n==0,"修改失败");
		return n;
	}

	@Override
	public int updateUser(SysUser user, Integer[] roleIds) {
		Assert.isEmpty(user==null||user.getId()==null,"请选择要修改的用户信息！");
		Assert.isEmpty(user.getUsername()==null||user.getUsername().equals(""),"请填写要修改的用户信息！");
		Assert.isEmpty(roleIds==null||roleIds.length==0,"请至少选择至少一个角色！");
		urDao.deteleUserRoleByUserId(user.getId());
		urDao.insertUserRole(user.getId(),roleIds);
		int n=userDao.updateUser(user);
		Assert.isEmpty(n==0,"修改用户失败！");
		return n;
	}
}
