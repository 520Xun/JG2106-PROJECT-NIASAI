package com.zzxy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable{
	private static final long serialVersionUID = 6125283417073218169L;
	private Integer id;
	private String name;
	private String note;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifiedTime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String createdUser;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String modifiedUser;
	
}
