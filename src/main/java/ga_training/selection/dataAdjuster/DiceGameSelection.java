package ga_training.selection.dataAdjuster;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.selection.dataAdjuster.DataAdjuster;
import ga_training.selection.dataAdjuster.MaxMinRemaker;

public class DiceGameSelection {

	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = 0;// Double.MIN_VALUE;

	private final int SIZE = 4;// in your right hand
	private final String ZERO_PATTERN = "000";// len = SIZE - 1

	public DiceGameSelection() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<GENE> select(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores, Random ran,
			int selectionSize) {
		int size = candidateSet.size();
		DataAdjuster dataAdjuster = new DataAdjuster();
		MaxMinRemaker maxMinRemaker = dataAdjuster.getMaxMinRemaker(candidateSet);
		double[] remakeCandidateSet = maxMinRemaker.getArrayData();

		double sigma = maxMinRemaker.getMin() / maxMinRemaker.getMax();
		double Range = -1 * Math.log10(sigma) + SIZE - 1;
		double averageCount = 0;
		double _s;
		// === true remakeCandidateSet=====
		if (naturalFitnessScores)
			for (int i = 0; i < size; i++) {
				_s = dataAdjuster.rawDoubletoDouble(remakeCandidateSet[i], Range, SIZE);
				remakeCandidateSet[i] = _s;
				averageCount += _s;
//			System.out.println("remakeCandidateSet: " + remakeCandidateSet[i]);
			}

		double average = averageCount / size;
		double theComebackSigma = dataAdjuster.rawDoubletoDouble(sigma, Range, SIZE);//

//		System.out.println("sigmaReduce " + sigmaReduce + ", averageCount: " + averageCount + ", average " + average);
		Random dice1 = new Random();
		Random dice2 = new Random();
		Random dice3 = new Random();
		ArrayList<GENE> selection = new ArrayList<>();
		GENE ele;
		for (int i = 0; i < selectionSize; i++) {
			ele = randomDiceSearch(remakeCandidateSet, candidateSet, dice1, dice2, dice3, ran, average,
					theComebackSigma);
			selection.add(ele);
		}
		return selection;

	}

	private GENE randomDiceSearch(double[] remakeCandidateSet, ArrayList<EvaluatedCandidate> candidateSet, Random dice1,
			Random dice2, Random dice3, Random ran, double average, double sigmaReduce) {
		int len = remakeCandidateSet.length;
		GENE candidate = null;
		double delta;
		while (candidate == null) {
			int index = ran.nextInt(len);
			int tossDice = dice1.nextInt(6) + dice2.nextInt(6) + dice3.nextInt(6);
			int toss = index + tossDice;
			if (toss < len) {
				delta = (ran.nextDouble() > 0.5 ? 1 : -1) * sigmaReduce * ran.nextDouble();
//				System.out.println("delta :" + delta + ", sigmaReduce " + sigmaReduce + ", average " + average
//						+ ", remakeCandidateSet[toss]: " + remakeCandidateSet[toss]);
				if (remakeCandidateSet[toss] >= average + delta) {
					candidate = new GENE();
					candidate.setGene(candidateSet.get(toss).getCandidate().getGene());
				}
			}
		}
		return candidate;
	}
}
