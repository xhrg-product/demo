package com.github.xhrg.demo.rocksdb;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.DBOptions;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

public class MyQueue {

	private RocksDB db;

	private Map<String, TopicHandler> topicHandlerMap;

	private DBOptions options;

	public static void main(String[] args) throws RocksDBException {
		MyQueue q = new MyQueue("d:/temp/v4");
		q.write("a", "ccd");
	}

	public MyQueue(String path) throws RocksDBException {
		topicHandlerMap = new ConcurrentHashMap<>();

		List<byte[]> columnFamilies = RocksDB.listColumnFamilies(new Options(), path);
		List<ColumnFamilyDescriptor> cfDescriptors = new ArrayList<>();
		for (byte[] cf : columnFamilies) {
			cfDescriptors.add(new ColumnFamilyDescriptor(cf));
		}
		List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		options = new DBOptions();
		options.setCreateIfMissing(true);
		db = RocksDB.open(options, path, cfDescriptors, columnFamilyHandles);

		for (ColumnFamilyHandle h : columnFamilyHandles) {
			RocksIterator it = db.newIterator(h);
			it.seekToLast();
			String s = new String(it.key(), StandardCharsets.UTF_8);
			if (s == null || s.isEmpty()) {
				s = "0";
			}
			TopicHandler t = new TopicHandler();
			t.setId(new AtomicLong(Long.parseLong(s)));
//			t.setOption(option);
			t.setTopicHandle(h);
			topicHandlerMap.put(new String(h.getName()), t);
		}

	}

	public long write(String topic, String data) throws RocksDBException {
		TopicHandler topicHandler = topicHandlerMap.get(topic);
		long id = topicHandler.getId().incrementAndGet();
		db.put(topicHandler.getTopicHandle(), (id + "").getBytes(), "a".getBytes());
		return id;
	}

	private TopicHandler init(String topic) {
		ColumnFamilyOptions option = new ColumnFamilyOptions();
		option.setTtl((TimeUnit.DAYS.toMillis(3)));
		try {
			ColumnFamilyHandle cfh = db.createColumnFamily(new ColumnFamilyDescriptor(topic.getBytes(), option));
			RocksIterator it = db.newIterator(cfh);
			it.seekToLast();
			String s = new String(it.key(), StandardCharsets.UTF_8);
			if (s == null || s.isEmpty()) {
				s = "0";
			}
			TopicHandler t = new TopicHandler();
			t.setId(new AtomicLong(Long.parseLong(s)));
			t.setOption(option);
			t.setTopicHandle(cfh);
			return t;
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {

	}

	static class TopicHandler {
		private AtomicLong id;
		private ColumnFamilyHandle topicHandle;
		private ColumnFamilyOptions option;

		public ColumnFamilyOptions getOption() {
			return option;
		}

		public void setOption(ColumnFamilyOptions option) {
			this.option = option;
		}

		public AtomicLong getId() {
			return id;
		}

		public void setId(AtomicLong id) {
			this.id = id;
		}

		public ColumnFamilyHandle getTopicHandle() {
			return topicHandle;
		}

		public void setTopicHandle(ColumnFamilyHandle topicHandle) {
			this.topicHandle = topicHandle;
		}
	}
}
