package com.zzxy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zzxy.pj.sys.entity.SysUser;
import com.zzxy.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {

	/**
	 * 严重用户名
	 */
	@Select ("select * from sys_users  where username=#{username}")
		SysUser checkUserName(String username);
	
	/**
	 * 查找用户和部门VO信息
	 * @param username
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptVo> findUserDeptVo(@Param("username") String username,
							@Param("start")Integer start,
							@Param("pageSize") Integer pageSize);
	
	/**
	 * 根据名称统计总条数
	 * @param username
	 * @return
	 */
	Integer getCountByUser(String username);
	
	/**
	 * 通过用户id，修改账号禁用或者启用
	 * @param id
	 * @param valId
	 * @return
	 */
	@Update("update sys_users set valId=#{valId} where id=#{id}")
	int updateValId(@Param("id") Integer id,@Param("valId") Integer valId);
	
	/**
	 * 根据用户名查找用户信息
	 * @param username
	 * @return
	 */
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByName(String username);
	
	/**
	 *	插入用户
	 * @param user
	 * @return
	 */
	int insertUser(SysUser user);
	
	/**
	 * 更新用户与部门信息
	 * @param deptId
	 * @return
	 */
	@Update("update sys_users set deptId=null where deptId=#{deptId}")
	int updateDeptId(Integer deptId);

	/**
	 * 更新密码
	 * @param password
	 * @param salt
	 * @param id
	 * @return
	 */
	@Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where id=#{id}")
	int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);

	/**
     * 通过用户id 修改用户信息
     *
     * @param user
     * @return
     */
    int updateUser(SysUser user);
}
