package com.github.xhrg.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NettyDemoApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(NettyDemoApplication.class, args);
	}
}
