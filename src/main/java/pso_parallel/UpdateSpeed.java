package pso_parallel;

import java.util.ArrayList;

import pso_network.Individual;
import pso_network.Space;

public class UpdateSpeed extends Thread {
	private ArrayList<Individual> population;
	private double minimumStep;
	private Space space;

	public UpdateSpeed(ArrayList<Individual> population, double minimumStep, Space space) {
		this.population = population;
		this.minimumStep = minimumStep;
		this.space = space;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
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
}
