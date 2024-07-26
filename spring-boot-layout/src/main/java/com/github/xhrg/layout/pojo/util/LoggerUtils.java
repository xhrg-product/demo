package com.github.xhrg.layout.pojo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

	/**
	 * 打印理论不可能发生的日志
	 * 
	 * @param msg
	 * @param args
	 */
	public void impossible(String msg, Object... args) {
		LOGGER.error("an impossible event has occurred, please take it seriously。(不可能事件发生，请重视。) " + msg, args);
	}
}
