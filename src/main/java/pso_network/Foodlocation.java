package pso_network;

public class Foodlocation {
	private Position location;
	private double distance;
	private double value;

	public Foodlocation(Position location, double value) {
		super();
		this.location = location;
		this.value = value;
	}

	public Position getLocation() {
		return location;
	}

	public void setLocation(Position location) {
		this.location = location;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
