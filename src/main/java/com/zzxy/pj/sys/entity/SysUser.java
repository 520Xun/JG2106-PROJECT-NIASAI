package com.zzxy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
	private Integer id;
	private String username;
	private String password;//md5
	private String salt;//盐
	private String email;
	private String mobile;
	private Integer valid=1;//是否禁用 1 启动 0 禁用
	private Integer deptId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
