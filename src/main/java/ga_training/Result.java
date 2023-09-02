package ga_training;

public class Result {
	private double upgradeValue;
	private double result;
	private EvaluatedCandidate error;
	private double valueLevel;
	private Result Backup;

	public Result(double upgradeValue, double result, EvaluatedCandidate error, double valueLevel) {
		super();
		this.upgradeValue = upgradeValue;
		this.result = result;
		this.error = error;
		this.valueLevel = valueLevel;
	}

	public double getUpgradeValue() {
		return upgradeValue;
	}

	public double getResult() {
		return result;
	}

	public EvaluatedCandidate getError() {
		return error;
	}

	public double getValueLevel() {
		return valueLevel;
	}

	public Result getBackup() {
		return Backup;
	}

	public void setBackup(Result backup) {
		Backup = backup;
	}

	public void setValueLevel(double valueLevel) {
		// TODO Auto-generated method stub
		this.valueLevel = valueLevel;

	}

}
