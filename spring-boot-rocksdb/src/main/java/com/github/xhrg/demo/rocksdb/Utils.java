package com.github.xhrg.demo.rocksdb;

import java.nio.charset.StandardCharsets;

public class Utils {

	public static String bytes2str(byte[] bytes) {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static byte[] str2bytes(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	public static byte[] id2bytes(long id) {
		return (id + "").getBytes(StandardCharsets.UTF_8);
	}
}
