package com.github.xhrg.netty.tcp.cmm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 这是一个笔记类，目的是为了说明白SimpleChannelInboundHandler的所有方法作用，场景。
 * 
 * 如下所示super.xxx()的意思是，让这个调用链往下继续传递，如果这个handler是最后一个handler，那么不需要调用super.xxx()
 * 
 * 
 * 
 * @param <I>
 */
public abstract class NoteSimpleChannelInboundHandler<I> extends SimpleChannelInboundHandler<I> {

	protected abstract void log(String msg) throws Exception;

	protected abstract void channelRead0(ChannelHandlerContext ctx, I msg) throws Exception;

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		return super.acceptInboundMessage(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
	}

	/**
	 * 可以开始接受数据了
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	/**
	 * 1. 在本进程内部，当调用channel.close()后，该方法会被触发。
	 * 
	 * 2.
	 * 如果是TCP链接的另一头调用close()，该方法也会被触发。经过测试，我发现，如果是另一头调用了close，本进程的channel.close不会被调用，
	 * 但是channelInactive 和 handlerRemoved
	 * 都会触发。也就是说被动场景下channelInactive内部，并不需要调用channel.close()方法
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	protected void ensureNotSharable() {
		super.ensureNotSharable();
	}

	@Override
	public boolean isSharable() {
		return super.isSharable();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
	}
}
