package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.command.MiniAppCommandBoundary;


public interface MiniAppCommandService {

	public Object invokeACommand(String miniAppName, MiniAppCommandBoundary command);
}