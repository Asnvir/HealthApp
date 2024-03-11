package superapp.boundary.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static superapp.common.Consts.NUTRITIONAL_INFO_CALORIES;
import static superapp.common.Consts.NUTRITIONAL_INFO_FAT;
import static superapp.common.Consts.NUTRITIONAL_INFO_CARBS;
import static superapp.common.Consts.NUTRITIONAL_INFO_PROTEIN;


public class NutritionalInfo {

    @JsonProperty(NUTRITIONAL_INFO_CALORIES)
    private String calories;
    @JsonProperty(NUTRITIONAL_INFO_FAT)
    private String fat;
    @JsonProperty(NUTRITIONAL_INFO_CARBS)
    private String carbs;
    @JsonProperty(NUTRITIONAL_INFO_PROTEIN)
    private String protein;

    public NutritionalInfo() {
    }

    public NutritionalInfo(String calories, String fat, String carbs, String protein) {
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public static NutritionalInfo fromObjectToNutritionalInfo(Object nutritionalInfoObject) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> nutritionalInfoMap = objectMapper.convertValue(nutritionalInfoObject, new TypeReference<Map<String, String>>() {});

        return new NutritionalInfo(
                nutritionalInfoMap.get(NUTRITIONAL_INFO_CALORIES),
                nutritionalInfoMap.get(NUTRITIONAL_INFO_FAT),
                nutritionalInfoMap.get(NUTRITIONAL_INFO_CARBS),
                nutritionalInfoMap.get(NUTRITIONAL_INFO_PROTEIN)
        );
    }

    @Override
    public String toString() {
        return "Calories: " + calories + "\n" +
                "Fat: " + fat + "\n" +
                "Carbs: " + carbs + "\n" +
                "Protein: " + protein + "\n";
    }
}
