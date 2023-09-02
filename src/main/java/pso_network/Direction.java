package pso_network;

public class Direction {
	private double xWeight;
	private double yWeight;
	private double zWeight;
	private Position position;

	public Direction(Position position, double xWeight, double yWeight, double zWeight) {
		super();
		this.position = position;
		this.xWeight = xWeight;
		this.yWeight = yWeight;
		this.zWeight = zWeight;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getxWeight() {
		return xWeight;
	}

	public void setxWeight(double xWeight) {
		this.xWeight = xWeight;
	}

	public double getyWeight() {
		return yWeight;
	}

	public void setyWeight(double yWeight) {
		this.yWeight = yWeight;
	}

	public double getzWeight() {
		return zWeight;
	}

	public void setzWeight(double zWeight) {
		this.zWeight = zWeight;
	}

}
