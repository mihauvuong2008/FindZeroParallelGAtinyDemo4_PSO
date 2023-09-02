package ga_training.selection;

import java.util.ArrayList;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.selection.dataAdjuster.DataAdjuster;
import ga_training.selection.dataAdjuster.MaxMinRemaker;
import ga_training.selection.dataAdjuster.SmooothGate;

public class CoinFlipGameSelection {

	public CoinFlipGameSelection() {

	}

	public final ArrayList<GENE> select(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores,
			Random ran, int selectionSize) {
		DataAdjuster dataAdjuster = new DataAdjuster();
		MaxMinRemaker maxMinRemaker = dataAdjuster.getMaxMinRemaker(candidateSet);
		double[] remakeCandidateSet = maxMinRemaker.getArrayData();

		SmooothGate smthPosition = dataAdjuster.getSmooothPosition(remakeCandidateSet);
		remakeCandidateSet = averageSmooth(remakeCandidateSet, smthPosition);
		smthPosition = dataAdjuster.getSmooothPosition(remakeCandidateSet);
		if (naturalFitnessScores)
			remakeCandidateSet = powSmooth(remakeCandidateSet, smthPosition);
		if (naturalFitnessScores)
			smthPosition = dataAdjuster.getSmooothPosition(remakeCandidateSet);
		ArrayList<GENE> selection = new ArrayList<>();
		GENE ele;
		for (int i = 0; i < selectionSize; i++) {
			ele = coinFlipSearch(remakeCandidateSet, candidateSet, smthPosition, ran);
			selection.add(ele);
		}
		return selection;
	}

	private final GENE coinFlipSearch(double[] remakeCandidateSet, ArrayList<EvaluatedCandidate> candidateSet,
			SmooothGate smthgt, Random coinflipRandom) {

		Random indexRandom = new Random();
		int len = remakeCandidateSet.length;
		GENE candidate = null;
		double delta = Math.abs(smthgt.getAverage() - smthgt.getFakeAverage());
		int index;
		double Coinflip;
		while (candidate == null) {
			index = indexRandom.nextInt(len);
			Coinflip = coinflipRandom.nextDouble() * delta * (coinflipRandom.nextDouble() > 0.5 ? 1d : -1d);
			if (remakeCandidateSet[index] >= smthgt.getAverage() + Coinflip) {
				candidate = candidateSet.get(index).getCandidate();
			}
		}
		return candidate;
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

	private final double[] powSmooth(double[] remakeCandidateSet, SmooothGate smthgt) {
		int len = remakeCandidateSet.length;
		double[] rs = new double[len];
		for (int i = 0; i < len; i++) {
			rs[i] = Math.pow(remakeCandidateSet[i], smthgt.getOmega());
		}
		return rs;
	}

}
