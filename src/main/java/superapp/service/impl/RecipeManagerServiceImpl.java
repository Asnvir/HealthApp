package superapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.menu.Recipe;
import superapp.common.Consts;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.MiniAppService;
import superapp.service.SuperAppObjectService;

@Service("RecipeManager")
public class RecipeManagerServiceImpl implements MiniAppService, RecipeManagerService {

    private final SuperAppObjectService superAppObjectService;
    private static final Logger logger = LoggerFactory.getLogger(RecipeManagerServiceImpl.class);

    public RecipeManagerServiceImpl(ObjectRepository objectRepository, SuperAppObjectService superAppObjectService) {
        this.superAppObjectService = superAppObjectService;
    }

    @Override
    public Flux<Object> handleCommand(MiniAppCommandEntity command) {
        return switchMenuCommand(command);
    }

    private Flux<Object> switchMenuCommand(MiniAppCommandEntity command) {
        String commandName = command.getCommand();
        switch (commandName) {
            case "getRecipes" -> {
                return getRecipes(command);
            }
            case "getRecipe" -> {
                return Flux.just(getRecipe(command));
            }
            case "updateRecipe" -> {
                return updateRecipe(command);
            }
            case "deleteRecipe" -> {
                return deleteRecipe(MiniAppCommandEntity command);
            }
            case "getRecipeInstructions" -> {
                return getRecipeInstructions(MiniAppCommandEntity command);
            }
            case "addRecipeInstruction" -> {
                return addRecipeInstruction(MiniAppCommandEntity command);
            }
            case "deleteRecipeInstruction" -> {
                return deleteRecipeInstruction(MiniAppCommandEntity command);
            }
            case "getRecipeIngredients" -> {
                return getRecipeIngredients(MiniAppCommandEntity command);
            }
            case "addRecipeIngredient" -> {
                return addRecipeIngredient(MiniAppCommandEntity command);
            }
            case "deleteRecipeIngredient" -> {
                return deleteRecipeIngredient(MiniAppCommandEntity command);
            }
            case "getRecipeNutrition" -> {
                return getRecipeNutrition(MiniAppCommandEntity command);
            }
            case "getRecipeNutritionSummary" -> {
                return getRecipeNutritionSummary(MiniAppCommandEntity command);
            }
            case "getRecipeNutritionDetails" -> {
                return getRecipeNutritionDetails(MiniAppCommandEntity command);
            }
            case "getRecipeNutritionDetailsSummary" -> {
                return getRecipeNutritionDetailsSummary(MiniAppCommandEntity command);
            }
            default -> {
                return Flux.error(new NotFoundException("UNKNOWN_COMMAND_EXCEPTION"));
            }
        }
    }

    @Override
    public Mono<Object> getRecipe(MiniAppCommandEntity command) {
        String superApp = command.getTargetObject().getObjectId().getSuperapp();
        String recipeId = command.getTargetObject().getObjectId().getId();
        String userSuperApp = command.getInvokedBy().getUserId().getSuperapp();
        String userEmail = command.getInvokedBy().getUserId().getEmail();


        return superAppObjectService
                .get(superApp, recipeId, userSuperApp, userEmail)
                .map(superAppObject -> superAppObject);
    }

    @Override
    public Flux<Object> getRecipes(MiniAppCommandEntity command) {
        String type = Consts.RECIPE_TYPE;
        String superApp = command.getTargetObject().getObjectId().getSuperapp();
        String userEmail = command.getInvokedBy().getUserId().getEmail();


        return superAppObjectService
                .getObjectsByType(type, superApp, userEmail)
                .map(superAppObject -> superAppObject);
    }


    @Override
    public Mono<Object> updateRecipe(MiniAppCommandEntity command) {
        String superApp = command.getTargetObject().getObjectId().getSuperapp();
        String userEmail = command.getInvokedBy().getUserId().getEmail();
        String idTargetObject = command.getTargetObject().getObjectId().getId();

        try {
            Object recipe = command.getCommandAttributes().get(Consts.RECIPE_NEW);
            ObjectMapper objectMapper = new ObjectMapper();
            String recipeJson = objectMapper.writeValueAsString(recipe);
            Recipe updatedRecipe = objectMapper.readValue(recipeJson, Recipe.class);

            Mono<Object> downloadedRecipe = getRecipe(command);

            return downloadedRecipe
                    .map(object -> {
                        try {
                            String oldRecipeJson = objectMapper.writeValueAsString(object);
                            Recipe oldRecipe = objectMapper.readValue(oldRecipeJson, Recipe.class);


                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                    });


        } catch (Exception e) {

        }


        return Mono.error(new IllegalArgumentException("Invalid recipe details"));
    }


    @Override
    public Object deleteRecipe(String recipeName) {
        return null;
    }

    @Override
    public Object getRecipeInstructions(String recipeName) {
        return null;
    }

    @Override
    public Object addRecipeInstruction(String recipeName, Object instruction) {
        return null;
    }

    @Override
    public Object deleteRecipeInstruction(String recipeName, String instruction) {
        return null;
    }

    @Override
    public Object getRecipeIngredients(String recipeName) {
        return null;
    }

    @Override
    public Object addRecipeIngredient(String recipeName, Object ingredient) {
        return null;
    }

    @Override
    public Object deleteRecipeIngredient(String recipeName, String ingredient) {
        return null;
    }

    @Override
    public Object getRecipeNutrition(String recipeName) {
        return null;
    }

    @Override
    public Object getRecipeNutritionSummary(String recipeName) {
        return null;
    }

    @Override
    public Object getRecipeNutritionDetails(String recipeName) {
        return null;
    }

    @Override
    public Object getRecipeNutritionDetailsSummary(String recipeName) {
        return null;
    }


}
