package com.github.xhrg.demo.rocksdb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class MyQueue {

	private RocksDB db;

	private Map<String, TopicHandler> topicHandler;

	private Options options;

	public MyQueue(String path) throws RocksDBException {
		options = new Options();
		options.setCreateIfMissing(true);
		db = RocksDB.open(options, path);
		topicHandler = new ConcurrentHashMap<>();
	}

	public long write(String topic, String data) {

		return 1;
	}

	public void close() {

	}

	static class TopicHandler {
		private AtomicLong id;
		private ColumnFamilyHandle topicHandle;
	}
}
