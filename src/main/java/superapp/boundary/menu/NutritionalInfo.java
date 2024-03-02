package superapp.boundary.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.common.Consts;

import java.util.LinkedHashMap;
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

        NutritionalInfo result = new NutritionalInfo();
        if (!(nutritionalInfoObject instanceof LinkedHashMap<?, ?> nutritionalInfoMap)) {
            throw new IllegalArgumentException("Invalid nutritional info object format.");
        }


        Integer caloriesValue = (Integer) nutritionalInfoMap.get(Consts.NUTRITIONAL_INFO_CALORIES);
        Integer fatValue = (Integer) nutritionalInfoMap.get(Consts.NUTRITIONAL_INFO_FAT);
        Integer carbsValue = (Integer) nutritionalInfoMap.get(Consts.NUTRITIONAL_INFO_CARBS);
        Integer proteinValue = (Integer) nutritionalInfoMap.get(Consts.NUTRITIONAL_INFO_PROTEIN);

        if (caloriesValue == null || fatValue == null || carbsValue == null || proteinValue == null) {
            throw new IllegalArgumentException("Nutritional information cannot be null.");
        }

        result.setCalories(String.valueOf(caloriesValue));
        result.setFat(String.valueOf(fatValue));
        result.setCarbs(String.valueOf(carbsValue));
        result.setProtein(String.valueOf(proteinValue));

        return result;
    }

    @Override
    public String toString() {
        return "Calories: " + calories + "\n" +
                "Fat: " + fat + "\n" +
                "Carbs: " + carbs + "\n" +
                "Protein: " + protein + "\n";
    }
}
