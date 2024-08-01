package com.github.xhrg.netty.gateway.front;

import com.github.xhrg.netty.gateway.ChannelKeys;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ProtocolUpgradeHandler extends ChannelOutboundHandlerAdapter {

    // 空闲检测时间1分钟，也就是60秒
    private static final int IDLE_TIME_SECOND = 60;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        try {
            if (!(msg instanceof FullHttpResponse)) {
                return;
            }
            FullHttpResponse response = (FullHttpResponse) msg;
            String value = response.headers().get(HttpHeaderNames.UPGRADE);
            if (!"websocket".equalsIgnoreCase(value)) {
                return;
            }

            Channel channelToBack = ctx.channel().attr(ChannelKeys.BACK).get();
            Channel channelToFront = ctx.channel();

            promise.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ChannelUtils.removeAllHandler(channelToFront);
                    ChannelUtils.removeAllHandler(channelToBack);
                    // 添加websocket解析器到客户端handler
                    channelToFront.pipeline().addLast(new WebSocket13FrameDecoder(true, true, 65536, true));
                    channelToFront.pipeline().addLast(new WebSocket13FrameEncoder(false));
                    channelToFront.pipeline().addLast(new IdleStateHandler(0, 0, IDLE_TIME_SECOND));
                    channelToFront.pipeline().addLast(new ClientToProxyWebsocket(channelToFront, channelToBack));

                    // 添加websocket解析器到服务端handler
                    channelToBack.pipeline().addLast(new WebSocket13FrameEncoder(true));
                    channelToBack.pipeline().addLast(new WebSocket13FrameDecoder(false, true, 65536, true));
                    channelToBack.pipeline().addLast(new IdleStateHandler(0, 0, IDLE_TIME_SECOND));
                    channelToBack.pipeline().addLast(new ProxyToServerWebsocket(channelToFront, channelToBack));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.write(ctx, msg, promise);
        }
    }
}
