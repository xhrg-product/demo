package com.github.xhrg.netty.gateway.front;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyServerMain {

    public static void main(String[] args) throws Exception {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup());
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                // 设置解码器
                ch.pipeline().addLast(new HttpServerCodec());
                ch.pipeline().addLast(new HttpObjectAggregatorExt(65536));
                ch.pipeline().addLast(new ProtocolUpgradeHandler());
                ch.pipeline().addLast(new ServerChannelHandler());
            }
        });
        serverBootstrap.bind(8000).sync();
    }
}
