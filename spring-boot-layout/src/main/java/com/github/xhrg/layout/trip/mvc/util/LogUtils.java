package com.github.xhrg.layout.trip.mvc.util;

import java.util.UUID;

import org.slf4j.MDC;

public class LogUtils {

	public static final String TRACE_ID = "txid";

	public static void mdc() {
		MDC.put(TRACE_ID, UUID.randomUUID().toString());
	}
}