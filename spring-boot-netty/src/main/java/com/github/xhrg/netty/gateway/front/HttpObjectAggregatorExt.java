package com.github.xhrg.netty.gateway.front;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.internal.StringUtil;

/**
 * 
 * 如果请求参数小于n就聚合http，否则不聚合。
 * 
 * @author 张三
 */
public class HttpObjectAggregatorExt extends HttpObjectAggregator {

    private int maxContentLength;
    private boolean aggr = false;

    public HttpObjectAggregatorExt(int maxContentLength) {
        super(maxContentLength);
        this.maxContentLength = maxContentLength;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof DefaultHttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) msg;

            String lengthString = request.headers().get("content-length");
            if (StringUtil.isNullOrEmpty(lengthString)) {
                aggr = true;
                super.channelRead(ctx, msg);
                return;
            }
            int length = Integer.parseInt(lengthString);
            if (length <= maxContentLength) {
                aggr = true;
                super.channelRead(ctx, msg);
                return;
            } else {
                aggr = false;
                ctx.fireChannelRead(msg);
                return;
            }
        }

        if (aggr) {
            super.channelRead(ctx, msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
