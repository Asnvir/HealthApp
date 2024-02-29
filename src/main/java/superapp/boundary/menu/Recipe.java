package superapp.boundary.menu;

import superapp.boundary.object.SuperAppObjectBoundary;

import java.util.Map;

import static superapp.common.Consts.*;

public class Recipe {

    private String name;
    private String description;
    private Ingredient[] ingredients;
    private Instructions[] instructions;
    private String cookingTime;
    private String servingSize;
    private NutritionalInfo nutritionalInfo;

    public Recipe() {
    }

    public Recipe(String name, String description, Ingredient[] ingredients, Instructions[] instructions, String cookingTime, String servingSize, NutritionalInfo nutritionalInfo) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.nutritionalInfo = nutritionalInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Instructions[] getInstructions() {
        return instructions;
    }

    public void setInstructions(Instructions[] instructions) {
        this.instructions = instructions;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public Recipe superAppObjectToRecipe(SuperAppObjectBoundary superAppRecipe) {
        Recipe recipe = new Recipe();

        Map<String,Object> details = superAppRecipe.getObjectDetails();


        recipe.setName((String) details.get(RECIPE_NAME));
        recipe.setDescription((String) details.get(RECIPE_DESCRIPTION));
        recipe.setCookingTime((String) details.get(RECIPE_COOKING_TIME));
        recipe.setServingSize((String) details.get(RECIPE_SERVING_SIZE));


//        Map<String, Object> nutritionalInfo = (Map<String, Object>) details.get(RECIPE_NUTRITIONAL_INFO);
//        recipe.setNutritionalInfo(
//                new NutritionalInfo(
//                        (String) nutritionalInfo.get(NUTRITIONAL_INFO_CALORIES),
//                        (String) nutritionalInfo.get(NUTRITIONAL_INFO_FAT),
//                        (String) nutritionalInfo.get(NUTRITIONAL_INFO_CARBS),
//                        (String) nutritionalInfo.get(NUTRITIONAL_INFO_PROTEIN)
//                )
//        );


        //TODO: fix this

//        recipe.setNutritionalInfo(new NutritionalInfo(superAppRecipe.getNutritionalInfo().getCalories(), superAppRecipe.getNutritionalInfo().getFat(), superAppRecipe.getNutritionalInfo().getCarbs(), superAppRecipe.getNutritionalInfo().getProtein()));
//        recipe.setIngredients(superAppIngredientsToIngredients(superAppRecipe.getIngredients()));
//        recipe.setInstructions(superAppInstructionsToInstructions(superAppRecipe.getInstructions()));
        return recipe;
    }
}
