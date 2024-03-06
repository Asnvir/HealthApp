package superapp.boundary.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.common.Consts;

import java.io.IOException;
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
    private CookingTime cookingTime;
    @JsonProperty(RECIPE_SERVING_SIZE)
    private String servingSize;
    @JsonProperty(RECIPE_NUTRITIONAL_INFO)
    private NutritionalInfo nutritionalInfo;

    public Recipe() {
    }

    public Recipe(String name, String description, ArrayList<Ingredient> ingredients, ArrayList<Instruction> instructions, CookingTime cookingTime, String servingSize, NutritionalInfo nutritionalInfo) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.nutritionalInfo = nutritionalInfo;
    }

    public Recipe(SuperAppObjectBoundary superAppObjectBoundary) {
        if (superAppObjectBoundary != null) {
            Map<String, Object> objectDetails = superAppObjectBoundary.getObjectDetails();
            if (objectDetails != null && !objectDetails.isEmpty()) {
                this.name = (String) objectDetails.get(RECIPE_NAME);
                this.description = (String) objectDetails.get(RECIPE_DESCRIPTION);
                this.ingredients = RecipeUtil.extractIngredients(objectDetails);
                this.instructions = RecipeUtil.extractInstructions(objectDetails);
                this.cookingTime = RecipeUtil.extractCookingTime(objectDetails);;
//                this.cookingTime = (CookingTime) objectDetails.get(RECIPE_COOKING_TIME);
                this.servingSize = (String) objectDetails.get(RECIPE_SERVING_SIZE);
                this.nutritionalInfo = RecipeUtil.extractNutritionalInfo(objectDetails);
            }
        }
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

    public CookingTime getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(CookingTime cookingTime) {
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

//    public static Recipe fromObjectToRecipe(Object object) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            JsonNode rootNode = objectMapper.valueToTree(object);
//            JsonNode recipeNode = rootNode.path(Consts.OBJECT_DETAILS);
//            return objectMapper.treeToValue(recipeNode, Recipe.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to convert object to Recipe." +
//                    "\nException: " + e.getMessage() +
//                    "\nObject: " + object +
//                    "\nObjectMapper: " + objectMapper
//                    + "\nJsonNode: " + objectMapper.valueToTree(object));
//        }
//    }


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
