package superapp.boundary.fitness;

public class IdealWeightResult {

    private double Hamwei;
    private double Devine;
    private double Miller;
    private double Robinson;


    public IdealWeightResult() {
    }

    public IdealWeightResult(double Hamwei, double Devine, double Miller, double Robinson) {
        this.Hamwei = Hamwei;
        this.Devine = Devine;
        this.Miller = Miller;
        this.Robinson = Robinson;
    } 

    public double getHamwei() {
		return Hamwei;
	}

	public void setHamwei(double hamwei) {
		Hamwei = hamwei;
	}

	public double getDevine() {
		return Devine;
	}

	public void setDevine(double devine) {
		Devine = devine;
	}

	public double getMiller() {
		return Miller;
	}

	public void setMiller(double miller) {
		Miller = miller;
	}

	public double getRobinson() {
		return Robinson;
	}

	public void setRobinson(double robinson) {
		Robinson = robinson;
	}
}
