package com.example.demo;

import java.net.URI;
import java.util.Arrays;

import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MyRouteDefinitionRepository implements RouteDefinitionRepository {

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return Flux.fromIterable(Arrays.asList(buildRouteDefinition(), buildRouteDefinitionWebsocket()));
	}

	private RouteDefinition buildRouteDefinition() {

		RouteDefinition routeDefinition = new RouteDefinition();

		PredicateDefinition p = new PredicateDefinition();
		// 这里除了Path，也可以是其他
		p.addArg("pattern", "/demo/**");
		p.setName("Path");

		routeDefinition.setPredicates(Arrays.asList(p));
		routeDefinition.setId("这个id的目的是为了覆盖");
		// 1.经过测试发现。这个uri不会使用path。比如配置"http://localhost:7001/abcd" 后面的abcd没用用处
		// 2.经过测试发现，"http://"不能少
		routeDefinition.setUri(URI.create("http://localhost:7001"));

		return routeDefinition;
	}

	private RouteDefinition buildRouteDefinitionWebsocket() {

		RouteDefinition routeDefinition = new RouteDefinition();

		PredicateDefinition p = new PredicateDefinition();
		// 这里除了Path，也可以是其他
		p.addArg("pattern", "/websocket/**");
		p.setName("Path");

		routeDefinition.setPredicates(Arrays.asList(p));
		routeDefinition.setId("这个id的目的是为了覆盖");
		// 1.经过测试发现。这个uri不会使用path。比如配置"http://localhost:7001/abcd" 后面的abcd没用用处
		// 2.经过测试发现，"http://"不能少
		routeDefinition.setUri(URI.create("http://localhost:7002"));

		return routeDefinition;
	}

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return Mono.empty();
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		return Mono.empty();
	}

}
