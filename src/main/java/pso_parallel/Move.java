package pso_parallel;

import java.util.ArrayList;
import java.util.Random;

import pso_network.Individual;
import pso_network.Position;
import pso_network.Space;

public class Move extends Thread {
	private ArrayList<Individual> population;
	private double maximumStep;
	private Space space;
	private Random random;

	public Move(ArrayList<Individual> population, double maximumStep, Space space) {
		this.population = population;
		this.maximumStep = maximumStep;
		this.space = space;
		random = new Random();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual ele = population.get(i);

			double x = ele.getPosition().getX() + ele.getSpeed() * maximumStep * (random.nextDouble() * 0.3 + 0.7)
					* ele.getDirection().getxWeight();
			double y = ele.getPosition().getY() + ele.getSpeed() * maximumStep * (random.nextDouble() * 0.3 + 0.7)
					* ele.getDirection().getyWeight();
			double z = ele.getPosition().getZ() + ele.getSpeed() * maximumStep * (random.nextDouble() * 0.3 + 0.7)
					* ele.getDirection().getzWeight();
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
}
