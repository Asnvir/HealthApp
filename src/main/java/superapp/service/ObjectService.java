package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.ObjectBoundary;

public interface ObjectService {
	Mono<ObjectBoundary> create(ObjectBoundary object);
	Mono<Void> update(String superApp, ObjectBoundary object, String id);
	Mono<ObjectBoundary> get(String superapp, String id);
	Flux<ObjectBoundary> getAll();

	Flux<ObjectBoundary> getObjectsByType(String type);

	Flux<ObjectBoundary> getObjectsByAlias(String alias);
}
