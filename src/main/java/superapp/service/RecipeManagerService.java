package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.SuperAppObjectEntity;

public interface RecipeManagerService {

    Flux<Object> handleCommand(MiniAppCommandEntity command);
    Flux<Object> performSearchByCommandAttributes (MiniAppCommandEntity command);

    void handleObjectByType(SuperAppObjectBoundary object);
}
