package pso_parallel;

import java.util.ArrayList;
import java.util.Random;

import pso_network.Direction;
import pso_network.Foodlocation;
import pso_network.Individual;

public class UpdateDistanceAndDirection extends Thread {
	private ArrayList<Individual> population;
	private Random random;

	public UpdateDistanceAndDirection(ArrayList<Individual> population, Random random) {
		// TODO Auto-generated constructor stub
		this.population = population;
		this.random = random;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
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
			if (distance == 0)
				sourc.setDirection(new Direction(sourc.getFoodlocation().getLocation(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble(),
						(random.nextBoolean() ? 1 : -1) * random.nextDouble()));// no run
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

}
