package com.zzxy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zzxy.pj.sys.entity.SysRole;
import com.zzxy.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {

	/**
	 * 根据角色名找角色
	 * @param name
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<SysRole> findRole(
			@Param("name") String name, 
			@Param("start") Integer start, 
			@Param("pageSize") Integer pageSize);

	/**
	 * 统计角色总条数，用来分页
	 * @param name
	 * @return
	 */
	int countRole(String name);
	
	/**
	 * 根据id删除角色
	 * @return
	 */
	@Delete("delete from sys_roles where id = #{id}")
	int deleteRoleById(Integer id);
	
	/**
	 * 插入角色
	 * @param role
	 * @return
	 */
	int insertRole(SysRole role);
	
	/**
	 * 通过角色id查找角色数据和角色对应的菜单id
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findRoleMenuIds(Integer id);
	
	/**
	 * 根据角色id修改角色信息
	 * @param vo
	 * @return
	 */
	int updatRoleByRoleId(SysRoleMenuVo vo);
	
	/**
	 * 查询所有所有角色
	 * @return
	 */
	@Select("select id,name from  sys_roles")
	List<SysRole> findAllRole();
	
}
