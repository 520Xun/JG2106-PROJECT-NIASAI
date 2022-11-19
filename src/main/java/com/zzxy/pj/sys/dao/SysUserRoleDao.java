package com.zzxy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色Dao层
 * @author Liu23
 *
 */
@Mapper
public interface SysUserRoleDao {

	/**
	 * 通过角色id删除用户角色关系数据
	 * @param roleId
	 * @return
	 */
	@Delete("delete from sys_user_roles where role_id = #{id}")
	int deteleUserRoleByRoleId(Integer roleId);
	
	/**
	 * 插入用户和角色关系数据
	 * @param id
	 * @param roleIds
	 * @return
	 */
	int insertUserRole(@Param("userId")Integer id,
						@Param("roleIds") Integer[] roleIds);

	/**
	 * 根据用户id查询角色id
	 * @param userId
	 * @return
	 */
	@Select("select role_id from sys_user_roles where user_id=#{userId}")
    List<Integer> findRoleByUserId(Integer userId);

	/**
	 * 根据用户id删除用户角色信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_user_roles where user_id=#{id}")
	int deteleUserRoleByUserId(Integer id);
}
