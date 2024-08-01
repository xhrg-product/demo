package com.github.xhrg.netty.gateway;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class ChannelKeys {

    public static AttributeKey<Channel> BACK = AttributeKey.valueOf("BACK");

    public static AttributeKey<Channel> FRONT = AttributeKey.valueOf("FRONT");

}
