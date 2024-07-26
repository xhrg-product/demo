package com.github.xhrg.layout.pojo.util;

public class UserUtils {

	private static final String SPIT = ",";

	public static boolean contains(String all, String one) {
		if (StrUtils.isEmpty(all) || StrUtils.isEmpty(one)) {
			return false;
		}
		String[] array = all.split(SPIT);
		for (String a : array) {
			if (one.equals(a)) {
				return true;
			}
		}
		return false;
	}
}
