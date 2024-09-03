package com.github.xhrg.springrpc.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xhrg.springrpc.client.OrderService;

/**
 * spring-rpc 其实并不要求接口和实现存在代码code级别的 implements，只要http暴露接口和提供给业务的
 * FeignClient.java注解 接口能对应上即可。
 */
@RestController
public class OrderServiceImpl implements OrderService {

    @GetMapping("/queryOrder")
    public String queryOrder() {
        return "这是server的追加";
    }
}
