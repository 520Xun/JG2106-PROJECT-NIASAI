package com.zzxy.pj.sys.service;

import java.util.List;

import com.zzxy.pj.sys.entity.SysDept;

public interface SysDeptService {
	/**
	 * 查找所有部门信息(id,name,parentId)
	 * @return
	 */
	List<SysDept> findAllDept();
	
	/**
	 * 根据id删除部门信息
	 * @param id
	 * @return
	 */
	Integer deleteDept(Integer id);
	
	/**
	 * 新增部门
	 * @param dept
	 * @return
	 */
	Integer insertDept(SysDept dept);
	
	/**
	 * 修改部门信息
	 * @param dept
	 * @return
	 */
	Integer updateDept(SysDept dept);

}
