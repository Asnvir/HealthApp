package superapp.boundary.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

import static superapp.common.Consts.*;

public class Recipe {

    @JsonProperty(RECIPE_NAME)
    private String name;
    @JsonProperty(RECIPE_DESCRIPTION)
    private String description;
    @JsonProperty(RECIPE_INGREDIENTS)
    private ArrayList<Ingredient> ingredients;
    @JsonProperty(RECIPE_INSTRUCTIONS)
    private ArrayList<Instruction> instructions;
    @JsonProperty(RECIPE_COOKING_TIME)
    private String cookingTime;
    @JsonProperty(RECIPE_SERVING_SIZE)
    private String servingSize;
    @JsonProperty(RECIPE_NUTRITIONAL_INFO)
    private NutritionalInfo nutritionalInfo;

    public Recipe() {
    }

    public Recipe(String name, String description, ArrayList<Ingredient> ingredients, ArrayList<Instruction> instructions, String cookingTime, String servingSize, NutritionalInfo nutritionalInfo) {
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
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

    public static Recipe fromObjectDetailstoRecipe(Map<String, Object> objectDetails) {
        Recipe recipe = new Recipe();

        recipe.setName((String) objectDetails.get(RECIPE_NAME));
        recipe.setDescription((String) objectDetails.get(RECIPE_DESCRIPTION));
        recipe.setCookingTime((String) objectDetails.get(RECIPE_COOKING_TIME));
        recipe.setServingSize((String) objectDetails.get(RECIPE_SERVING_SIZE));
        recipe.setIngredients(Ingredient.fromObjectIngredientToIngredients(objectDetails.get(RECIPE_INGREDIENTS)));
        recipe.setInstructions(Instruction.fromObjectInstructionToInstructions(objectDetails.get(RECIPE_INSTRUCTIONS)));
        recipe.setNutritionalInfo(NutritionalInfo.fromObjectToNutritionalInfo(objectDetails.get(RECIPE_NUTRITIONAL_INFO)));

        return recipe;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append(ingredient.toString()).append("\n");
        }
        sb.append("Instructions:\n");
        for (Instruction instruction : instructions) {
            sb.append(instruction.getInstruction()).append("\n");
        }
        sb.append("Cooking Time: ").append(cookingTime).append("\n");
        sb.append("Serving Size: ").append(servingSize).append("\n");
        sb.append("Nutritional Info: ").append("\n").append(nutritionalInfo.toString()).append("\n");
        return sb.toString();
    }
}
