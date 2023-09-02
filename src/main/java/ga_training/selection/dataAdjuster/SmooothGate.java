package ga_training.selection.dataAdjuster;

public class SmooothGate {
	private double average;
	private double fakeAverage;
	private double omega;

	public SmooothGate(double average, double fakeAverage, double omega) {
		super();
		this.average = average;
		this.fakeAverage = fakeAverage;
		this.omega = omega;
	}

	public double getAverage() {
		return average;
	}

	public double getFakeAverage() {
		return fakeAverage;
	}

	public double getOmega() {
		return omega;
	}
}
