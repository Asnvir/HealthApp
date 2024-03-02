package superapp.boundary.fitness;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdealWeightResult {

    @JsonProperty("Hamwi")
    private double hamwei;
    
    @JsonProperty("Devine")
    private double devine;
    
    @JsonProperty("Miller")
    private double miller;
    
    @JsonProperty("Robinson")
    private double robinson;
    
    private double avg;

    public IdealWeightResult() {
    }

    public IdealWeightResult(double hamwei, double devine, double miller, double robinson) {
        this.hamwei = hamwei;
        this.devine = devine;
        this.miller = miller;
        this.robinson = robinson;
        this.avg = (this.hamwei + this.devine + this.miller + this.robinson)/4;
    } 

    public double getHamwei() {
        return hamwei;
    }

    public void setHamwei(double hamwei) {
        this.hamwei = hamwei;
    }

    public double getDevine() {
        return devine;
    }

    public void setDevine(double devine) {
        this.devine = devine;
    }

    public double getMiller() {
        return miller;
    }

    public void setMiller(double miller) {
        this.miller = miller;
    }

    public double getRobinson() {
        return robinson;
    }

    public void setRobinson(double robinson) {
        this.robinson = robinson;
    }

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	public void updateAVG() {
		this.avg =(this.hamwei + this.devine + this.miller + this.robinson)/4;
	}
    
}
