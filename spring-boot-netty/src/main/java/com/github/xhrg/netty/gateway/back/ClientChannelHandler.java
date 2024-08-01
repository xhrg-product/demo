package com.github.xhrg.netty.gateway.back;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.github.xhrg.netty.gateway.ChannelKeys;

import io.netty.buffer.ByteBufHolder;

public class ClientChannelHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channelToFront = ctx.channel().attr(ChannelKeys.FRONT).get();
        Channel channelToBack = ctx.channel();

        if (msg instanceof TextWebSocketFrame) {
            String ss = ((TextWebSocketFrame) msg).text();
            System.out.println(ss);
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse resp = (FullHttpResponse) msg;
            channelToFront.writeAndFlush(resp.copy()).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {

                    // 删除所有的http解析器
                    channelToFront.pipeline().remove(HttpServerCodec.class);
                    channelToFront.pipeline().remove(HttpObjectAggregator.class);

                    channelToBack.pipeline().remove(HttpClientCodec.class);
                    channelToBack.pipeline().remove(HttpObjectAggregator.class);

                    // 添加websocket解析器
                    channelToFront.pipeline().addFirst(new WebSocket13FrameDecoder(true, true, 65536, true));
                    channelToFront.pipeline().addFirst(new WebSocket13FrameEncoder(false));

                    channelToBack.pipeline().addFirst(new WebSocket13FrameEncoder(true));
                    channelToBack.pipeline().addFirst(new WebSocket13FrameDecoder(false, true, 65536, true));

                    finishHandshake(channelToBack, resp);

                }
            });
        } else if (msg instanceof ByteBufHolder) {
            channelToFront.writeAndFlush(((ByteBufHolder) msg).copy());
        }
    }

    /**
     * 
     * 这段代码来自哪里尚未找到
     * 
     * @param channel
     * @param response
     */
    public final void finishHandshake(Channel channel, FullHttpResponse response) {

        final ChannelPipeline p = channel.pipeline();
        // Remove decompressor from pipeline if its in use
        HttpContentDecompressor decompressor = p.get(HttpContentDecompressor.class);
        if (decompressor != null) {
            p.remove(decompressor);
        }

        // Remove aggregator if present before
        HttpObjectAggregator aggregator = p.get(HttpObjectAggregator.class);
        if (aggregator != null) {
            p.remove(aggregator);
        }

        ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
        if (ctx == null) {
            ctx = p.context(HttpClientCodec.class);
            if (ctx == null) {
                throw new IllegalStateException(
                        "ChannelPipeline does not contain " + "an HttpRequestEncoder or HttpClientCodec");
            }
            final HttpClientCodec codec = (HttpClientCodec) ctx.handler();
            // Remove the encoder part of the codec as the user may start writing frames
            // after this method returns.
            codec.removeOutboundHandler();

            p.addAfter(ctx.name(), "ws-decoder", new WebSocket13FrameDecoder(false, true, 65536, true));

            // Delay the removal of the decoder so the user can setup the pipeline if needed
            // to handle
            // WebSocketFrame messages.
            // See https://github.com/netty/netty/issues/4533
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    p.remove(codec);
                }
            });
        } else {
            if (p.get(HttpRequestEncoder.class) != null) {
                // Remove the encoder part of the codec as the user may start writing frames
                // after this method returns.
                p.remove(HttpRequestEncoder.class);
            }
            final ChannelHandlerContext context = ctx;
            p.addAfter(context.name(), "ws-decoder", new WebSocket13FrameDecoder(false, true, 65536, true));

            // Delay the removal of the decoder so the user can setup the pipeline if needed
            // to handle
            // WebSocketFrame messages.
            // See https://github.com/netty/netty/issues/4533
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    p.remove(context.handler());
                }
            });
        }
    }
}
