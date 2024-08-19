package com.github.xhrg.demo.rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class Main1 {

	private static final String dbPath = "d:/temp/rocksdb";
//	private static final String cfdbPath = "temp/rocksdb-data-cf/";

	public static void main(String[] args) throws RocksDBException {

		Options options = new Options();
		options.setCreateIfMissing(true);

		RocksDB rocksDB = RocksDB.open(options, dbPath);

		byte[] key = "Hello".getBytes();
		rocksDB.put(key, "World".getBytes());

		byte[] val = rocksDB.get(key);
		System.out.println(new String(val));

	}
}
