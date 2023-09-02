package pso_network;

import java.util.ArrayList;
import java.util.Random;

public class Map {

	private final int maxHerdSize = 20;
	private final double minimumStep = 1.0E-6;
	private final double maximumStep = 5.0d;
	private int sizeOfPopulation = 12500;
	private ArrayList<Individual> population;
	private ArrayList<Connection> connections;
	private Space space;
	private Random random;

	public Map(Space space, Random random) {
		this.space = space;
		connections = new ArrayList<>();
		population = new ArrayList<>();
		this.random = random;
	}

	public int getSizeOfPopulation() {
		return sizeOfPopulation;
	}

	public void setSizeOfPopulation(int sizeOfPopulation) {
		this.sizeOfPopulation = sizeOfPopulation;
	}

	public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public void setupNetwork(ArrayList<Individual> population) {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual source = population.get(i);
			for (int j = 0; j < maxHerdSize; j++) {
				int index = random.nextInt(size - 1) + 1;
				Individual dest = population.get(index);

				Connection connection = new Connection(source, dest);
				source.getConnectFromMe().add(connection);
				dest.getConnectToMe().add(connection);
				connections.add(connection);
			}
//				System.out.println("source connec Fromme: " + source.getConnectFromme().size());
		}

	}

	public void transferInformation() {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual source = population.get(i);
			ArrayList<Connection> connectFromMe = source.getConnectFromMe();
			ArrayList<Connection> connectToMe = source.getConnectToMe();
			Foodlocation message = null;
			double max = source.getValue();
			message = new Foodlocation(source.getPosition(), max);
			for (Connection connection : connectToMe) {
				double tmp = connection.getSource().getValue();
				if (max < tmp) {
					max = tmp;
					message = new Foodlocation(connection.getSource().getPosition(), tmp);
				}
			}
			for (Connection connection : connectFromMe) {
				double tmp = connection.getDest().getValue();
				if (max < tmp) {
					max = tmp;
					message = new Foodlocation(connection.getSource().getPosition(), tmp);
				}
			}
			source.setFoodlocation(new Foodlocation(source.getPosition(), source.getValue()));
			if (max < source.getValue()) {
				message = new Foodlocation(source.getPosition(), source.getValue());
				source.setFoodlocation(message);
			}

			int connectFromMeSize = source.getConnectFromMe().size();
			for (int j = 0; j < connectFromMeSize; j++) {
				pushMessage(connectFromMe.get(j).getDest().getFriendMessage(), message);
			}

			int connectToMeeSize = connectToMe.size();
			for (int j = 0; j < connectToMeeSize; j++) {
				pushMessage(connectToMe.get(j).getSource().getFriendMessage(), message);
			}
		}
	}

	private void pushMessage(ArrayList<Foodlocation> arrayList, Foodlocation message) {
		if (message != null)
			arrayList.add(message);
		int fullyConnectSize = maxHerdSize * 2;
		if (arrayList.size() <= fullyConnectSize) {
			return;
		}
		arrayList.remove(0);
	}

	public void updateDistanceAndDirection() {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual sourc = population.get(i);
			Foodlocation dest = getBestFoodlocation(sourc);
			double dx = dest.getLocation().getX() - sourc.getPosition().getX();
			double dy = dest.getLocation().getY() - sourc.getPosition().getY();
			double dz = dest.getLocation().getZ() - sourc.getPosition().getZ();
			double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2), 0.5);
			sourc.getFoodlocation().setDistance(distance);
			double max = Math.abs(dx);
			if (max < Math.abs(dy))
				max = Math.abs(dy);
			if (max < Math.abs(dz))
				max = Math.abs(dz);
			if (max > 0) {
				dx = dx / max;
				dy = dy / max;
				dz = dz / max;
				sourc.setDirection(new Direction(sourc.getFoodlocation().getLocation(), dx, dy, dz));
			}
//			System.out.println("distance: " + distance);
//			System.out.println(
//					"sourc.getFoodlocation().getLocation().getZ(): " + sourc.getFoodlocation().getLocation().getZ());
//			System.out.println("sourc.getPosition().getZ()): " + sourc.getPosition().getZ());
//			System.out.println("Direction: " + dx + ", " + dy + ", " + dz);
//			System.out.println("max: " + max);

			if (distance == 0)
				sourc.setDirection(new Direction(sourc.getFoodlocation().getLocation(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble()));// no run
//			System.out.println("Direction: " + sourc.getDirection().getxWeight());
//			System.out.println("Direction: " + sourc.getDirection().getyWeight());
//			System.out.println("Direction: " + sourc.getDirection().getzWeight());
		}
	}

	private Foodlocation getBestFoodlocation(Individual sourc) {
		Foodlocation result = new Foodlocation(sourc.getPosition(), sourc.getValue());
		double max = result.getValue();
		for (Foodlocation foodlocation : sourc.getFriendMessage()) {
			if (max < foodlocation.getValue()) {
				result = foodlocation;
			}
		}
		return result;
	}

	public void updateSpeed() {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual source = population.get(i);
			double speed = 0d;
			speed = (double) source.getFoodlocation().getDistance() / (double) space.getMaxDistance();
			if (speed == 0)
				speed = minimumStep;
			source.setSpeed(speed);
		}
	}

	public void move(ArrayList<Individual> population) {
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual ele = population.get(i);

//			System.out.println("ele.getDirection().getxWeight(): " + ele.getDirection().getxWeight());
//			System.out.println("1ele.getSpeed(): " + ele.getSpeed());
//			System.out.println("2ele.getSpeed(): " + ele.getDirection().getxWeight());
//			System.out.println("3ele.getSpeed(): " + ele.getSpeed() * maximumStep * ele.getDirection().getxWeight());
			double x = ele.getPosition().getX() + ele.getSpeed() * maximumStep * ele.getDirection().getxWeight();
			double y = ele.getPosition().getY() + ele.getSpeed() * maximumStep * ele.getDirection().getyWeight();
			double z = ele.getPosition().getZ() + ele.getSpeed() * maximumStep * ele.getDirection().getzWeight();
//
			x = ((Double.compare(x, space.getMaxX()) < 0) ? x : space.getMaxX());
			y = ((Double.compare(y, space.getMaxY()) < 0) ? y : space.getMaxY());
			z = ((Double.compare(z, space.getMaxZ()) < 0) ? z : space.getMaxZ());
			x = ((Double.compare(x, space.getMinX()) > 0) ? x : space.getMinX());
			y = ((Double.compare(y, space.getMinY()) > 0) ? y : space.getMinY());
			z = ((Double.compare(z, space.getMinZ()) > 0) ? z : space.getMinZ());

			Position position = new Position(x, y, z);
			ele.setPosition(position);
		}
	}

	public ArrayList<Individual> genaratePopulation() {
		ArrayList<Individual> rs = new ArrayList<>();
		for (int i = 0; i < sizeOfPopulation; i++) {
			double x = (random.nextBoolean() ? 1 : -1) * random.nextDouble() * space.getMaxX();
			double y = (random.nextBoolean() ? 1 : -1) * random.nextDouble() * space.getMaxY();
			double z = (random.nextBoolean() ? 1 : -1) * random.nextDouble() * space.getMaxZ();
			Position position = new Position(x, y, z);
			Individual individual = new Individual(position);
			rs.add(individual);
		}
		this.population = rs;
		return rs;
	}

}
