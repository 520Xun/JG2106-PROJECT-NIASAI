package com.zzxy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzxy.pj.sys.entity.SysLog;

//日志数据层
@Mapper
public interface SysLogDao {

	/**
	 * 按用户名查找日志总条数
	 * @param username
	 * @return
	 */
	int getRowCount(String username);
	
	/**
	 * 按用户名查找日志信息且分页
	 * @param username	用户名
	 * @param start	开始条数(service中处理)
	 * @param pageSize	查询的条数
	 * @return
	 */
	List<SysLog> findLogObject(@Param("username") String username,@Param("start") Integer start,@Param("pageSize") Integer pageSize);

	/**
	 * 按id删除日志信息
	 * @param ids
	 * @return
	 */
	int deleteLogByIds(Integer[] ids);

    List<SysLog> findLogObjectByUserName(String username);

	@Insert("INSERT INTO sys_logs(`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `createdTime`) VALUES (null, #{username},#{operation},#{method}, #{params},#{time},#{ip}, #{createdTime});")
	public int insertLog(SysLog log);
}
