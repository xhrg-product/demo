package com.example.demo.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class RocksDBQueue {

	private RocksDB db;
	private ColumnFamilyHandle defaultHandle;
	private Map<String, ColumnFamilyHandle> topicHandles;

	public RocksDBQueue(String path) throws RocksDBException {
		RocksDB.loadLibrary();
		Options options = new Options();
		options.setCreateIfMissing(true);

		db = RocksDB.open(options, path);
		defaultHandle = db.getDefaultColumnFamily();
		topicHandles = new HashMap<>();
	}

	public void put(String topic, int index, byte[] data) throws RocksDBException {
		ColumnFamilyHandle topicHandle = topicHandles.get(topic);
		if (topicHandle == null) {
			ColumnFamilyOptions option = new ColumnFamilyOptions();
			option.setTtl((TimeUnit.DAYS.toMillis(3)));
			topicHandle = db.createColumnFamily(new ColumnFamilyDescriptor(topic.getBytes(), option));
			topicHandles.put(topic, topicHandle);

		}
		db.put(topicHandle, topic.getBytes(), data);
	}

	public void close() {
		for (ColumnFamilyHandle handle : topicHandles.values()) {
			handle.close();
		}
		db.close();
		defaultHandle.close();
	}
}