package com.zzxy.pj.sys.service;

import java.util.List;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysRole;
import com.zzxy.pj.sys.vo.SysRoleMenuVo;

//角色
public interface SysRoleService {

	/**
	 * 查询角色
	 * @param name	角色名
	 * @param curPage	当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	Pagination findRole(String name, Integer curPage, Integer pageSize);
	
	/**
	 * 通过角色id删除角色
	 * @param id
	 * @return
	 */
	int deleteRoleById(Integer id);
	/**
	 * 添加角色
	 * @param role
	 * @param ids
	 * @return
	 */
	int insertRole(SysRole role, Integer[] ids);
	
	SysRoleMenuVo findRoleMenuIds(Integer id);
	
	/**
	 * 根据角色id修改角色和对应菜单
	 * @param vo
	 * @return
	 */
	int updateRoleById(SysRoleMenuVo vo);
	/**
	 * 找所有role(查询id和name)
	 * @return
	 */
	List<SysRole> findAllRole();
}
