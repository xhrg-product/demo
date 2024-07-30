package com.github.xhrg.layout.pojo.util;

import org.slf4j.MDC;

public class LogUtils {

	public static final String TRACE_ID = "traceid";

	public static void mdc() {
		MDC.put(TRACE_ID, StrUtils.uuid());
	}
}
