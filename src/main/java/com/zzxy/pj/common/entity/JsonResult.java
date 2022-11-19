package com.zzxy.pj.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResult {
	//状态，1：ok	0：no
	private Integer state = 1;
	//成功数据
	private Object data;
	//异常文本描述
	private String message;
	public JsonResult(Throwable e) {
		//状态码改为0
		this.state = 0;
		this.message = e.getMessage();
	}
	public JsonResult(Object data) {
		this.data = data;
	}

}
