package com.zzxy.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zzxy.pj.common.entity.Pagination;
import com.zzxy.pj.sys.entity.SysLog;
import com.zzxy.pj.sys.service.SysLogService;

@SpringBootTest
public class SysLogServiceTest {
	
	@Autowired
	private SysLogService service;
	
	@Test
	public void findLogByUsernameTest() {
		Pagination page = service.findLogByUsername("ad", 2, 10);
		List<?> list = page.getPageData();
		for(Object sysLog : list) {
			System.out.println(sysLog);
		}
	}
}
