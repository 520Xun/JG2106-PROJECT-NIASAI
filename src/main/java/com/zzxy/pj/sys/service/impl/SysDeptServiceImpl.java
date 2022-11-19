package com.zzxy.pj.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.SysDeptDao;
import com.zzxy.pj.sys.dao.SysUserDao;
import com.zzxy.pj.sys.entity.SysDept;
import com.zzxy.pj.sys.service.SysDeptService;

@Service
public class SysDeptServiceImpl implements SysDeptService{
	
	@Autowired
	private SysDeptDao deptDao;
	
	@Autowired
	private SysUserDao userDao;

	public List<SysDept> findAllDept() {
		List<SysDept> list=deptDao.findAllDept();
		Assert.isEmpty(list==null||list.size()==0, "部门信息不存在或！");
		return list;
	}

	public Integer deleteDept(Integer id) {
		Assert.isEmpty(id==null||id==0, "请选择要删除的数据！");
		//查询部门是否有下级部门
		Integer c=deptDao.getCountChildByMenuId(id);
		Assert.isEmpty(c>0, "有下级部门不允许删除！");
		//删除部门
		Integer n=deptDao.deleteDept(id);
		Assert.isEmpty(n==0, "删除失败！");
		//更新用户与部门之间的关系
		userDao.updateDeptId(id);
		return n;
	}

	@Override
	public Integer insertDept(SysDept dept) {
		Assert.isEmpty(dept==null||dept.getName().equals(""), "请填写部门信息！");
		Integer n=deptDao.insertDept(dept);
		Assert.isEmpty(n==0, "新增失败！");
		return n;
	}

	@Override
	public Integer updateDept(SysDept dept) {
		Assert.isEmpty(dept==null||dept.getName().equals(""), "请填写部门信息！");
		Integer n=deptDao.updateDept(dept);
		Assert.isEmpty(n==0, "修改失败！");
		return n;
	}
	
	
}
