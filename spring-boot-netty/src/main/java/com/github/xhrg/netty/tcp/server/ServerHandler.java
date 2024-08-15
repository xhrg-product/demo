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

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server: " + ctx);
		System.out.println("server: " + ctx.channel().isActive());
		System.out.println("server: " + ctx.channel().isOpen());
		System.out.println("server: " + ctx.channel().isRegistered());
		System.out.println("server: " + ctx.channel().isWritable());
//		
//		server: false
//		server: false
//		server: true
//		server: false
		
//		client: false
//		client: false
//		client: true
//		client: false
		
		ctx.channel().close();
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

	@Override
	protected void log(String msg) throws Exception {

	}
}
