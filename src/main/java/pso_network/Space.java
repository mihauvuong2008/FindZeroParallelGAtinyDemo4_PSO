package pso_network;

public class Space {
	private final double maxDistance;

	private double maxX;
	private double maxY;
	private double maxZ;
	private double minX;
	private double minY;
	private double minZ;
	private double stepMaxSpeedX;
	private double stepMaxSpeedY;
	private double stepMaxSpeedZ;

	public Space(int spaceStepMaxspeed, double maxX, double maxY, double maxZ, double minX, double minY, double minZ) {
		super();
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;

		stepMaxSpeedX = (this.getMaxX() - this.getMinX()) / spaceStepMaxspeed;
		stepMaxSpeedY = (this.getMaxX() - this.getMinX()) / spaceStepMaxspeed;
		stepMaxSpeedZ = (this.getMaxX() - this.getMinX()) / spaceStepMaxspeed;

		maxDistance = Math.pow(Math.pow(maxX - minX, 2) + Math.pow(maxY - minY, 2) + Math.pow(maxZ - minZ, 2), 0.5);
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getStepMaxSpeedX() {
		return stepMaxSpeedX;
	}

	public void setStepMaxSpeedX(double stepMaxSpeedX) {
		this.stepMaxSpeedX = stepMaxSpeedX;
	}

	public double getStepMaxSpeedY() {
		return stepMaxSpeedY;
	}

	public void setStepMaxSpeedY(double stepMaxSpeedY) {
		this.stepMaxSpeedY = stepMaxSpeedY;
	}

	public double getStepMaxSpeedZ() {
		return stepMaxSpeedZ;
	}

	public void setStepMaxSpeedZ(double stepMaxSpeedZ) {
		this.stepMaxSpeedZ = stepMaxSpeedZ;
	}

	public double getMaxDistance() {
		return maxDistance;
	}

}
