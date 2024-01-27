package superapp.service;

import superapp.boundary.MiniAppCommandBoundary;
import java.util.List;

public interface MiniAppCommandService {

	Mono<MiniAppCommandBoundary> invokeCommand(MiniAppCommandBoundary command);
}