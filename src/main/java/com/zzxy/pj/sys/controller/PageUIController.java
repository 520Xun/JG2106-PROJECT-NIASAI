package com.zzxy.pj.sys.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//提问：Controller的注解所在的类在项目启动后立即创建对象，为什么这样？
//@Scope("prototype")原型模式
//@Scope("singleton")单例模式
//@Lazy延迟加载
//@RestController	相当于@Controller + @ResponseBody
public class PageUIController {
	
	//该对象利用CPU的算法保证了线程安全
	private AtomicLong times = new AtomicLong();

	public PageUIController() {
		System.out.println("哈哈哈哈哈哈哈");
	}
	@RequestMapping("doIndexUI")
	public String indexUI(Map<String, Object> map) {
		long n = times.incrementAndGet();//加1的方法
		System.out.println(n);
		map.put("username", "admin");
		return "starter";
	}

	@RequestMapping("doLoginUI")
	public String loginUI() {
		//加1的方法
		long n = times.incrementAndGet();
		System.out.println(n);
		return "login";
	}
	
	@RequestMapping("map")
	public String mapUI() {
		return "index";
	}
//	
//	/**
//	 * 菜单管理主页入口
//	 * @return
//	 */
//	@RequestMapping("sys/menu_list")
//	public String doLogPageUI() {
//		return "sys/menu_list";
//	}
	//"{module}/{page}": 可以统一接收访问路径
	//参数需要加@PathVariable("page")注解标注匹配
	@RequestMapping("{module}/{page}")
	public String doModuleUI(@PathVariable("page") String page) {
		return "sys/" + page;
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
}
