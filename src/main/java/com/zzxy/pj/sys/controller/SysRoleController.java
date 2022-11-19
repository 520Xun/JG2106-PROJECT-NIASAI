package com.zzxy.pj.sys.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysRole;
import com.zzxy.pj.sys.service.SysRoleService;
import com.zzxy.pj.sys.vo.SysRoleMenuVo;

@RestController
@RequestMapping("role")
public class SysRoleController {

	@Autowired
	private SysRoleService roleService;
	
	@RequestMapping("findAllRole")
	public JsonResult findAllRole() {
		List<SysRole> list=roleService.findAllRole();
		return  new JsonResult(list);
	}
	/**
	 * 通过角色名查找角色
	 * @param name	角色名，可以模糊查询
	 * @param curPage	当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	@RequestMapping("findRole")
	public JsonResult findRole(String name, Integer curPage, Integer pageSize) {
		Pagination obj = roleService.findRole(name, curPage, pageSize);
		return new JsonResult(obj);
	} 
	
	/**
	 * 通过id删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteRole")
	public JsonResult deleteRoleById(Integer id) {
		int n = roleService.deleteRoleById(id);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("删除成功");
		return jr;
	}
	/**
	 * 添加角色（还要给角色授权)
	 * @param role
	 * @param menuIds
	 * @return
	 * @RequestParam 数组需要处理
	 */
	@RequestMapping("saveRole")
	public JsonResult saveRole(SysRole role,@RequestParam("menuIds[]")Integer[] menuIds) {
		int n=roleService.insertRole(role,menuIds);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("添加成功");
		return jr;
	}
	
	@RequestMapping("findRoleMenuIdsByRoleId")
	public JsonResult findRoleMenuIdsByRoleId(Integer id) {
		SysRoleMenuVo roleMenuVo=roleService.findRoleMenuIds(id);
		return new JsonResult(roleMenuVo);
	}
	@RequestMapping("updateRoleById")
	public JsonResult updateRoleById(SysRoleMenuVo vo) {
		int n=roleService.updateRoleById(vo);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("修改成功");
		return jr;
	}

}
