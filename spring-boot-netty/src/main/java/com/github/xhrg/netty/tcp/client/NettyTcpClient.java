package com.github.xhrg.netty.tcp.client;

import com.github.xhrg.netty.tcp.server.NettyTcpServer;
import com.github.xhrg.netty.util.NettyUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyTcpClient {

	private static boolean isUseEpoll = NettyUtils.useEpoll();

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();

		EventLoopGroup group = isUseEpoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();

		bootstrap.group(group);
		bootstrap.channel(isUseEpoll ? EpollSocketChannel.class : NioSocketChannel.class);

		bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
				ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
				ch.pipeline().addLast(new NettyTcpClientHandler());
			}
		});
		try {
			Channel c = bootstrap.connect("127.0.0.1", NettyTcpServer.port).sync().channel();
			c.writeAndFlush("abcd");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
