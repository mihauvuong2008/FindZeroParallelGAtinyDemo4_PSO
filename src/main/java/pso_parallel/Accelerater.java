package pso_parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pso_network.Individual;
import pso_network.Space;

public class Accelerater {
	private static final int maxOfStream = 680;
	private static final int minOfStream = 5;
	private static final int maxSizeOfIngredien = 1200;
	private static final int minSizeOfIngredient = 110;
	private static final int resourceStep = 100;
	private static boolean cPUprioritize = false;
	private static final int FORGIVE_TIME = 1;
	private static StreamInfo sinfo;

	public static void transferInformationAccelerate(ArrayList<Individual> population, int maxHerdSize)
			throws InterruptedException {
		sinfo = setNumOfStream(population.size());
		ArrayList<ArrayList<Individual>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {
			List<Individual> sub = population.subList(i * sinfo.sizeOfIngredient, (i + 1) * sinfo.sizeOfIngredient);
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<Individual> sub = population.subList(last, population.size());

		if (sub.size() != 0) {// catch last element size = 0
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		TransferInformation[] execThread = new TransferInformation[ListSub.size()];

		for (int j = 0; j < ListSub.size(); j++) {
			execThread[j] = new TransferInformation(ListSub.get(j), maxHerdSize);
		}

		Random shake = new Random();
		for (TransferInformation transferInformation : execThread) {
			if (transferInformation != null)
				transferInformation.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		for (TransferInformation transferInformation : execThread) {
			if (transferInformation != null)
				transferInformation.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

	}

	public static void updateDistanceAndDirection(ArrayList<Individual> population, Random random)
			throws InterruptedException {
		sinfo = setNumOfStream(population.size());

		ArrayList<ArrayList<Individual>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {
			List<Individual> sub = population.subList(i * sinfo.sizeOfIngredient, (i + 1) * sinfo.sizeOfIngredient);
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<Individual> sub = population.subList(last, population.size());

		if (sub.size() != 0) {// catch last element size = 0
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		UpdateDistanceAndDirection[] execThread = new UpdateDistanceAndDirection[ListSub.size()];

		for (int j = 0; j < ListSub.size(); j++) {
			execThread[j] = new UpdateDistanceAndDirection(ListSub.get(j), random);
		}

		Random shake = new Random();
		for (UpdateDistanceAndDirection updateDistanceAndDirection : execThread) {
			if (updateDistanceAndDirection != null)
				updateDistanceAndDirection.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		for (UpdateDistanceAndDirection updateDistanceAndDirection : execThread) {
			if (updateDistanceAndDirection != null)
				updateDistanceAndDirection.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}
	}

	public static void updateSpeed(ArrayList<Individual> population, double minimumStep, Space space)
			throws InterruptedException {
		sinfo = setNumOfStream(population.size());

		ArrayList<ArrayList<Individual>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {
			List<Individual> sub = population.subList(i * sinfo.sizeOfIngredient, (i + 1) * sinfo.sizeOfIngredient);
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<Individual> sub = population.subList(last, population.size());

		if (sub.size() != 0) {// catch last element size = 0
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		UpdateSpeed[] execThread = new UpdateSpeed[ListSub.size()];

		for (int j = 0; j < ListSub.size(); j++) {
			execThread[j] = new UpdateSpeed(ListSub.get(j), minimumStep, space);
		}

		Random shake = new Random();
		for (UpdateSpeed updateSpeed : execThread) {
			if (updateSpeed != null)
				updateSpeed.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		for (UpdateSpeed updateSpeed : execThread) {
			if (updateSpeed != null)
				updateSpeed.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}
	}

	public static void move(ArrayList<Individual> population, double maximumStep, Space space)
			throws InterruptedException {
		sinfo = setNumOfStream(population.size());

		ArrayList<ArrayList<Individual>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {
			List<Individual> sub = population.subList(i * sinfo.sizeOfIngredient, (i + 1) * sinfo.sizeOfIngredient);
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<Individual> sub = population.subList(last, population.size());

		if (sub.size() != 0) {// catch last element size = 0
			ArrayList<Individual> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		Move[] execThread = new Move[ListSub.size()];

		for (int j = 0; j < ListSub.size(); j++) {
			execThread[j] = new Move(ListSub.get(j), maximumStep, space);
		}

		Random shake = new Random();
		for (Move move : execThread) {
			if (move != null)
				move.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		for (Move move : execThread) {
			if (move != null)
				move.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}
	}

	public static StreamInfo setNumOfStream(int setSize) {
		if (sinfo == null)
			sinfo = new StreamInfo();
		boolean flag = false;
		if (cPUprioritize) {
			for (int i = minOfStream; i < maxOfStream; i += 1) {
				if (!flag)
					for (int j = minSizeOfIngredient; j < maxSizeOfIngredien; j += resourceStep) {
						if (i * j > setSize) {
							sinfo.numOfStream = i;
							sinfo.sizeOfIngredient = j;
							flag = true;
							break;
						}
					}
			}
		} else {
			for (int j = minSizeOfIngredient; j < maxSizeOfIngredien; j += resourceStep) {
				if (!flag)
					for (int i = minOfStream; i < maxOfStream; i += 1) {
						if (i * j > setSize) {
							sinfo.numOfStream = i;
							sinfo.sizeOfIngredient = j;
							flag = true;
							break;
						}
					}
			}
		}

		if (!flag) {
			sinfo.numOfStream = maxOfStream;
			sinfo.sizeOfIngredient = setSize / sinfo.numOfStream;
		}
		return sinfo;
	}

	private static class StreamInfo {
		public int numOfStream;
		public int sizeOfIngredient;
	}
}
