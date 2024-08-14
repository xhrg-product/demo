package com.github.xhrg.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyTcpServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("服务端收到请求：" + msg);
		ctx.channel().writeAndFlush("你好" + msg + "服务端收到了");
	}
}
