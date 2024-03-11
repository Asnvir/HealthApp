package superapp.common;

import superapp.boundary.recipe.Ingredient;
import superapp.boundary.recipe.Recipe;
import superapp.boundary.recipe.RecipeUtil;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class RecipeValidationUtil {

    public static void validateRecipe(Recipe recipe) {
        // Check if ingredients and nutritional info are present
        if (recipe.getIngredients().isEmpty() || recipe.getIngredients() == null) {
            throw new IllegalArgumentException("Recipe must have ingredients");
        }

        if (recipe.getNutritionalInfo() == null) {
            throw new IllegalArgumentException("Recipe must have nutritional info");
        }

        // Check for required fields
        checkNotBlank(recipe.getName(), "Recipe must have name");
        checkNotBlank(recipe.getDescription(), "Recipe must have description");
        checkNotBlank(recipe.getServingSize(), "Recipe must have serving size info");

        // Check ingredient details
        for (Ingredient ingredient : recipe.getIngredients()) {
            checkPositiveDouble(ingredient.getQuantity());
            checkUnit(ingredient.getUnit());
        }

        // Check cooking time details
        checkPositiveDouble(recipe.getCookingTime().getDuration());
        checkCookingTimeUnit(recipe.getCookingTime().getUnit());

        // Check serving size
        checkPositiveDouble(recipe.getServingSize());

        // Check nutritional info
        checkDouble(recipe.getNutritionalInfo().getCalories(), "Calories must be a double number");
        checkDouble(recipe.getNutritionalInfo().getProtein(), "Protein must be a double number");
        checkDouble(recipe.getNutritionalInfo().getFat(), "Fat must be a double number");
        checkDouble(recipe.getNutritionalInfo().getCarbs(), "Carbohydrates must be a double number");
    }

    private static void checkNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void checkPositiveDouble(String value) {
        try {
            double checkedValue = Double.parseDouble(value);
            if (checkedValue <= 0) {
                throw new IllegalArgumentException("Quantity must be a positive double number");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a positive double number");
        }
    }

    private static void checkDouble(String value, String message) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkUnit(String unit) {
        String unitLowercase = unit.toLowerCase();

        if (!EnumSet.allOf(RecipeUtil.Unit.class)
                .stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet())
                .contains(unitLowercase)) {
            throw new IllegalArgumentException("Unit can only be one of the accepted values");
        }
    }

    public static void checkCookingTimeUnit(String unit) {
        // Convert the unit string to lowercase before comparison
        String unitLowercase = unit.toLowerCase();

        // Check if the lowercase unit string is one of the accepted values
        if (!EnumSet.allOf(RecipeUtil.CookingTimeUnit.class)
                .stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet())
                .contains(unitLowercase)) {
            throw new IllegalArgumentException("Unit in cooking time can only be one of the accepted values");
        }
    }
}

