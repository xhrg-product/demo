package com.github.xhrg.netty.tcp.client;

import com.github.xhrg.netty.tcp.cmm.NoteSimpleChannelInboundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHandler extends NoteSimpleChannelInboundHandler<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
		ctx.channel().close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client: "+ctx.channel().isActive());
		System.out.println("client: "+ctx.channel().isOpen());
		System.out.println("client: "+ctx.channel().isRegistered());
		System.out.println("client: "+ctx.channel().isWritable());
		System.out.println("client: "+ctx);
//		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("客户端收到响应：" + msg);
	}

	@Override
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
