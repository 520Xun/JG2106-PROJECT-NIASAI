package com.zzxy.pj.sys.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysLog;
import com.zzxy.pj.sys.service.SysLogService;

//@Controller
@RestController//相当于@Controller + @ResponseBody
@RequestMapping("/log")
public class SysLogController {

	@Autowired
	private SysLogService service;
	
	@RequestMapping("findLogObjects")
	public JsonResult findLogObjects(String username,@RequestParam(defaultValue="0") Integer pageSize,@RequestParam(defaultValue="1") Integer curPage) {
		Pagination pageObj = service.findLogByUsername(username, curPage, pageSize);
		return new JsonResult(pageObj);
	}
	
	@RequestMapping("deleteLog")
	public JsonResult doDeleteLog(@RequestParam("ids[]") Integer[] ids) {
		int n = service.doDeleteLog(ids);
		return new JsonResult(n);
		}
	}
