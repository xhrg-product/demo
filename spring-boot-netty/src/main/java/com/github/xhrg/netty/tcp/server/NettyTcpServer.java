package com.github.xhrg.netty.tcp.server;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLEngine;

import com.github.xhrg.netty.util.NettyUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyTcpServer {

    private static boolean isUseEpoll = NettyUtils.useEpoll();

    private static int cSize = NettyRuntime.availableProcessors();

    public static int port = 40814;

    private static boolean enableTls = false;

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = isUseEpoll ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
        EventLoopGroup selector = isUseEpoll ? new EpollEventLoopGroup(cSize) : new NioEventLoopGroup(cSize);
        EventExecutorGroup worker = new DefaultEventExecutorGroup(cSize);
        
        bootstrap.group(boss, selector)
                .channel(isUseEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class);

        initOption(bootstrap);

        SslContext tls = tls();

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline line = ch.pipeline();
                if (tls != null) {
                    SSLEngine engine = tls.newEngine(ch.alloc());
                    engine.setUseClientMode(false);
                    ch.pipeline().addFirst("ssl", new SslHandler(engine));
                }
                line.addLast(worker, new IdleStateHandler(0, 0, 60, TimeUnit.MINUTES));
                line.addLast(worker, new StringDecoder(CharsetUtil.UTF_8));
                line.addLast(worker, new StringEncoder(CharsetUtil.UTF_8));
                line.addLast(worker, new ServerHandler());
            }
        });
        ChannelFuture c = bootstrap.bind(port).sync();
        String ip = c.channel().localAddress().toString();
        System.out.println(ip);
    }

    public static void initOption(ServerBootstrap bootstrap) {

        // linux下的单进程多端口
        if (isUseEpoll) {
            bootstrap.option(EpollChannelOption.SO_REUSEPORT, true);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        }

        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 65535);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 65535);
    }

    public static SslContext tls() {
        try {
            if (!enableTls) {
                return null;
            }
            SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
            return SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey())
                    .sslProvider(SslProvider.JDK).clientAuth(ClientAuth.OPTIONAL).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
