package com.github.xhrg.demo.rocksdb;

import org.rocksdb.RocksDBException;

public class Test {

	public static void main(String[] args) throws RocksDBException {
		RocksdbQueue q = new RocksdbQueue("d:/temp/v7");
		long id1 = q.put("topicaa", "ccd");
		long id2 = q.put("topicbb", "ccd3333333333333333333333333333333333");
		System.out.println(id1);
		System.out.println(id2);

		String data = q.get("topicaa", id1);
		System.out.println(data);
		String datab = q.get("topicbb", id2);
		System.out.println(datab);
	}
}
