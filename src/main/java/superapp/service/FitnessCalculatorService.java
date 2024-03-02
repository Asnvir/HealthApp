package superapp.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.SuperAppObjectEntity;

public interface FitnessCalculatorService {

    Flux<Object> handleCommand(MiniAppCommandEntity command);

    void handleObjectByType(SuperAppObjectBoundary object);
    Object calculateBmi(SuperAppObjectEntity object);

    Mono<Object> calculateBodyFatPercentage(SuperAppObjectEntity object);

    Mono<Object> getIdealWeight(SuperAppObjectEntity object);

    Mono<Object> getDailyCalories(SuperAppObjectEntity object);


}
