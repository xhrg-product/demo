package com.github.xhrg.netty.tcp.server;

import java.util.concurrent.TimeUnit;

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
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.NettyRuntime;

public class NettyTcpServer {

	private static boolean isUseEpoll = NettyUtils.useEpoll();

	private static int cSize = NettyRuntime.availableProcessors();

	public static int port = 40814;

	public static void main(String[] args) throws InterruptedException {

		ServerBootstrap bootstrap = new ServerBootstrap();

		EventLoopGroup boss = isUseEpoll ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
		EventLoopGroup selector = isUseEpoll ? new EpollEventLoopGroup(cSize) : new NioEventLoopGroup(cSize);

		bootstrap.group(boss, selector)
				.channel(isUseEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class);

		initOption(bootstrap);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline line = ch.pipeline();
				line.addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
				line.addLast(new StringDecoder(CharsetUtil.UTF_8));
				line.addLast(new StringEncoder(CharsetUtil.UTF_8));
				line.addLast(new ServerHandler());
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

}
