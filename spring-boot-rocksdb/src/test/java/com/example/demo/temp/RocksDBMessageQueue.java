package com.example.demo.temp;

import java.nio.charset.StandardCharsets;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

/**
 *  WriteBatch
 *  
 */
public class RocksDBMessageQueue {

    private RocksDB db;

    public RocksDBMessageQueue(String path) throws RocksDBException {
        Options options = new Options().setCreateIfMissing(true);
        db = RocksDB.open(options, path);
    }

    public void push(String message) throws RocksDBException {
        byte[] key = Long.toString(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
        byte[] value = message.getBytes(StandardCharsets.UTF_8);
        WriteBatch batch = new WriteBatch();
        batch.put(key, value);
        db.write(new WriteOptions(), batch);
    }

    public String pop() throws RocksDBException {
        RocksIterator iterator = db.newIterator();
        iterator.seekToFirst();
        if (iterator.isValid()) {
            byte[] key = iterator.key();
            byte[] value = db.get(key);
            db.delete(key);
            return new String(value, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    public void close() {
        db.close();
    }

    public static void main(String[] args) throws RocksDBException {
        RocksDBMessageQueue queue = new RocksDBMessageQueue("/path/to/rocksdb");
        queue.push("Message 1");
        queue.push("Message 2");
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        queue.close();
    }
}