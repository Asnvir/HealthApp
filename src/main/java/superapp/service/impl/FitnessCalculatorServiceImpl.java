package superapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.api.FitnessCalculatorApi;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.boundary.fitness.BmiResult;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.FitnessCalculatorService;
import superapp.service.MiniAppService;

@Service("FitnessCalculator")
public class FitnessCalculatorServiceImpl implements MiniAppService, FitnessCalculatorService {

    private final ObjectRepository objectRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(FitnessCalculatorServiceImpl.class);


    public FitnessCalculatorServiceImpl(
            ObjectRepository objectRepository
    ) {
        super();
        this.objectRepository = objectRepository;
    }

    @Override
    public Flux<Object> handleCommand(MiniAppCommandEntity command) {
        ObjectId objectId = new ObjectId(command.getTargetObject().getObjectId().getSuperapp(), command.getTargetObject().getObjectId().getId());
        Mono<SuperAppObjectEntity> object = objectRepository.findById(objectId)
                .switchIfEmpty(Mono.error(new NotFoundException("OBJECT_NOT_FOUND_EXCEPTION")));
        return object.flatMapMany(obj -> {
            String commandName = command.getCommand();
            switch (commandName) {
            case "calculateBmi" ->{
                return calculateBmi(obj).flatMapMany(Flux::just);
            }
                default -> throw new NotFoundException("UNKNOWN_COMMAND_EXCEPTION");
            }

        });
    }

    @Override
    public Mono<Object> calculateBmi(SuperAppObjectEntity object) {
        double height = Double.parseDouble(object.getObjectDetails().getOrDefault("height", "0").toString());
        double weight = Double.parseDouble(object.getObjectDetails().getOrDefault("weight", "0").toString());
        int age = Integer.parseInt(object.getObjectDetails().getOrDefault("age", "0").toString());
        return FitnessCalculatorApi.calculateBMI(height, weight, age)
                .doOnSuccess(response -> logger.info("Response before flatMap: " + response))
                .flatMap(response -> {
                    logger.info("Inside flatMap with response: " + response);
                    try {
                        JsonNode rootNode = objectMapper.readTree(response);
                        JsonNode dataNode = rootNode.path("data");
                        BmiResult bmiResult = objectMapper.treeToValue(dataNode, BmiResult.class);
                        return Mono.just((Object)bmiResult);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.error(e);
                    }
                })
                .doOnError(error -> logger.error("Error in the stream", error));
    }

    @Override
    public Mono<Object> calculateBodyFatPercentage(SuperAppObjectEntity object) {
        return null;
    }

    @Override
    public Mono<Object> getIdealWeight(SuperAppObjectEntity object) {
        return null;
    }

    @Override
    public Mono<Object> getDailyCalories(SuperAppObjectEntity object) {
        return null;
    }

}
