package com.github.xhrg.springrpc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.github.xhrg.springrpc.client.OrderService;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients(clients = OrderService.class)
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    // 利用sdk的方式调用
    @GetMapping("/a")
    public String h() {
        return feign.queryOrder();
    }

    // 利用无sdk的方式调用
    @GetMapping("/b")
    public String helloConsumer() {
        return restTemplate.getForEntity("http://producer-service/queryOrder", String.class).getBody();
    }

    @Autowired
    private OrderService feign;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    protected RestTemplate restTemplate() {
        return new RestTemplate();
    }
}