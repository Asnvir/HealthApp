package superapp.boundary.fitness;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyCalorieBudgetResult {

    @JsonProperty("BMR")
    private double BMR;
    @JsonProperty("DAILY_CALORIES")
    private int dailyCalories;

    private Map<String, Object> goals;

    public DailyCalorieBudgetResult() {
    }

    public DailyCalorieBudgetResult(double BMR, Map<String, Object> goals, int dailyCalories) {
        this.BMR = BMR;
        this.goals = goals;
        this.dailyCalories = dailyCalories;
    }

    public double getBMR() {
        return BMR;
    }

    public void setBMR(double BMR) {
        this.BMR = BMR;
    }

    public Map<String, Object> getGoals() {
        return goals;
    }

    public int getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(int dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public void setGoals(Map<String, Object> goals) {
        this.goals = goals;
    }
}
