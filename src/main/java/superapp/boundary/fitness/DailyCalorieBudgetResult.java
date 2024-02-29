package superapp.boundary.fitness;

public class DailyCalorieBudgetResult {

    private double BMR;
    private double maintainWeight;
    private Goals goals;

    public DailyCalorieBudgetResult() {
    }

    public DailyCalorieBudgetResult(double BMR, double maintainWeight, Goals goals) {
        this.BMR = BMR;
        this.maintainWeight = maintainWeight;
        this.goals = goals;
    }

    public double getBMR() {
        return BMR;
    }

    public void setBMR(double BMR) {
        this.BMR = BMR;
    }

    public double getMaintainWeight() {
        return maintainWeight;
    }

    public void setMaintainWeight(double maintainWeight) {
        this.maintainWeight = maintainWeight;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public static class Goals {
        private Goal mildWeightLoss;
        private Goal weightLoss;
        private Goal extremeWeightLoss;
        private Goal mildWeightGain;
        private Goal weightGain;
        private Goal extremeWeightGain;

        public Goals() {
        }

        public Goals(Goal mildWeightLoss, Goal weightLoss, Goal extremeWeightLoss, Goal mildWeightGain, Goal weightGain, Goal extremeWeightGain) {
            this.mildWeightLoss = mildWeightLoss;
            this.weightLoss = weightLoss;
            this.extremeWeightLoss = extremeWeightLoss;
            this.mildWeightGain = mildWeightGain;
            this.weightGain = weightGain;
            this.extremeWeightGain = extremeWeightGain;
        }

        public Goal getMildWeightLoss() {
            return mildWeightLoss;
        }

        public void setMildWeightLoss(Goal mildWeightLoss) {
            this.mildWeightLoss = mildWeightLoss;
        }

        public Goal getWeightLoss() {
            return weightLoss;
        }

        public void setWeightLoss(Goal weightLoss) {
            this.weightLoss = weightLoss;
        }

        public Goal getExtremeWeightLoss() {
            return extremeWeightLoss;
        }

        public void setExtremeWeightLoss(Goal extremeWeightLoss) {
            this.extremeWeightLoss = extremeWeightLoss;
        }

        public Goal getMildWeightGain() {
            return mildWeightGain;
        }

        public void setMildWeightGain(Goal mildWeightGain) {
            this.mildWeightGain = mildWeightGain;
        }

        public Goal getWeightGain() {
            return weightGain;
        }

        public void setWeightGain(Goal weightGain) {
            this.weightGain = weightGain;
        }

        public Goal getExtremeWeightGain() {
            return extremeWeightGain;
        }

        public void setExtremeWeightGain(Goal extremeWeightGain) {
            this.extremeWeightGain = extremeWeightGain;
        }
    }

    public static class Goal {
        private String weightChange;
        private int calory;

        public Goal() {
        }

        public Goal(String weightChange, int calory) {
            this.weightChange = weightChange;
            this.calory = calory;
        }

        public String getWeightChange() {
            return weightChange;
        }

        public void setWeightChange(String weightChange) {
            this.weightChange = weightChange;
        }

        public int getCalory() {
            return calory;
        }

        public void setCalory(int calory) {
            this.calory = calory;
        }
    }
}
