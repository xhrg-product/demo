package com.github.xhrg.netty.gateway.back;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class NettyClient {

	public static Channel createChannel() {
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<NioSocketChannel>() { // 通道是NioSocketChannel
			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {
				// 字符串编码器，一定要加在SimpleClientHandler 的上面
//                ch.pipeline().addLast(new EmptyHandler());
				ch.pipeline().addLast(new HttpClientCodec());
				// 聚合器，使用websocket会用到
				ch.pipeline().addLast(new HttpObjectAggregator(65536));
				ch.pipeline().addLast(new ClientChannelHandler());
			}
		});

		try {
			Channel c = bootstrap.connect("127.0.0.1", 8080).sync().channel();
			return c;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
