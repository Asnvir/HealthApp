package superapp.service;

import reactor.core.publisher.Mono;
import superapp.entity.object.SuperAppObjectEntity;

public interface FitnessCalculatorService {
    Object calculateBmi(SuperAppObjectEntity object);

    Mono<Object> calculateBodyFatPercentage(SuperAppObjectEntity object);

    Mono<Object> getIdealWeight(SuperAppObjectEntity object);

    Mono<Object> getDailyCalories(SuperAppObjectEntity object);


}
