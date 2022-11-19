package com.zzxy.pj.sys.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zzxy.pj.sys.entity.SysDept;

@Mapper
public interface SysDeptDao {
	
	/**
	 * 根据部门id查找部门信息
	 * @param deptId
	 * @return
	 */
	@Select("select * from sys_depts where id=#{deptId}")
	SysDept findDeptById(Integer deptId);
	
	/**
	 * 查找所有部门
	 * @return
	 */
	@Select("select * from sys_depts")
	List<SysDept> findAllDept();

	@Delete("delete from sys_depts where id=#{id}")
	Integer deleteDept(Integer id);
	
	/**
	 * 统计下级部门的数量
	 * @param id
	 * @return
	 */
	@Select("select count(1) c from sys_depts where parentId=#{id}")
	Integer getCountChildByMenuId(Integer id);
	
	/**
	 * 新增部门
	 * @param dept
	 * @return
	 */
	Integer insertDept(SysDept dept);
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	Integer updateDept(SysDept dept);
}
