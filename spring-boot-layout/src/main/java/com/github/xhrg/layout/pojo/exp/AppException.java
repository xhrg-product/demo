package com.github.xhrg.layout.pojo.exp;

/**
 * 系统异常，出现该异常后，不需要管理员介入的异常。
 * 例如：登录用户名密码错误;Topic的名称不合规范。
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code = 0;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}