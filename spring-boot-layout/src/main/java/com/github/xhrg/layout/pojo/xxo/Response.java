package com.github.xhrg.layout.pojo.xxo;

public class Response {

	// 返回接口的code, 成功 1， 弹出提示框 2， 跳转到登录 3
	private int code;

	// 返回数据
	private Object data;

	// 弹出框内容
	private String msg;

	public static Response success(Object data) {
		Response response = new Response();
		response.setCode(1000);
		response.setData(data);
		return response;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}