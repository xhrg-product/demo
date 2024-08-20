package com.github.xhrg.demo.mapdb;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Main {

	public static void main(String[] args) {
		DB db = DBMaker.fileDB("d:/temp/mapdb").make();
		ConcurrentMap map = db.hashMap("map").create();
		map.put("something", "here");
	}
}
