package com.zzxy.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zzxy.pj.sys.dao.SysLogDao;
import com.zzxy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogDaoTest {
	
	@Autowired
	private SysLogDao dao;
	
	@Test
	public void getRowCountTest() {
		int count = dao.getRowCount("ad");
		System.out.println(count);
	}
	
	@Test
	public void findLogObjectTest() {
		List<SysLog> list = dao.findLogObject("ad", 3, 5);
		for(SysLog sysLog : list) {
			System.out.println(sysLog);
		}
	}

}
