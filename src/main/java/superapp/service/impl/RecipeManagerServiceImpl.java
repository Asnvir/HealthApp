package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.boundary.recipe.Recipe;
import superapp.common.Consts;
import superapp.common.RecipeValidationUtil;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.exception.NotFoundException;
import superapp.repository.ObjectRepository;
import superapp.service.RecipeManagerService;

import java.util.Map;

import static superapp.common.Consts.*;

@Service(Consts.MINI_APP_NAME_RECIPE_MANAGER)
public class RecipeManagerServiceImpl implements RecipeManagerService {

    private final ObjectRepository objectRepository;
    private static final Logger logger = LoggerFactory.getLogger(RecipeManagerServiceImpl.class);

    public RecipeManagerServiceImpl(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }


    @Override
    public void handleObjectByType(SuperAppObjectBoundary object) {
        checkRecipeData(object);
    }

    private void checkRecipeData(SuperAppObjectBoundary object) {
        Recipe recipe = new Recipe(object);
        RecipeValidationUtil.validateRecipe(recipe);
    }

    @Override
    public Flux<Object> handleCommand(MiniAppCommandEntity command) {
        return switchMenuCommand(command);
    }

    private Flux<Object> switchMenuCommand(MiniAppCommandEntity command) {
        String commandName = command.getCommand();
        if (commandName.equals(MINI_APP_COMMAND_PERFORM_SEARCH_BY_COMMAND_ATTRIBUTES)) {
            return performSearchByCommandAttributes(command);
        }
        return Flux.error(new NotFoundException("UNKNOWN_COMMAND_EXCEPTION"));
    }

