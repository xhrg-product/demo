package com.github.xhrg.netty.gateway.back;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EmptyHandler extends ChannelInboundHandlerAdapter {

	private static AtomicInteger i = new AtomicInteger(0);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bs = (ByteBuf) msg;
		bs = bs.copy();

		byte[] req = new byte[bs.readableBytes()];
		bs.readBytes(req);

		System.out.println(i.incrementAndGet() + " ==== " + Arrays.toString(req));
		super.channelRead(ctx, msg);
	}

}