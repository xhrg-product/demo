package com.github.xhrg.netty.tcp.cmm;

public class StringMessage {

	// 1. ping/pong
	// 2. GET
	// 3. SET
	// 4. xxx
	private String type;

	private String data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
