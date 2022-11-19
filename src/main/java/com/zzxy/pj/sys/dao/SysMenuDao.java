package com.zzxy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zzxy.pj.common.entity.Node;
import com.zzxy.pj.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysMenuDao {

	/**
	 * 查找所有菜单及父菜单
	 * @return
	 */
	List<Map<String, Object>> findObjects();

	/**
	 * 根据父菜单id查找子菜单数量
	 * @param menuId
	 * @return
	 */
	int getCountChildByMenuId(int menuId);

	/**
	 * 根据菜单id删除菜单
	 * @param menuId
	 * @return
	 */
	int deleteMenuById(Integer menuId);
	
	/**
	 * 查询菜单id，菜单名和父菜单id
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 插入菜单
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

	/**
	 * 菜单id查找权限标识的方法
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(@Param ("menuIds")Integer[] menuIds);

	/**
	 * 根据当前用户查找对应菜单标识
	 * @param userId
	 * @return
	 */
	List<String> findPermissionsByUserId (Integer userId);
}
