package superapp.boundary.menu;

import java.util.Map;

import static superapp.common.Consts.NUTRITIONAL_INFO_CALORIES;
import static superapp.common.Consts.NUTRITIONAL_INFO_FAT;
import static superapp.common.Consts.NUTRITIONAL_INFO_CARBS;
import static superapp.common.Consts.NUTRITIONAL_INFO_PROTEIN;


public class NutritionalInfo {

    private String calories;
    private String fat;
    private String carbs;
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


}
