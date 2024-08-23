package com.github.xhrg.netty.main;

import java.io.File;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChunkedWriteHandlerDemoMain {

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    new ChunkedWriteHandlerDemoMain(port).run();
  }

  private final int port;

  public ChunkedWriteHandlerDemoMain(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline p = ch.pipeline();
              p.addLast(new HttpServerCodec());
              p.addLast(new ChunkedWriteHandler());
              p.addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                @Override
                protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
                  String uri = request.uri();
                  File file = new File("path/to/your/files" + uri);
                  if (file.exists()) {
                    ChunkedFile chunkedFile = new ChunkedFile(file);
                    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                    HttpHeaders headers = response.headers();
                    headers.set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream");
                    headers.set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                    ctx.write(response);
                    ctx.writeAndFlush(chunkedFile);
                  } else {
                    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                  }
                }
              });
            }
          });
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

}