    @Override
    public Flux<Object> performSearchByCommandAttributes(MiniAppCommandEntity command) {
        Map<String, Object> commandAttributes = command.getCommandAttributes();
        String searchType = (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_TYPE);

        if (searchType == null || searchType.isEmpty()) {
            logger.error("Search type is missing or empty");
            return Flux.error(new IllegalArgumentException("Search type is missing or empty"));
        }

        return switch (searchType) {
            case COMMAND_ATTRIBUTES_SEARCH_TYPE_CALORIES -> performNutritionalAttributeSearch(
                    NUTRITIONAL_INFO_CALORIES,
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE),
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_TYPE_OPERATOR)
            ).doOnError(error -> logger.error("Error performing calories search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_PROTEIN -> performNutritionalAttributeSearch(
                    NUTRITIONAL_INFO_PROTEIN,
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE),
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_TYPE_OPERATOR)
            ).doOnError(error -> logger.error("Error performing protein search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_FAT -> performNutritionalAttributeSearch(
                    NUTRITIONAL_INFO_FAT,
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE),
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_TYPE_OPERATOR)
            ).doOnError(error -> logger.error("Error performing fat search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_CARBS -> performNutritionalAttributeSearch(
                    NUTRITIONAL_INFO_CARBS,
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE),
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_TYPE_OPERATOR)
            ).doOnError(error -> logger.error("Error performing carbs search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_NAME -> performNameSearch(
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE)
            ).doOnError(error -> logger.error("Error performing name search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_RECIPE_DESCRIPTION -> performDescriptionSearch(
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE)
            ).doOnError(error -> logger.error("Error performing description search: {}", error.getMessage()));

            case COMMAND_ATTRIBUTES_SEARCH_TYPE_INGREDIENT -> performIngredientSearch(
                    (String) commandAttributes.get(COMMAND_ATTRIBUTES_SEARCH_VALUE)
            ).doOnError(error -> logger.error("Error performing ingredient search: {}", error.getMessage()));

            default -> {
                logger.warn("Unknown search type: {}", searchType);
                yield Flux.empty();
            }
        };
    }


    private Flux<Object> performNutritionalAttributeSearch(String attribute, String value, String operator) {
        if (!isValidAttribute(attribute)) {
            logger.error("Invalid attribute: {}", attribute);
            return Flux.error(new IllegalArgumentException("Invalid attribute"));
        }

        if (!isValidOperator(operator)) {
            logger.error("Invalid operator: {}", operator);
            return Flux.error(new IllegalArgumentException("Invalid operator"));
        }

        double targetValue = parseValue(value);
        if (targetValue == -1) {
            logger.error("Error parsing value: {}", value);
            return Flux.error(new IllegalArgumentException("Invalid value format"));
        }

        return objectRepository
                .findByType(RECIPE_TYPE)
                .map(SuperAppObjectBoundary::new)
                .filter(superAppObjectBoundary -> filterByAttribute(superAppObjectBoundary, attribute, operator, targetValue))
                .map(Object.class::cast);
    }


    private boolean filterByAttribute(SuperAppObjectBoundary superAppObjectBoundary, String attribute, String operator, double targetValue) {
        if (superAppObjectBoundary.getAlias().equalsIgnoreCase(MINI_APP_ALIAS_DUMMYRECIPE)) {
            return false;
        }
        Recipe recipe = new Recipe(superAppObjectBoundary);
        double attributeValue = getNutritionalAttributeValue(recipe, attribute);
        return switch (operator) {
            case SEARCH_OPERATOR_GREATER_THAN -> attributeValue > targetValue;
            case SEARCH_OPERATOR_LESS_THAN -> attributeValue < targetValue;
            case SEARCH_OPERATOR_EQUAL_TO -> attributeValue == targetValue;
            default -> false;
        };
    }


    private boolean isValidOperator(String operator) {
        return operator.equalsIgnoreCase(SEARCH_OPERATOR_GREATER_THAN) ||
                operator.equalsIgnoreCase(SEARCH_OPERATOR_LESS_THAN) ||
                operator.equalsIgnoreCase(SEARCH_OPERATOR_EQUAL_TO);
    }

    private boolean isValidAttribute(String attribute) {
        return attribute.equalsIgnoreCase(NUTRITIONAL_INFO_CALORIES) ||
                attribute.equalsIgnoreCase(NUTRITIONAL_INFO_PROTEIN) ||
                attribute.equalsIgnoreCase(NUTRITIONAL_INFO_FAT) ||
                attribute.equalsIgnoreCase(NUTRITIONAL_INFO_CARBS);
    }

    private double parseValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private double getNutritionalAttributeValue(Recipe recipe, String attribute) {
        return switch (attribute) {
            case NUTRITIONAL_INFO_CALORIES -> Double.parseDouble(recipe.getNutritionalInfo().getCalories());
            case NUTRITIONAL_INFO_PROTEIN -> Double.parseDouble(recipe.getNutritionalInfo().getProtein());
            case NUTRITIONAL_INFO_FAT -> Double.parseDouble(recipe.getNutritionalInfo().getFat());
            case NUTRITIONAL_INFO_CARBS -> Double.parseDouble(recipe.getNutritionalInfo().getCarbs());
            default -> {
                logger.error("Invalid nutritional attribute: {}", attribute);
                throw new IllegalArgumentException("Invalid nutritional attribute: " + attribute);
            }
        };
    }


    private Flux<Object> performNameSearch(String name) {
        logger.info("Starting name search for: {}", name);
        return objectRepository.findByType(RECIPE_TYPE)
                .map(SuperAppObjectBoundary::new)
                .filter(superAppObjectBoundary -> {
                    if (superAppObjectBoundary.getAlias().equalsIgnoreCase(MINI_APP_ALIAS_DUMMYRECIPE)) {
                        return false;
                    }
                    Recipe recipe = new Recipe(superAppObjectBoundary);
                    return recipe.getName().equalsIgnoreCase(name);
                })
                .map(Object.class::cast)
                .doOnError(e -> logger.error("Error during name search: ", e))
                .doOnComplete(() -> logger.info("Name search completed successfully."));
    }


    private Flux<Object> performDescriptionSearch(String description) {
        return objectRepository.findByType(RECIPE_TYPE)
                .map(SuperAppObjectBoundary::new)
                .filter(superAppObjectBoundary -> {
                    if (superAppObjectBoundary.getAlias().equalsIgnoreCase(MINI_APP_ALIAS_DUMMYRECIPE)) {
                        return false;
                    }
                    Recipe recipe = new Recipe(superAppObjectBoundary);
                    return description != null && recipe.getDescription() != null && recipe.getDescription().contains(description);
                })
                .map(Object.class::cast);
    }



    private Flux<Object> performIngredientSearch(String ingredient) {
        return objectRepository.findByType(RECIPE_TYPE)
                .map(SuperAppObjectBoundary::new)
                .filter(superAppObjectBoundary -> {
                    if (superAppObjectBoundary.getAlias().equalsIgnoreCase(MINI_APP_ALIAS_DUMMYRECIPE)) {
                        return false;
                    }
                    Recipe recipe = new Recipe(superAppObjectBoundary);
                    return recipe.getIngredients().stream()
                            .anyMatch(i -> i.getName().equalsIgnoreCase(ingredient));
                })
                .map(Object.class::cast)
                .doOnError(e -> logger.error("Error during ingredient search: ", e))
                .doOnComplete(() -> logger.info("Ingredient search completed successfully."));
    }
}
