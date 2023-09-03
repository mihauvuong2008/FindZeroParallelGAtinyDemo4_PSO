package pso_network;

import java.util.ArrayList;
import java.util.Random;

import pso_parallel.Accelerater;

public class Map {

	private final int maxHerdSize = 20;
	private final double minimumStep = 1.0E-10;
	private final double maximumStep = 3.0d;
	private int sizeOfPopulation = 5000;
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

	public void transferInformation() throws InterruptedException {
		Accelerater.transferInformationAccelerate(population, maxHerdSize);
	}

	public void updateDistanceAndDirection() throws InterruptedException {
		Accelerater.updateDistanceAndDirection(population, random);
	}

	public void updateSpeed() throws InterruptedException {
		Accelerater.updateSpeed(population, minimumStep, space);
	}

	public void move(ArrayList<Individual> population) throws InterruptedException {
		Accelerater.move(population, maximumStep, space);
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
