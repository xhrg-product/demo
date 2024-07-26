package com.github.xhrg.layout.pojo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date now() {
		return new Date();
	}

	public static String nowText() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now());
	}
}
