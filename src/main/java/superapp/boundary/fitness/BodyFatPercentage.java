package superapp.boundary.fitness;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyFatPercentage {


    private double bodyFatPercentageUSNavy;

    private String bodyFatCategory;

    private double bodyFatMass;

    private double leanBodyMass;

    private double bodyFatBMIMethod;

    public BodyFatPercentage() {
    }

    public BodyFatPercentage(double bodyFatPercentageUSNavy, String bodyFatCategory, double bodyFatMass, double leanBodyMass, double bodyFatBMIMethod) {
        this.bodyFatPercentageUSNavy = bodyFatPercentageUSNavy;
        this.bodyFatCategory = bodyFatCategory;
        this.bodyFatMass = bodyFatMass;
        this.leanBodyMass = leanBodyMass;
        this.bodyFatBMIMethod = bodyFatBMIMethod;
    }

    public double getBodyFatPercentageUSNavy() {
        return bodyFatPercentageUSNavy;
    }
    @JsonProperty("Body Fat (U.S. Navy Method)")
    public void setBodyFatPercentageUSNavy(double bodyFatPercentageUSNavy) {
        this.bodyFatPercentageUSNavy = bodyFatPercentageUSNavy;
    }

    public String getBodyFatCategory() {
        return bodyFatCategory;
    }
    @JsonProperty("Body Fat Category")
    public void setBodyFatCategory(String bodyFatCategory) {
        this.bodyFatCategory = bodyFatCategory;
    }

    public double getBodyFatMass() {
        return bodyFatMass;
    }
    @JsonProperty("Body Fat Mass")
    public void setBodyFatMass(double bodyFatMass) {
        this.bodyFatMass = bodyFatMass;
    }

    public double getLeanBodyMass() {
        return leanBodyMass;
    }
    @JsonProperty("Lean Body Mass")
    public void setLeanBodyMass(double leanBodyMass) {
        this.leanBodyMass = leanBodyMass;
    }

    public double getBodyFatBMIMethod() {
        return bodyFatBMIMethod;
    }
    @JsonProperty("Body Fat (BMI method)")
    public void setBodyFatBMIMethod(double bodyFatBMIMethod) {
        this.bodyFatBMIMethod = bodyFatBMIMethod;
    }
}
