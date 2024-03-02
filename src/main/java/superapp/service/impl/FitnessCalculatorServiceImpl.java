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
import superapp.boundary.fitness.BodyFatPercentage;
import superapp.boundary.fitness.DailyCalorieBudgetResult;
import superapp.boundary.fitness.IdealWeightResult;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.boundary.fitness.BmiResult;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.exception.InvalidInputException;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.FitnessCalculatorService;

@Service("FitnessCalculator")
public class FitnessCalculatorServiceImpl implements FitnessCalculatorService {

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
            case "calculateBodyFatPercentage" ->{
                return calculateBodyFatPercentage(obj).flatMapMany(Flux::just);
            }
            case "calculateDailyCaloricBudget" ->{
                return getDailyCalories(obj).flatMapMany(Flux::just);
            }
            case "calculateIdealWeight" ->{
                return getIdealWeight(obj).flatMapMany(Flux::just);
            }
                default -> throw new InvalidInputException("UNKNOWN_COMMAND_EXCEPTION");
            }

        });
    }

    @Override
    public void handleObjectByType(SuperAppObjectBoundary object) {
        checkUserProfileData(object);
    }

    private void checkUserProfileData(SuperAppObjectBoundary object) {
        if (object.getObjectDetails().get("height") == null || object.getObjectDetails().get("weight") == null) {
            throw new InvalidInputException("MISSING_HEIGHT_OR_WEIGHT");
        }
        if(object.getObjectDetails().get("age") == null){
            throw new InvalidInputException("MISSING_AGE");
        }
        if(object.getObjectDetails().get("waist") == null || object.getObjectDetails().get("neck") == null || object.getObjectDetails().get("hip") == null){
            throw new InvalidInputException("MISSING_WAIST_OR_NECK_OR_HIP");
        }
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
    public Mono<Object> getIdealWeight(SuperAppObjectEntity object) {
        double height = Double.parseDouble(object.getObjectDetails().getOrDefault("height", "0").toString());
        String gender = object.getObjectDetails().getOrDefault("gender","male").toString();
        return FitnessCalculatorApi.calculateIdelWeight(gender, height)
                .doOnSuccess(response -> logger.info("Response before flatMap: " + response))
                .flatMap(response -> {
                    logger.info("Inside flatMap with response: " + response);
                    try {
                        JsonNode rootNode = objectMapper.readTree(response);
                        JsonNode dataNode = rootNode.path("data");
                        IdealWeightResult idealWeightReesult = objectMapper.treeToValue(dataNode, IdealWeightResult.class);
                        idealWeightReesult.updateAVG();
                        return Mono.just((Object)idealWeightReesult);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.error(e);
                    }
                })
                .doOnError(error -> logger.error("Error in the stream", error));
    }


	@Override
    public Mono<Object> calculateBodyFatPercentage(SuperAppObjectEntity object) {
        double height = Double.parseDouble(object.getObjectDetails().getOrDefault("height", "0").toString());
        double weight = Double.parseDouble(object.getObjectDetails().getOrDefault("weight", "0").toString());
        int age = Integer.parseInt(object.getObjectDetails().getOrDefault("age", "0").toString());
        String gender = object.getObjectDetails().getOrDefault("gender","male").toString();
        double waist = Double.parseDouble(object.getObjectDetails().getOrDefault("waist", "0").toString());
        double neck = Double.parseDouble(object.getObjectDetails().getOrDefault("neck", "0").toString());
        double hip = Double.parseDouble(object.getObjectDetails().getOrDefault("hip", "0").toString());
        return FitnessCalculatorApi.calculateFatPercentage(age,gender,weight,height,waist,hip, neck)
                .doOnSuccess(response -> logger.info("Response before flatMap: " + response))
                .flatMap(response -> {
                    logger.info("Inside flatMap with response: " + response);
                    try {
                        JsonNode rootNode = objectMapper.readTree(response);
                        JsonNode dataNode = rootNode.path("data");
                        BodyFatPercentage bodyFatPercentage = objectMapper.treeToValue(dataNode, BodyFatPercentage.class);
                        return Mono.just((Object)bodyFatPercentage);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.error(e);
                    }
                });
    }

    @Override
    public Mono<Object> getDailyCalories(SuperAppObjectEntity object) {
        double height = Double.parseDouble(object.getObjectDetails().getOrDefault("height", "0").toString());
        double weight = Double.parseDouble(object.getObjectDetails().getOrDefault("weight", "0").toString());
        int age = Integer.parseInt(object.getObjectDetails().getOrDefault("age", "0").toString());
        String gender = object.getObjectDetails().getOrDefault("gender","male").toString();
        String activityLevel = object.getObjectDetails().getOrDefault("activitylevel","level_1").toString();
        return FitnessCalculatorApi.calculateDailyCalory(age,gender,height,weight,activityLevel)
                .doOnSuccess(response -> logger.info("Response before flatMap: " + response))
                .flatMap(response -> {
                    logger.info("Inside flatMap with response: " + response);
                    try {
                        JsonNode rootNode = objectMapper.readTree(response);
                        JsonNode dataNode = rootNode.path("data");
                        DailyCalorieBudgetResult dailyCalorieBudget = objectMapper.treeToValue(dataNode, DailyCalorieBudgetResult.class);
                        return Mono.just((Object)dailyCalorieBudget);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.error(e);
                    }
                });
    }

}
