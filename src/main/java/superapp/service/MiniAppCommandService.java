package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;


public interface MiniAppCommandService {

	Mono<MiniAppCommandBoundary> invokeACommand(String miniAppName, MiniAppCommandBoundary command);
}