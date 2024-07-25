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
		return Flux.fromIterable(Arrays.asList(buildRouteDefinition()));
	}

	private RouteDefinition buildRouteDefinition() {

		RouteDefinition routeDefinition = new RouteDefinition();

		PredicateDefinition p = new PredicateDefinition();
		// 这里除了Path，也可以是其他
		p.addArg("pattern", "/demo/**");
		p.setName("Path");

		routeDefinition.setPredicates(Arrays.asList(p));
		routeDefinition.setId("这个id的目的是为了覆盖");
		routeDefinition.setUri(URI.create("http://localhost:8080"));

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
