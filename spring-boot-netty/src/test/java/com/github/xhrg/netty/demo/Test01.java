package com.github.xhrg.netty.demo;

import io.netty.channel.ChannelHandlerContext;

public class Test01 {

	public static void main(String[] args) {

		ChannelHandlerContext ctx = null;
		ctx.channel().closeFuture();
	}
}
