package ga_training.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;

public class RouletteWheelSelection {
	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = 0;// Double.MIN_VALUE;

	private int WHEEL_BinnarySearch(double candi_position, double arr[]) {
		if (candi_position < arr[0])
			return 0;

		int idx = 0;
		for (int i = 0; i < arr.length; i++) {
			if (Double.compare(candi_position, arr[i]) > 0) {
				idx = i + 1;
			} else {
//				System.out.println("index:" + idx + ", cumulativeFitnesses: " + arr[i]);
				break;
			}
		}
		return idx;
	}

	private double getAdjustedFitness(double rawFitness, Boolean naturalFitnessScores) {
		if (naturalFitnessScores) {// option
			return rawFitness;
		} else {
			if (rawFitness == 0) {
				return Double.POSITIVE_INFINITY;
			} else {
				return (1 / (rawFitness));
			}
		}
	}

	public ArrayList<GENE> select(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores, Random ran,
			int selectionSize) {
		int popSize = candidateSet.size();
		double cumulativeFitnesses[] = new double[popSize];
		cumulativeFitnesses[0] = getAdjustedFitness(candidateSet.get(0).getFitness(), naturalFitnessScores);
//		System.out.println("cumulativeFitnesses[" + 0 + "]: " + cumulativeFitnesses[0]);
		for (int i = 1; i < popSize; i++) {
			double fitness = getAdjustedFitness(candidateSet.get(i).getFitness(), naturalFitnessScores);
			// tao banh xe //rawFitness cang lon, fitness tuong thich cang nho.
			cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
//			System.out.println("cumulativeFitnesses[" + i + "]: " + cumulativeFitnesses[i]);
		}

		ArrayList<GENE> selection = new ArrayList<>();

		for (int i = 0; i < selectionSize; i++) {
			double randomFitness = ran.nextDouble() * cumulativeFitnesses[popSize - 1];
			int index = WHEEL_BinnarySearch(randomFitness, cumulativeFitnesses);
			GENE candidate = candidateSet.get(index).getCandidate();
			selection.add(candidate);
		}

		return selection;
	}

	public ArrayList<GENE> select2(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores, Random ran,
			int selectionSize) {
		int candidateSetSize = candidateSet.size();
		double[] remakeCandidateSet = new double[candidateSetSize];
		double average = 0;
		double fakeAverage = 0;
		double max = MIN;
		double min = MAX;
		for (int i = 0; i < candidateSetSize; i++) {
			EvaluatedCandidate element = candidateSet.get(i);
			remakeCandidateSet[i] = element.getFitness();
			average += remakeCandidateSet[i];
			if (max < remakeCandidateSet[i]) {
				max = remakeCandidateSet[i];
			}
			if (min > remakeCandidateSet[i]) {
				min = remakeCandidateSet[i];
			}
		}
		average = average / candidateSetSize;
		fakeAverage = (max + min) / 2;
		remakeCandidateSet = smooth(remakeCandidateSet, average, fakeAverage);

		ArrayList<GENE> selection = new ArrayList<>();
		Random indexRandom = new Random();
		for (int i = 0; i < selectionSize; i++) {
			boolean find = true;
			while (find) {
				int index = indexRandom.nextInt(candidateSetSize);
//				double randomFitness = ran.nextFloat(); 
//				System.out.println("remakeCandidateSet[index]: " + remakeCandidateSet[index] + ", randomFitness "
//						+ index + ", average " + average);
				if (remakeCandidateSet[index] > average * (0.5 + ran.nextDouble())) {
					GENE candidate = candidateSet.get(index).getCandidate();
					selection.add(candidate);
					find = false;
				}
			}
		}

		return selection;
	}

	private double[] smooth(double[] remakeCandidateSet, double average, double fakeAverage) {
		// TODO Auto-generated method stub
		int len = remakeCandidateSet.length;
		double[] rs = new double[len];
		double omega = fakeAverage / average;
		if (omega >= 1) {
			for (int i = 0; i < len; i++) {
				if (remakeCandidateSet[i] >= average) {
					rs[i] = remakeCandidateSet[i] / omega;
				} else {
					rs[i] = remakeCandidateSet[i] * omega;
				}
			}
		} else {
			for (int i = 0; i < len; i++) {
				if (remakeCandidateSet[i] >= average) {
					rs[i] = remakeCandidateSet[i] * omega;
				} else {
					rs[i] = remakeCandidateSet[i] / omega;
				}
			}
		}
		return rs;
	}

}
