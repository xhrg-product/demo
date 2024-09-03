package com.github.xhrg.springrpc.producer;

import org.apache.dubbo.config.annotation.DubboService;

import com.github.xhrg.springrpc.client.OrderService;

@DubboService
public class OrderServiceImpl implements OrderService {

    public String queryOrder() {
        return "这是server的追加";
    }
}
