package com.example.demo.temp;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

public class Main1 {

	private static final String dbPath = "d:/temp/rocksdb";
//	private static final String cfdbPath = "temp/rocksdb-data-cf/";

	public static void main(String[] args) throws RocksDBException {

		Options options = new Options();
		options.setCreateIfMissing(true);

		RocksDB db = RocksDB.open(options, dbPath);

		RocksIterator iterator = db.newIterator();

		for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
			System.out.println(new String(iterator.key()) + " : " + new String(iterator.value()));
		}

	}
}
