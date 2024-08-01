package com.github.xhrg.netty.gateway.front;

import java.util.Map;

import com.github.xhrg.netty.gateway.ChannelKeys;
import com.github.xhrg.netty.gateway.back.NettyClient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker13;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;

public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String ss = ((TextWebSocketFrame) msg).text();
            System.out.println(ss);
        }

        Channel channeToBack = ctx.channel().attr(ChannelKeys.BACK).get();
        if (channeToBack == null) {
            channeToBack = NettyClient.createChannel();
            channeToBack.attr(ChannelKeys.FRONT).set(ctx.channel());
            ctx.channel().attr(ChannelKeys.BACK).set(channeToBack);
        }

        final Channel cc = channeToBack;
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest bf = (FullHttpRequest) msg;
            HttpHeaders ss = bf.headers();
            ss.set("host", "rdfa-gateway-sample.dev.ennewi.cn");
            for (Map.Entry a : ss.entries()) {
                System.out.println(a.getKey() + " :::::: " + a.getValue());
            }
            ss.remove("Sec-WebSocket-Extensions");
            channeToBack.writeAndFlush(bf.copy());
        } else if (msg instanceof ByteBufHolder) {
            channeToBack.writeAndFlush(new TextWebSocketFrame("aaaaaaaaaabbbbbbbbb"));
        }
    }
}
