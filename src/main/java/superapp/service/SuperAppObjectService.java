package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;

public interface SuperAppObjectService {
	Mono<SuperAppObjectBoundary> create(SuperAppObjectBoundary object);
	Mono<Void> update(String superApp, SuperAppObjectBoundary object, String id, String userSuperapp, String email);
	Mono<SuperAppObjectBoundary> get(String superapp, String id, String userSuperapp, String email);
	Flux<SuperAppObjectBoundary> getAll(String superapp, String email);

	Flux<SuperAppObjectBoundary> getObjectsByType(String type, String superApp, String email);

	Flux<SuperAppObjectBoundary> getObjectsByAlias(String alias, String superApp, String email);
	
	Flux<SuperAppObjectBoundary> findByAliasContaining(String pattern, String superApp, String email);
}
