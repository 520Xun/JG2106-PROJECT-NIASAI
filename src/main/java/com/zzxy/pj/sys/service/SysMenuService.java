package com.zzxy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.zzxy.pj.common.entity.Node;
import com.zzxy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	/**
	 * 获取当前用户的菜单标识
	 * @param id
	 * @return
	 */
     List<String> findPermissionsByUserId (Integer id);

    /**
	 * 找所有菜单及父菜单名字
	 * @return
	 */
	public List<Map<String, Object>> findObjects();
	
	/**
	 * 根据id删除菜单
	 * @return
	 */
	int deleteMenuById(Integer menuId);
	
	/**
	 * 找菜单id，菜单名，父菜单id
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	int insertMenu(SysMenu menu);
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	int updateMenu(SysMenu menu);
}
