package com.github.xhrg.grpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GrpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcConsumerApplication.class, args);
    }
}