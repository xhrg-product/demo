package com.github.xhrg.springrpc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "producer-service")
public interface OrderService {

    @GetMapping(value = "/queryOrder")
    String queryOrder();
}