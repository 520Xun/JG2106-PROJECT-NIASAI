package com.zzxy.pj.sys.controller;

import com.zzxy.pj.common.util.ShiorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.sys.entity.SysMenu;
import com.zzxy.pj.sys.service.SysMenuService;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

	@Autowired
	private SysMenuService service;
	
	/**
	 * 查询所有菜单及父菜单名称
	 * @return
	 */
	@RequestMapping("doFindMenus")
	public JsonResult doFindObjects() {
		return new JsonResult(service.findObjects());
	}
	
	/**
	 * 根据菜单id删除菜单（有子菜单不允许删除）
	 * @param menuId
	 * @return
	 */
	@RequestMapping("doDeleteMenu")
	public JsonResult deleteMenuById(Integer menuId) {
		return new JsonResult(service.deleteMenuById(menuId));
	}
	
	@RequestMapping("findZtreeMenuNodes")
	public JsonResult findZtreeMenuNodes() {
		return new JsonResult(service.findZtreeMenuNodes());
	}
	
	@RequestMapping("saveMenu")
	public JsonResult saveMenu(SysMenu menu) {
			JsonResult jr = new JsonResult(service.insertMenu(menu));
			jr.setMessage("添加成功！");//设置提示信息
			return jr;
	}
	
	@RequestMapping("updateMenu")
	public JsonResult updateMenu(SysMenu menu) {
		JsonResult jr = new JsonResult(service.updateMenu(menu));
		jr.setMessage("修改成功！");//设置提示信息
		return jr;
	}

	/**
	 * 获取当前用户的菜单标识
	 * @return
	 */
	@RequestMapping("getPermissions")
	public  JsonResult getPermissions(){
		List<String> list=service.findPermissionsByUserId(ShiorUtil.getLoginUser ().getId ());
		return  new JsonResult (list);
	}
}
