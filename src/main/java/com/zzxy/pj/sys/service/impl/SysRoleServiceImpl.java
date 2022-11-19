package com.zzxy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.SysRoleDao;
import com.zzxy.pj.sys.dao.SysRoleMenuDao;
import com.zzxy.pj.sys.dao.SysUserRoleDao;
import com.zzxy.pj.sys.entity.SysRole;
import com.zzxy.pj.sys.service.SysRoleService;
import com.zzxy.pj.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService{

	@Autowired
	private SysRoleDao roleDao;
	@Autowired
	private SysUserRoleDao urDao;
	@Autowired
	private SysRoleMenuDao rmDao;
	
	public Pagination findRole(String name, Integer curPage, Integer pageSize) {
		//1.验证curPage，pageSize是否有值
		Assert.isEmpty(curPage == null || pageSize == null, "请选择当前页码或每页条数！");
		//2.得到角色的总条数
		int count = roleDao.countRole(name);
		//3.创建分页对象，算出所有属性
		Pagination pageObj = new Pagination(curPage, count, pageSize);
		curPage = pageObj.getCurPage();//得到当前页
		pageSize = pageObj.getPageSize();//得到每页条数
		int start = (curPage - 1) * pageSize;//分页跳过条数=（当前页-1）* 每页条数
		//4.根据参数找角色
		List<SysRole> list = roleDao.findRole(name, start, pageSize);
		//5.验证第4步的结果是否为null
		Assert.isEmpty(list == null || list.size() == 0, "数据不存在！");
		pageObj.setPageData(list);//设置分页数据
		return pageObj;
	}

	public int deleteRoleById(Integer id) {
		//	1. 验证参数
		Assert.isEmpty(id == null || id == 0, "请选择要删除的数据！");
		//	2.调用三个dao层与角色相关的删除方法
		urDao.deteleUserRoleByRoleId(id);
		rmDao.deleteRoleMenuByRoleId(id);
		int n = roleDao.deleteRoleById(id);
		//	3.验证结果
		Assert.isEmpty(n == 0, "角色不存在或已被删除！");
		return n;
	}

	
	public int insertRole(SysRole role, Integer[] ids) {
		Assert.isEmpty(role==null, "角色信息不能为空");
		Assert.isEmpty(role.getName()==null||role.getName().equals(""), "角色名称不能为空");
		Assert.isEmpty(ids== null||ids.length==0, "未给角色授权");
		//插入角色
		int n=roleDao.insertRole(role);
		//验证结果
		Assert.isEmpty(n==0, "插入角色信息有误！");
		//插入角色与菜单关系表
		int rm=rmDao.insertRoleMenu(role.getId(),ids);
		Assert.isEmpty(rm==0, "插入角色与菜单关系表时有误！");
		return rm;
	}

	public SysRoleMenuVo findRoleMenuIds(Integer id) {
		Assert.isEmpty(id==null||id==0, "请选择要修改的角色");
		SysRoleMenuVo roleMenuVo=roleDao.findRoleMenuIds(id);
		Assert.isEmpty(roleMenuVo==null||roleMenuVo.getName()==null||roleMenuVo.getName().equals(""), "角色不存在或已被删除");
		return roleMenuVo;
	}

	@Override
	public int updateRoleById(SysRoleMenuVo vo) {
		Assert.isEmpty(vo==null||vo.getId()==null, "请选择要修改的颜色");
		//修改角色与菜单之间的关系数据
		//先删除角色，然后新增角色和菜单的关系
		rmDao.deleteRoleMenuByRoleId(vo.getId());
		rmDao.insertRoleMenu(vo.getId(), vo.getMenuIds().toArray(new Integer[] {}));
		int n = roleDao.updatRoleByRoleId(vo);
		Assert.isEmpty(n==0, "修改角色信息失败");
		return n;
	}

	public List<SysRole> findAllRole() {
		List<SysRole> list=roleDao.findAllRole();
		return list;
	}
	
	
}
