package com.github.xhrg.layout.pojo.util;

import java.util.Collections;
import java.util.List;

public class PageUtils {

	/**
	 * @param list
	 * @param pageSize
	 * @param pageNum  从1开始
	 * @return
	 */
	public static <T> List<T> getPage(List<T> list, int pageNo, int pageSize) {
		int start = (pageNo - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());
		if (start >= end) {
			return Collections.emptyList();
		} else {
			return list.subList(start, end);
		}
	}
}
