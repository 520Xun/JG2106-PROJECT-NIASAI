package com.zzxy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.sys.entity.SysDept;
import com.zzxy.pj.sys.service.SysDeptService;

@RestController
@RequestMapping("dept")
public class SysDeptController {
	
	@Autowired
	private SysDeptService sysDeptServiceImpl;
	
	@RequestMapping("findDeptZtree")
	public JsonResult findDeptZtree() {
		List<SysDept> list=sysDeptServiceImpl.findAllDept();
		return new JsonResult(list);
	}
	
	@RequestMapping("doDeleteDept")
	public JsonResult doDeleteDept(Integer id) {
		Integer n=sysDeptServiceImpl.deleteDept(id);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("删除成功！");
		return jr;
	}
	@RequestMapping("doSaveDept")
	public JsonResult doSaveDept(SysDept dept) {
		Integer n=sysDeptServiceImpl.insertDept(dept);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("新增成功！");
		return jr;
	}
	
	@RequestMapping("doUpdateDept")
	public JsonResult doupdateDept(SysDept dept) {
		Integer n=sysDeptServiceImpl.updateDept(dept);
		JsonResult jr = new JsonResult(n);
		jr.setMessage("修改成功！");
		return jr;
	}
}
