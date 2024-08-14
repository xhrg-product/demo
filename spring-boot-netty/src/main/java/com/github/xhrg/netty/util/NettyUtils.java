package com.github.xhrg.netty.util;

import io.netty.channel.epoll.Epoll;

public class NettyUtils {

	public static boolean useEpoll() {
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("linux") && Epoll.isAvailable()) {
			return true;
		}
		return false;
	}
}
