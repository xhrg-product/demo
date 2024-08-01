package com.github.xhrg.netty.gateway.front;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ProxyToServerWebsocket extends SimpleChannelInboundHandler<Object> {

    private Channel channelClient;

    private Channel channelServer;

    public ProxyToServerWebsocket(Channel channelClient, Channel channelServer) {
        this.channelClient = channelClient;
        this.channelServer = channelServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof TextWebSocketFrame) {
//            TextWebSocketFrame text = (TextWebSocketFrame)msg;
//            text.retain();
//        } else if (msg instanceof CloseWebSocketFrame) {
//            CloseWebSocketFrame text = (CloseWebSocketFrame)msg;
//            text.retain();
//        }
        if (msg instanceof ReferenceCounted) {
            ((ReferenceCounted) msg).retain();
        }

        String logMsgText = "";
        if ((msg instanceof TextWebSocketFrame)) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            logMsgText = textWebSocketFrame.text();
        }
        System.out.println(logMsgText);

        this.channelClient.writeAndFlush(msg).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (msg instanceof CloseWebSocketFrame) {
                    closeChannel();
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.closeChannel();
    }

    @Override
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

    private void closeChannel() {
        channelClient.writeAndFlush(new CloseWebSocketFrame()).addListener(ChannelFutureListener.CLOSE);
        channelServer.writeAndFlush(new CloseWebSocketFrame()).addListener(ChannelFutureListener.CLOSE);
    }
}
