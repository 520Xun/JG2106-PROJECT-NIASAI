package com.zzxy.pj.sys.service;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysUser;

import java.util.List;

public interface SysUserService {
	
	/**
	 * 通过名称查找用户部门Vo对象
	 * @param name
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	Pagination findUserDeptVo(String name,Integer curPage,Integer pageSize);
	
	
	/**
	 * 
	 * @param id
	 * @param valId
	 * @return
	 */
	int updateValId(Integer id,Integer valId);
	
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	int insertUser(SysUser user,Integer[] roleIds) ;

	/**
	 * 根据用户id查找角色id
	 * @param userId
	 * @return
	 */
    List<Integer> findRoleByUserId(Integer userId);


	/**
	 * 修改密码
	 * @param password  原先密码
	 * @param newPassword 新密码
	 * @param cfgPassword 	确认密码
	 * @return
	 */
	Integer updatePassWord(String password,String newPassword,String cfgPassword);

	/**
     * 修改用户
     *
     * @param user
     * @param roleIds
     * @return
     */
    int updateUser(SysUser user, Integer[] roleIds);
}
