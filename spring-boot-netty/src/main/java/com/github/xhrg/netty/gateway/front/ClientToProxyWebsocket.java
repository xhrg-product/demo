package com.github.xhrg.netty.gateway.front;

import java.util.concurrent.atomic.LongAdder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCounted;

public class ClientToProxyWebsocket extends SimpleChannelInboundHandler<Object> {

    private static final LongAdder fdSize = new LongAdder();

    private Channel channelClient;

    private Channel channelServer;

    public ClientToProxyWebsocket(Channel channelClient, Channel channelServer) {
        fdSize.increment();
        this.logFdSize();
        this.channelClient = channelClient;
        this.channelServer = channelServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ReferenceCounted) {
            ((ReferenceCounted) msg).retain();
        }
//		if (msg instanceof TextWebSocketFrame) {
//			TextWebSocketFrame text = (TextWebSocketFrame) msg;
//			text.retain();
//		} else if (msg instanceof CloseWebSocketFrame) {
//			CloseWebSocketFrame text = (CloseWebSocketFrame) msg;
//			text.retain();
//		}
        String logMsgText = "";
        if ((msg instanceof TextWebSocketFrame)) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            logMsgText = textWebSocketFrame.text();
        }
        System.out.println(logMsgText);

        this.channelServer.writeAndFlush(msg);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            this.closeChannel();
        } catch (Exception e) {
        } finally {
            super.channelInactive(ctx);
        }
    }

    @Override
    public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        try {
            if (evt instanceof IdleStateEvent) {
                closeChannel();
            }
        } finally {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.closeChannel();
    }

    private void closeChannel() {
        channelClient.writeAndFlush(new CloseWebSocketFrame()).addListener(ChannelFutureListener.CLOSE);
        channelServer.writeAndFlush(new CloseWebSocketFrame()).addListener(ChannelFutureListener.CLOSE);
        fdSize.decrement();
        this.logFdSize();
    }

    private void logFdSize() {
    }
}