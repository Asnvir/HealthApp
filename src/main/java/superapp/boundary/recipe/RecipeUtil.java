package superapp.boundary.recipe;

import java.util.ArrayList;
import java.util.Map;

import static superapp.common.Consts.*;

public class RecipeUtil {

    public enum Unit {
        PIECES, CUPS, GRAMS, KG, LITERS, ML, TSP, TBSP, LB, OZ, FL_OZ, MG
    }

    public enum CookingTimeUnit {
        SECONDS, MINUTES, HOURS
    }

    public static ArrayList<Ingredient> extractIngredients(Map<String, Object> objectDetails) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Object value = objectDetails.get(RECIPE_INGREDIENTS);
        if (value instanceof ArrayList<?>) {
            extractIngredientsFromList((ArrayList<?>) value, ingredients);
        }
        return ingredients;
    }

    private static void extractIngredientsFromList(ArrayList<?> list, ArrayList<Ingredient> ingredients) {
        for (Object item : list) {
            if (item instanceof Map<?, ?> ingredientMap) {
                String name = (String) ingredientMap.get(INGREDIENT_NAME);
                String quantity = (String) ingredientMap.get(INGREDIENT_QUANTITY);
                String unit = (String) ingredientMap.get(INGREDIENT_UNIT);
                String category = (String) ingredientMap.get(INGREDIENT_CATEGORY);
                ingredients.add(new Ingredient(name, quantity, unit, category));
            }
        }
    }

    public static ArrayList<Instruction> extractInstructions(Map<String, Object> objectDetails) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        Object value = objectDetails.get(RECIPE_INSTRUCTIONS);
        if (value instanceof ArrayList<?>) {
            extractInstructionsFromList((ArrayList<?>) value, instructions);
        }
        return instructions;
    }

    private static void extractInstructionsFromList(ArrayList<?> list, ArrayList<Instruction> instructions) {
        for (Object item : list) {
            if (item instanceof String instruction) {
                instructions.add(new Instruction(instruction));
            }
        }
    }

    public static NutritionalInfo extractNutritionalInfo(Map<String, Object> objectDetails) {
        Object value = objectDetails.get(RECIPE_NUTRITIONAL_INFO);
        if (value instanceof Map<?, ?> nutritionalInfoMap) {
            String calories = getStringValue(nutritionalInfoMap.get(NUTRITIONAL_INFO_CALORIES));
            String protein = getStringValue(nutritionalInfoMap.get(NUTRITIONAL_INFO_PROTEIN));
            String fat = getStringValue(nutritionalInfoMap.get(NUTRITIONAL_INFO_FAT));
            String carbohydrates = getStringValue(nutritionalInfoMap.get(NUTRITIONAL_INFO_CARBS));
            return new NutritionalInfo(calories, protein, fat, carbohydrates);
        }
        return null;
    }

    private static String getStringValue(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Integer) {
            return String.valueOf(obj);
        }
        return null; // Or handle other types if necessary
    }

    public static CookingTime extractCookingTime(Map<String, Object> objectDetails) {
        Object value = objectDetails.get(RECIPE_COOKING_TIME);
        if (value instanceof Map<?, ?> cookingTimeMap) {
            String duration = (String) cookingTimeMap.get(RECIPE_COOKING_TIME_DURATION);
            String unit = (String) cookingTimeMap.get(RECIPE_COOKING_TIME_UNIT);
            return new CookingTime(duration, unit);
        }
        return null;
    }

}
