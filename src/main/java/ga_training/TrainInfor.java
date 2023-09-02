package ga_training;

public class TrainInfor {
	private int CurrLoop;
	private int CurrTime;
	private int CurrSize;
	private double average;
	Result bestrs;
	Result currrs;
	Result solution;

	public int getCurrLoop() {
		return CurrLoop;
	}

	public void setCurrLoop(int currLoop) {
		CurrLoop = currLoop;
	}

	public int getCurrTime() {
		return CurrTime;
	}

	public void setCurrTime(int currTime) {
		CurrTime = currTime;
	}

	public int getCurrSize() {
		return CurrSize;
	}

	public void setCurrSize(int currSize) {
		CurrSize = currSize;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public Result getBestrs() {
		return bestrs;
	}

	public void setBestrs(Result bestrs) {
		this.bestrs = bestrs;
	}

	public Result getCurrrs() {
		return currrs;
	}

	public void setCurrrs(Result currrs) {
		this.currrs = currrs;
	}

	public Result getSolution() {
		return solution;
	}

	public void setSolution(Result solution) {
		this.solution = solution;
	}

}
