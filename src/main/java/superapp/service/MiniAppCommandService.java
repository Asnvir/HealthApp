package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;


public interface MiniAppCommandService {

	Mono<MiniAppCommandBoundary> invokeACommand(String miniAppName, MiniAppCommandBoundary command);
}