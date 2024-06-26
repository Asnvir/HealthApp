package superapp.boundary.fitness;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyCalorieBudgetResult {

    @JsonProperty("BMR")
    private double BMR;
    
    private Map<String, Object> goals;

    public DailyCalorieBudgetResult() {
    }

    public DailyCalorieBudgetResult(double BMR, Map<String, Object> goals) {
        this.BMR = BMR;
        this.goals = goals;
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

    public void setGoals(Map<String, Object> goals) {
        this.goals = goals;
    }
}
