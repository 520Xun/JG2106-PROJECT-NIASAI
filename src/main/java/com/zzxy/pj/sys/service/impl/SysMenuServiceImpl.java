package com.zzxy.pj.sys.service.impl;

import com.zzxy.pj.common.entity.Node;
import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.SysMenuDao;
import com.zzxy.pj.sys.dao.SysRoleMenuDao;
import com.zzxy.pj.sys.entity.SysMenu;
import com.zzxy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService{

	@Autowired
	private SysMenuDao dao;
	@Autowired
	private SysRoleMenuDao rmDao;


	@Override
	public List<String> findPermissionsByUserId (Integer id) {
		Assert.isEmpty (id==null||id==0,"参数异常！");
		List<String> permissions = dao.findPermissionsByUserId (id);
		Assert.isEmpty (permissions==null,"当前用户无任何菜单权限！");
		return permissions;
	}

	//@RequiredCache
	//@Cacheable("menuCache") //使用缓存注解，指定名字
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = dao.findObjects();
		Assert.isEmpty(list == null || list.size() == 0, "菜单不存在！");
		return list;
	}

	public int deleteMenuById(Integer menuId) {
		//第一步：查看当前菜单id的子菜单数量
		int n = dao.getCountChildByMenuId(menuId);
		//第二步：如果子菜单数量大于0则直接抛出异常
		Assert.isEmpty(n > 0, "有子菜单不允许删除！");
		//第三步：如果子菜单数量等于0则删除菜单与权限的关系数据
		rmDao.deleteRoleMenuByMenuId(menuId);
		//第四步：最后根据该id删除菜单
		int t = dao.deleteMenuById(menuId);
		Assert.isEmpty(t == 0, "菜单可能已被删除！");
		return t;
	}

	public List<Node> findZtreeMenuNodes() {
		List<Node> list = dao.findZtreeMenuNodes();
		Assert.isEmpty(list == null || list.size() == 0, "菜单不存在！");
		return list;
	}

	public int insertMenu(SysMenu menu) {
		Assert.isEmpty(menu == null || menu.getName() == null, "菜单名不能为空！");
		int n = dao.insertMenu(menu);
		Assert.isEmpty(n == 0, "菜单添加失败！");
		return n;
	}

	@CacheEvict(value = "menuCache",allEntries = true)//清空指定key的缓存  allEntries=true 清空cache
	public int updateMenu(SysMenu menu) {
		Assert.isEmpty(menu == null || menu.getId() == null, "请填写数据！");
		int n = dao.updateMenu(menu);
		Assert.isEmpty(n == 0, "菜单修改失败或数据已不存在！");
		return n;
	}

}
