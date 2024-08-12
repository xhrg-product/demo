package com.github.xhrg.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTcpServer {
	private static boolean isUseEpoll = useEpoll();

	private static int port = 443;

	public static void main(String[] args) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup boss;
		EventLoopGroup selector;

		if (isUseEpoll) {
			boss = new EpollEventLoopGroup(1);
		} else {
			boss = new NioEventLoopGroup(1);
		}

		if (isUseEpoll) {
			selector = new EpollEventLoopGroup(Runtime.getRuntime().availableProcessors());
		} else {
			selector = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
		}

		bootstrap.group(boss, selector).channel(socketChannelClass());

		// linux下的单进程多端口
		if (isUseEpoll) {
			bootstrap.option(EpollChannelOption.SO_REUSEPORT, true);
			bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		}

		initOption(bootstrap);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new TcpHandler());
			}
		});
		// bootstrap.bind(8080).sync().channel().closeFuture().sync();这句话注释掉，先不删除，待定其用法
		ChannelFuture c = bootstrap.bind(port).sync();
		String ip = c.channel().localAddress().toString();
		System.out.println(ip);
	}

	public static void initOption(ServerBootstrap bootstrap) {

		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 65535);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 65535);
	}

	private static Class<? extends ServerChannel> socketChannelClass() {
		if (isUseEpoll) {
			return EpollServerSocketChannel.class;
		}
		return NioServerSocketChannel.class;
	}

	private static boolean useEpoll() {
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("linux") && Epoll.isAvailable()) {
			return true;
		}
		return false;
	}
}
