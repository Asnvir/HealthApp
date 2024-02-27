package superapp.service;

import reactor.core.publisher.Flux;
import superapp.boundary.command.MiniAppCommandBoundary;
import superapp.entity.command.MiniAppCommandEntity;

public interface MiniAppService {

    Flux<Object> handleCommand(MiniAppCommandEntity command);
}
