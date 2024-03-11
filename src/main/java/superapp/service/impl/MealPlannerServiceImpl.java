package superapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.api.FitnessCalculatorApi;
import superapp.api.MealPlannerAPI;
import superapp.boundary.fitness.DailyCalorieBudgetResult;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.ObjectId;
import superapp.entity.object.SuperAppObjectEntity;
import org.springframework.web.reactive.function.client.WebClient;
import superapp.exception.InvalidInputException;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.MealPlannerService;

import java.util.concurrent.atomic.AtomicInteger;

public class MealPlannerServiceImpl implements MealPlannerService {
    // private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectRepository objectRepository;

    private static final Logger logger = LoggerFactory.getLogger(MealPlannerServiceImpl.class);

    public MealPlannerServiceImpl(WebClient webClient, ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public Flux<Object> handleCommand(MiniAppCommandEntity command) {
        ObjectId objectId = new ObjectId(command.getTargetObject().getObjectId().getSuperapp(), command.getTargetObject().getObjectId().getId());
        Mono<SuperAppObjectEntity> object = objectRepository.findById(objectId)
                .switchIfEmpty(Mono.error(new NotFoundException("OBJECT_NOT_FOUND_EXCEPTION")));
        return object.flatMapMany(obj -> {
            String commandName = command.getCommand();

                  if(commandName.equals("meal planner") )
                    return getMealPlanner(obj).flatMapMany(Flux::just);
                  else
                      throw new InvalidInputException("UNKNOWN_COMMAND_EXCEPTION");
            });
    }


    @Override
    public Mono<Object> getMealPlanner(SuperAppObjectEntity object) {
        int caloriesBudget = Integer.parseInt(object.getObjectDetails()
                .getOrDefault("calories budget", 2000)
                .toString());


        // get the max values from FitnessCalculatorApi.calculateDailyCalory
        return MealPlannerAPI.mealPlanner(caloriesBudget, caloriesBudget + 500)
                .doOnSuccess(response -> logger.info("Response before flatMap: " + response))
                .flatMap(response -> {
                    logger.info("Inside flatMap with response: " + response);
                    try {
                        JsonNode rootNode = objectMapper.readTree(response);
                        JsonNode dataNode = rootNode.path("data");
                        DailyCalorieBudgetResult dailyCalorieBudget = objectMapper.treeToValue(dataNode, DailyCalorieBudgetResult.class);
                        return Mono.just((Object) dailyCalorieBudget);
                    } catch (JsonProcessingException e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.error(e);
                    }
                });

    }
}
