package com.zzxy.pj.sys.entity;

import java.util.Date;

import lombok.Data;

//部门
@Data
public class SysDept {
	private Integer id;
	private String name;
	private Integer parentId;//上级部门
	private Integer soft;//排序
	private String note;//描述
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
