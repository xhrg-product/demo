package com.github.xhrg.netty.gateway.front;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

public class ChannelUtils {

    public static void removeAllHandler(Channel channel) {

        ChannelPipeline channelPipeline = channel.pipeline();
        while (true) {
            ChannelHandler channelHandler = channelPipeline.last();
            if (channelHandler == null) {
                return;
            }
            channelPipeline.remove(channelHandler);
        }
    };
}
