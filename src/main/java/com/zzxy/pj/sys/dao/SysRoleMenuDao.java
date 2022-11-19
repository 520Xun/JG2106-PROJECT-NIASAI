package com.zzxy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zzxy.pj.sys.vo.SysRoleMenuVo;

/**
 * 权限和菜单关系dao层
 * @author Liu23
 *
 */
@Mapper
public interface SysRoleMenuDao {

	/**
	 * 通过菜单id删除权限菜单关系数据
	 * @param menuId
	 * @return
	 */
	int deleteRoleMenuByMenuId(Integer menuId);
	
	/**
	 * 通过角色id删除角色菜单关系数据
	 * @param roleId
	 * @return	
	 */
	@Delete("delete from sys_role_menus where role_id = #{roleId}")
	int deleteRoleMenuByRoleId(Integer roleId);
	
	/**
	 * 插入角色与菜单关系表的关系
	 * @param roleId 角色id
	 * @param menuIds 菜单id
	 * @return
	 */
	int insertRoleMenu(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	/**
	 * 跟据角色id查询菜单id
	 * @param id
	 * @return
	 */
	List<Integer> findMenuIdsByRoleId(Integer id);

	/**
	 * 根据 roleIds 查找角色对应的菜单Id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(@Param ("roleIds") Integer[] roleIds);

}
