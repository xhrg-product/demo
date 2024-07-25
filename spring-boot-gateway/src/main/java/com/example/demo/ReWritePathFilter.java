package com.example.demo;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ReWritePathFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		// 数字越小，执行越早
		return 100;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		System.out.println(path);
		String newPath = newPath(path);
		ServerHttpRequest newRequest = exchange.getRequest().mutate().path(newPath).build();
		return chain.filter(exchange.mutate().request(newRequest).build());
	}

	public String newPath(String path) {
		path = path.replaceFirst("/", "");
		int index = path.indexOf("/");
		if (index < 0) {
			return "/" + path + "-ext";
		}
		return "/" + path.substring(0, index) + "-ext" + path.substring(index, path.length() - 1);
	}

}
