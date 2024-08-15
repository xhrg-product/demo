package com.github.xhrg.netty.tcp.server;

import com.github.xhrg.netty.tcp.cmm.NoteSimpleChannelInboundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends NoteSimpleChannelInboundHandler<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("服务端收到请求：" + msg);
		ctx.channel().writeAndFlush("你好" + msg + "服务端收到了");
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		IdleStateEvent event = (IdleStateEvent) evt;
		if (event.state() == IdleState.ALL_IDLE) {
			ctx.channel().close();
			return;
		}
		System.out.println("其他异常" + evt + ctx);
	}
}
