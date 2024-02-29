package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;


public interface MiniAppCommandService {

	public Flux<Object> invokeACommand(String miniAppName, MiniAppCommandBoundary command);
}