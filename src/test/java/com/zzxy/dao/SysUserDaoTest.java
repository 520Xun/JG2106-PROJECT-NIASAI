package com.zzxy.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zzxy.pj.sys.dao.SysUserDao;
import com.zzxy.pj.sys.vo.SysUserDeptVo;

@SpringBootTest
public class SysUserDaoTest {
	
	@Autowired
	private SysUserDao userDao;
	
	@Test
	public void findUserTest() {
		List<SysUserDeptVo> userVo = userDao.findUserDeptVo(null, 0, 10);
		for (SysUserDeptVo s : userVo) {
			System.out.println(s);
		}
	}
	
	@Test
	public void getCountTest() {
		Integer countByUser = userDao.getCountByUser(null);
		System.out.println(countByUser);
	}
}
