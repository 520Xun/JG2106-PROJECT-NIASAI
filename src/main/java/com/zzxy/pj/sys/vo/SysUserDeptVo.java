package com.zzxy.pj.sys.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzxy.pj.sys.entity.SysDept;

import lombok.Data;

@Data
public class SysUserDeptVo {
	private Integer id;
	private String username;
	private String password;//md5
	private String salt;//盐
	private String email;
	private String mobile;
	private Integer valid=1;//是否禁用 1 启动 0 禁用
	//private Integer deptId;
	private SysDept sysDept;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;

}
