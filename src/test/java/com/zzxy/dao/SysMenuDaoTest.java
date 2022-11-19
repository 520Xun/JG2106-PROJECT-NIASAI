package com.zzxy.dao;

import java.util.List;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zzxy.pj.common.entity.Node;
import com.zzxy.pj.sys.dao.SysMenuDao;
import com.zzxy.pj.sys.entity.SysMenu;

@SpringBootTest
public class SysMenuDaoTest {

	@Autowired
	private SysMenuDao dao;

	@Test
	public void findObjectTest() {
		List<Map<String,Object>> list = dao.findObjects();
		for (Map<String,Object> map: list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void getCountChildByMenuIdTest() {
		int n = dao.getCountChildByMenuId(8);
		System.out.println(n);
	}
	
	@Test
	public void findZtreeMenuNodeTest() {
		List<Node> list = dao.findZtreeMenuNodes();
		for (Node node : list) {
			System.out.println(node);
		}
	}
	
	@Test
	public void insertMenuTest() {
		SysMenu menu = new SysMenu(null, "宿舍管理", "ss", 1, 200, null, 0, "aaa", null, null, null, null);
		int n = dao.insertMenu(menu);
		System.out.println(n);
	}
	
	@Test
	public void updateMenu() {
		SysMenu menu = new SysMenu(136, "食堂管理", "sturl", 1, 200, null, 0, "aaa", null, null, null, null);
		int n = dao.updateMenu(menu);
		System.out.println(n);
	}
}