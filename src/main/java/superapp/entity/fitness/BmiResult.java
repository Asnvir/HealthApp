package superapp.entity.fitness;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmiResult {

    private double bmi;
    private String health;
    private String healthyBmiRange;

public BmiResult() {
    }

    public BmiResult(double bmi, String health, String healthyBmiRange) {
        this.bmi = bmi;
        this.health = health;
        this.healthyBmiRange = healthyBmiRange;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHealthyBmiRange() {
        return healthyBmiRange;
    }
    @JsonProperty("healthy_bmi_range")
    public void setHealthyBmiRange(String healthyBmiRange) {
        this.healthyBmiRange = healthyBmiRange;
    }
}
