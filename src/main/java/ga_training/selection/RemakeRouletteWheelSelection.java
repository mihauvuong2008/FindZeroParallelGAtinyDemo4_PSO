package ga_training.selection;

import java.util.ArrayList;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.selection.dataAdjuster.DataAdjuster;
import ga_training.selection.dataAdjuster.MaxMinRemaker;

public class RemakeRouletteWheelSelection {

	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = 0;// Double.MIN_VALUE;

	private final int SIZE = 4;// in your right hand

	public RemakeRouletteWheelSelection() {

	}

	public final ArrayList<GENE> select(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores,
			Random ran, int selectionSize) {
		int size = candidateSet.size();
		DataAdjuster dataAdjuster = new DataAdjuster();
		MaxMinRemaker maxMinRemaker = dataAdjuster.getMaxMinRemaker(candidateSet);
		double[] remakeCandidateSet = maxMinRemaker.getArrayData();

		double sigma = maxMinRemaker.getMin() / maxMinRemaker.getMax();
		double Range = -1 * Math.log10(sigma) + SIZE - 1;
		double _s;
		// === true remakeCandidateSet=====
		if (naturalFitnessScores)
			for (int i = 0; i < size; i++) {
				_s = dataAdjuster.rawDoubletoDouble(remakeCandidateSet[i], Range, SIZE);
				remakeCandidateSet[i] = _s;
			}

		SmooothGate smthPosition = getSmooothPosition(remakeCandidateSet);
		remakeCandidateSet = averageSmooth(remakeCandidateSet, smthPosition);
		smthPosition = getSmooothPosition(remakeCandidateSet);
		remakeCandidateSet = powSmooth(remakeCandidateSet, smthPosition);
		smthPosition = getSmooothPosition(remakeCandidateSet);

		double[] remakeRouletteWheel = new double[size];
		remakeRouletteWheel[0] = remakeCandidateSet[0];
		for (int i = 1; i < size; i++) {
			remakeRouletteWheel[i] = remakeCandidateSet[i] + remakeRouletteWheel[i - 1];
		}
		ArrayList<GENE> selection = new ArrayList<>();
		GENE ele;
//		int count = 0;
		for (int i = 0; i < selectionSize; i++) {
			ele = roulettWhellSelection2(remakeRouletteWheel, candidateSet, smthPosition, ran);
//			if (ele == null) {
//				count++;
//			}
			selection.add(ele);
		}
//		System.out.println("selectionSize: " + selectionSize + ". count: " + count);
		return selection;
	}

	private final GENE roulettWhellSelection2(double[] remakeRouletteWheel, ArrayList<EvaluatedCandidate> candidateSet,
			SmooothGate smthPosition, Random ran) {

		int size = remakeRouletteWheel.length;
		double finder;
		double lastRemake = remakeRouletteWheel[size - 1];
		Random shake = new Random();
		double delta = 0.5 * shake.nextDouble() * smthPosition.getOmega() * smthPosition.getAverage();
		finder = ran.nextDouble() * lastRemake - 0.5 + delta;
		if (finder <= remakeRouletteWheel[0]) {
			GENE gene = new GENE();
			gene.setGene(candidateSet.get(0).getCandidate().getGene());
			return gene;
		}

		GENE gene = null;

//		System.out.println(" begin ");
//		System.out
//				.println("remakeCandidateSet  0: " + remakeCandidateSet[0] + " last: " + remakeCandidateSet[size - 1]);
		for (int i = 1; i < remakeRouletteWheel.length; i++) {

//			System.out.println("findValue :" + finder + ", remakeCandidateSet[i] " + remakeRouletteWheel[i]
//					+ ", remakeCandidateSet[i - 1] " + remakeRouletteWheel[i - 1] + ", lastRemake: " + lastRemake
//					+ ", delta: " + delta + ", Omega: " + smthPosition.getOmega() + ", Average: "
//					+ smthPosition.getAverage());

			if (/* finder <= remakeRouletteWheel[i] && */finder >= remakeRouletteWheel[i - 1]) {
				gene = new GENE();
				gene.setGene(candidateSet.get(i).getCandidate().getGene());
				break;
			}
		}
//		if (gene != null) {
//			System.out.println(true);
//		} else {
//			System.out.println(false);
//		}
		return gene;
	}

	private final double[] powSmooth(double[] remakeCandidateSet, SmooothGate smthgt) {
		int len = remakeCandidateSet.length;
		double[] rs = new double[len];
		for (int i = 0; i < len; i++) {
			rs[i] = Math.pow(remakeCandidateSet[i], smthgt.getOmega());
		}
		return rs;
	}

	private final double[] averageSmooth(double[] remakeCandidateSet, SmooothGate smthgt) {
		// TODO Auto-generated method stub
		int len = remakeCandidateSet.length;
		double[] rs = new double[len];
		for (int i = 0; i < len; i++) {
			if (remakeCandidateSet[i] >= smthgt.getAverage()) {
				rs[i] = remakeCandidateSet[i] * smthgt.getOmega();
			} else {
				rs[i] = remakeCandidateSet[i] / smthgt.getOmega();
			}
//				System.out.println("fakeAverage " + fakeAverage + ",  average " + average + ", omega: " + omega
//						+ ", remakeCandidateSet[i]: " + remakeCandidateSet[i] + ", rs[i]: " + rs[i]);
		}
		return rs;
	}

	private SmooothGate getSmooothPosition(double[] remakeCandidateSet) {
		double averageCount = 0;
		double fakeAverage = 0;
		double max = MIN;
		double min = MAX;
		int size = remakeCandidateSet.length;
		for (int i = 0; i < size; i++) {
			double ele = remakeCandidateSet[i];
			averageCount += ele;
			if (max < ele) {
				max = ele;
			}
			if (min > ele) {
				min = ele;
			}
		}

		double average = averageCount / size;
		fakeAverage = (max + min) / (2);
		double tmp = fakeAverage / average;
		double omega = (tmp < 1d ? tmp : 1d / tmp);
		SmooothGate smotgt = new SmooothGate(average, fakeAverage, omega);
		return smotgt;
	}

	private class SmooothGate {
		private double average;
		private double fakeAverage;
		private double omega;

		public SmooothGate(double average, double fakeAverage, double omega) {
			super();
			this.average = average;
			this.fakeAverage = fakeAverage;
			this.omega = omega;
		}

		@SuppressWarnings("unused")
		public double getAverage() {
			return average;
		}

		@SuppressWarnings("unused")
		public void setAverage(double average) {
			this.average = average;
		}

		@SuppressWarnings("unused")
		public double getFakeAverage() {
			return fakeAverage;
		}

		@SuppressWarnings("unused")
		public void setFakeAverage(double fakeAverage) {
			this.fakeAverage = fakeAverage;
		}

		public double getOmega() {
			return omega;
		}

		@SuppressWarnings("unused")
		public void setOmega(double omega) {
			this.omega = omega;
		}

	}
}
