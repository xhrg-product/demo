package com.example.demo.filter;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class WebsocketProtocolChangeFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return 100;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		try {
			modifyUrlProtocol(exchange);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return chain.filter(exchange);
	}

	private void modifyUrlProtocol(ServerWebExchange exchange) throws URISyntaxException {

		if ("websocket".equalsIgnoreCase(exchange.getRequest().getHeaders().getUpgrade())) {
			ServerHttpRequest request = exchange.getRequest();
			String url = request.getURI().toString();
			String newUrl = url.replaceFirst("http", "ws");
			ServerHttpRequest newRequest = request.mutate().uri(new URI(newUrl)).build();
			exchange = exchange.mutate().request(newRequest).build();
			exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
		}
	}
}
