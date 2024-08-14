package com.github.xhrg.netty.tcp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyTcpServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("服务端收到请求：" + msg);
		ctx.channel().writeAndFlush("你好" + msg + "服务端收到了");
	}
}
