package superapp.boundary.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import superapp.common.Consts;

public class CookingTime {
    @JsonProperty(Consts.RECIPE_COOKING_TIME_DURATION)
    private String duration;
    @JsonProperty(Consts.RECIPE_COOKING_TIME_UNIT)
    private String unit;

    public CookingTime() {
    }

    public CookingTime(String duration, String unit) {
        this.duration = duration;
        this.unit = unit;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "CookingTime{" +
                "duration='" + duration + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

}
