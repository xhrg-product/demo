package com.github.xhrg.layout.pojo.exp;

/**
 * 系统异常，出现该异常后，一定需要管理员接入处理问题
 */
public class AdminException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminException(String message) {
		super(message);
	}

	public AdminException(String message, Throwable cause) {
		super(message, cause);
	}
}