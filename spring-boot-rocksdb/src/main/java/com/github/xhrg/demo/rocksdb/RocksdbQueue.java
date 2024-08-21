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

public class RocksdbQueue {

	private RocksDB db;

	private Map<String, TopicHandler> topicHandlerMap;

	private DBOptions options;

	public long put(String column, String data) throws RocksDBException {
		TopicHandler topicHandler = topicHandlerMap.get(column);
		if (topicHandler == null) {
			topicHandler = create(column);
			topicHandlerMap.put(column, topicHandler);
		}
		long id = topicHandler.getId().incrementAndGet();
		db.put(topicHandler.getTopicHandle(), Utils.id2bytes(id), Utils.str2bytes(data));
		return id;
	}

	public String get(String column, long id) throws RocksDBException {
		TopicHandler topicHandler = topicHandlerMap.get(column);
		if (topicHandler == null) {
			throw new RuntimeException("not colum " + column);
		}
		byte[] bs = db.get(topicHandler.getTopicHandle(), (id + "").getBytes());
		return new String(bs, StandardCharsets.UTF_8);
	}

	public RocksdbQueue(String path) throws RocksDBException {
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
			t.setTopicHandle(h);
			topicHandlerMap.put(new String(h.getName()), t);
		}
	}

	private TopicHandler create(String column) {
		// 待关闭
		ColumnFamilyOptions option = new ColumnFamilyOptions();
		option.setTtl((TimeUnit.DAYS.toMillis(3)));
		try {
			ColumnFamilyHandle cfh = db.createColumnFamily(new ColumnFamilyDescriptor(column.getBytes(), option));
			TopicHandler t = new TopicHandler();
			t.setId(new AtomicLong(1));
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
