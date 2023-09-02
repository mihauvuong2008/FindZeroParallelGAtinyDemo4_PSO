package pso_network;

public class Connection {
	private Individual source;
	private Individual dest;
	private double distance;

	public Connection(Individual source, Individual dest) {
		super();
		this.source = source;
		this.dest = dest;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Individual getSource() {
		return source;
	}

	public Individual getDest() {
		return dest;
	}

}
