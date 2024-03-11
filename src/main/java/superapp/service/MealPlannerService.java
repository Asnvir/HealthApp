package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.SuperAppObjectEntity;

public interface MealPlannerService {
    Flux<Object> handleCommand(MiniAppCommandEntity command);

    public Mono<Object> getMealPlanner(SuperAppObjectEntity object);
}