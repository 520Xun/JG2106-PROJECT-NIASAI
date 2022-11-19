package com.zzxy.pj.sys.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SysRoleMenuVo implements Serializable{
	private static final long serialVersionUID = 8661919592606052059L;
	private Integer id;//角色id
	private String name;//角色名
	private String note;//描述
	private List<Integer> menuIds;//角色对应的菜单集合
}
