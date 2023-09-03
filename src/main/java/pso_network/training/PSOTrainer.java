package pso_network.training;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import pso_network.Individual;
import pso_network.Map;
import pso_network.Space;
import pso_network.evolution.Valuer;

public class PSOTrainer {

	static Logger log = Logger.getLogger("PSOTrainer");

	int maxspeedSpaceStep = 10;
	int loopTotal = 200;
	int sizeOfPopulation = 2500;
	Space space;

	public PSOTrainer(int sizeOfPopulation, int maxspeedSpaceStep, Space space, int loopTotal) {
		super();
		this.maxspeedSpaceStep = maxspeedSpaceStep;
		this.space = space;
		this.loopTotal = loopTotal;
	}

	public double train() throws InterruptedException {
		double solution = -1;
		pso_network.Map map;
		Random random = new Random();
		Valuer valuer = new Valuer();

		map = new Map(space, random);
		map.setSizeOfPopulation(sizeOfPopulation);
		initMap(map, valuer);
		Individual result = null;
		ArrayList<Individual> population = map.getPopulation();
		log.info("PSO begin");
		for (int i = 0; i < loopTotal; i++) {

//			System.out.println("populationValue... ");
			valuer.populationValue(population);

			result = getBestResult(map.getPopulation());

//			System.out.println("transferInformation... ");
			map.transferInformation();

//			System.out.println("updateDistanceAndDirection... ");
			map.updateDistanceAndDirection();

//			System.out.println("updateSpeed... ");
			map.updateSpeed();

//			System.out.println("moving... ");
			map.move(population);

		}

		log.info(
				"PSO complete, raw x: "
						+ valuer._x(result.getPosition().getX(), result.getPosition().getY(),
								result.getPosition().getZ())
						+ "(" + result.getPosition().getX() + ", y: " + result.getPosition().getY() + ", z: "
						+ result.getPosition().getZ() + ")");

		if (result != null)
			solution = valuer._x(result.getPosition().getX(), result.getPosition().getY(), result.getPosition().getZ());
		return solution;
	}

	private Individual getBestResult(ArrayList<Individual> population) {
		double max = -1 * Double.MAX_VALUE;
		Individual rs = null;
		for (Individual individual : population) {
			if (max < individual.getValue()) {
				max = individual.getValue();
				rs = individual;
			}
		}
		return rs;
	}

	private void initMap(Map map, Valuer valuer) {
		ArrayList<Individual> population = map.genaratePopulation();
		map.setupNetwork(population);
		System.out
				.println("population.size: " + population.size() + ", connectionsize: " + map.getConnections().size());
	}
}
