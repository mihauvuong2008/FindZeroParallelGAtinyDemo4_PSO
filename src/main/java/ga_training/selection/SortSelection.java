package ga_training.selection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.selection.dataAdjuster.DataAdjuster;
import ga_training.selection.dataAdjuster.MaxMinRemaker;

public class SortSelection {
	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = Double.MIN_VALUE;

	private final int SIZE = 4;// in your right hand
	private final String ZERO_PATTERN = "000";// len = SIZE - 1

	public final ArrayList<GENE> select(ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores,
			Random ran, int size) {

		ArrayList<EvaluatedCandidate> _candidateSet = reMakecandidateSet(candidateSet);
		double min = MAX, max = MIN;
		int len = candidateSet.size();
		for (int i = 0; i < _candidateSet.size(); i++) {
			double ele = _candidateSet.get(i).getFitness();
			if (Double.compare(max, ele) < 0) {
				max = ele;
			}
			if (Double.compare(min, ele) > 0) {
				min = ele;
			}
//			System.out.println("_candidateSet: " + _candidateSet.get(i).getFitness() + ", candidateSet: "
//					+ candidateSet.get(i).getFitness());
		}

		Collections.sort(_candidateSet);
//		int z = 0;
//		for (EvaluatedCandidate evaluatedCandidate : _candidateSet) {
//			z++;
//			System.out.println("evaluatedCandidate.getFitness(): " + evaluatedCandidate.getFitness() + ", z " + z);
//		}

//		System.out.println(_candidateSet.get(0).getFitness() + " + " + _candidateSet.get(len / 2 - 1).getFitness()
//				+ " + " + _candidateSet.get(len - 1).getFitness());

//		double tan = tan(max, min);
		double cotan = min / max;
		double gama = cotan * len;
		gama = (gama >= 1d ? 1d : gama);
		ArrayList<GENE> rs = new ArrayList<>();
		int index = 0;
		for (int i = 0; i < size; i++) {
			index = (int) (ran.nextInt(len) * ran.nextDouble() * ran.nextDouble() * gama);
//			System.out.println("index" + index);
			GENE g = new GENE();
			g.setGene(candidateSet.get(index).getCandidate().getGene());
			rs.add(g);
		}
		return rs;
	}

	private final ArrayList<EvaluatedCandidate> reMakecandidateSet(ArrayList<EvaluatedCandidate> candidateSet) {
		ArrayList<EvaluatedCandidate> rs = new ArrayList<>();
		int size = candidateSet.size();

		DataAdjuster dataAdjuster = new DataAdjuster();
		MaxMinRemaker maxMinRemaker = dataAdjuster.getMaxMinRemaker(candidateSet);
		double[] remakeCandidateSet = maxMinRemaker.getArrayData();

		// === init remakeCandidateSet=====

		double sigma = maxMinRemaker.getMin() / maxMinRemaker.getMax();
		double Range = -1 * Math.log10(sigma) + SIZE - 1;
		double _s;
		// === true remakeCandidateSet=====
		for (int i = 0; i < size; i++) {
			_s = rawDoubletoDouble(remakeCandidateSet[i], Range, SIZE);
//			System.out.println("_s: " + _s + ", Finess: " + candidateSet.get(i).getFitness());
			EvaluatedCandidate ec = new EvaluatedCandidate();
			ec.setCandidate(candidateSet.get(i).getCandidate());
			ec.setIndex(_s);
			rs.add(ec);
		}

		return rs;
	}

	private final double rawDoubletoDouble(double sigma, double Range, int size) {
		DecimalFormat df = new DecimalFormat("0.#");
		df.setMaximumFractionDigits((int) (Range + 0.5));
		String f = ZERO_PATTERN + df.format(sigma);
		String rsStr = f.substring(f.length() - size, f.length());
		double rs = Double.valueOf(rsStr) / Math.pow(10, size);
//		System.out.println("sigma " + sigma + " rsStr: " + rsStr + ", rs: " + rs);
		return rs;
	}

	@SuppressWarnings("unused")
	private final double tan(double max, double min) {
		return max / min;
	}

	public class CustomComparator implements Comparator<EvaluatedCandidate> {
		public CustomComparator() {
			super();
		}

		@Override
		public int compare(EvaluatedCandidate o1, EvaluatedCandidate o2) {
			if (o1.getFitness() < o2.getFitness())
				return 1;
			else if (o1.getFitness() >= o2.getFitness())
				return -1;
			else
				return 0;
		}
	}
}
