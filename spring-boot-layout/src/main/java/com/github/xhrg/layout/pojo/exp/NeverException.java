package com.github.xhrg.layout.pojo.exp;

/**
 * 系统绝对不可能发生的异常，出现该异常一定要慎重
 */
public class NeverException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NeverException(String message) {
		super(message);
	}

}