package superapp.boundary.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static superapp.common.Consts.*;


public class Ingredient {
    @JsonProperty(INGREDIENT_NAME)
    private String name;
    @JsonProperty(INGREDIENT_QUANTITY)
    private String quantity;
    @JsonProperty(INGREDIENT_UNIT)
    private String unit;
    @JsonProperty(INGREDIENT_CATEGORY)
    private String category;

    public Ingredient() {
    }

    public Ingredient(String name, String quantity, String unit, String category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static ArrayList<Ingredient> fromObjectIngredientToIngredients(Object ingredientObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Ingredient> ingredients = objectMapper.convertValue(ingredientObject, new TypeReference<>() {});
        return new ArrayList<>(ingredients);
    }


    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Quantity: " + quantity + "\n" +
                "Unit: " + unit + "\n" +
                "Category: " + category + "\n";
    }
}
