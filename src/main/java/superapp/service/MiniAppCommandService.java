package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.MiniAppCommandBoundary;
import java.util.List;

public interface MiniAppCommandService {

	Mono<MiniAppCommandBoundary> invokeACommand(MiniAppCommandBoundary command);
}