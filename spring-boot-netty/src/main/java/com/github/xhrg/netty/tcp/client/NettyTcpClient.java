package com.github.xhrg.netty.tcp.client;

import java.util.concurrent.TimeUnit;

import com.github.xhrg.netty.tcp.cmm.SystemUtils;
import com.github.xhrg.netty.tcp.server.NettyTcpServer;
import com.github.xhrg.netty.util.NettyUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class NettyTcpClient {

    private static boolean isUseEpoll = NettyUtils.useEpoll();

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(isUseEpoll ? new EpollEventLoopGroup() : new NioEventLoopGroup());
        bootstrap.channel(isUseEpoll ? EpollSocketChannel.class : NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline line = ch.pipeline();
                line.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.MINUTES));
                line.addLast(new StringDecoder(CharsetUtil.UTF_8));
                line.addLast(new StringEncoder(CharsetUtil.UTF_8));
                line.addLast(new ClientHandler());
            }
        });
        try {
            Channel c = bootstrap.connect("127.0.0.1", NettyTcpServer.port).sync().channel();
            while (1 == 1) {
                c.writeAndFlush("abcd").addListener(future -> {
                    System.out.println(future.isSuccess());
                    System.out.println(future.cause());
                });
                SystemUtils.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
