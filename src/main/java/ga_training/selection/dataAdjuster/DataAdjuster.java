package ga_training.selection.dataAdjuster;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ga_training.EvaluatedCandidate;

public class DataAdjuster {

	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = 0;// Double.MIN_VALUE;

	private final int SIZE = 4;// in your right hand

	public String ZERO_PATTERN() {
		String rs = "";
		for (int i = 0; i < SIZE; i++) {
			rs += "0";
		}
		return rs;

	}

	public double rawDoubletoDouble(double sigma, double Range, int size) {
		DecimalFormat df = new DecimalFormat("0.#");
		df.setMaximumFractionDigits((int) (Range + 0.5));
		String f = ZERO_PATTERN() + df.format(sigma);
		String rsStr = f.substring(f.length() - size, f.length());
		double rs = Double.valueOf(rsStr) / Math.pow(10, size);
//		System.out.println("sigma " + sigma + " rsStr: " + rsStr + ", rs: " + rs);
		return rs;
	}

	public SmooothGate getSmooothPosition(double[] remakeCandidateSet) {
		double averageCount = 0;
		double fakeAverage = 0;
		double max = MIN;
		double min = MAX;
		int size = remakeCandidateSet.length;
		for (int i = 0; i < size; i++) {
			double ele = remakeCandidateSet[i];
			averageCount += ele;
			if (Double.compare(max, ele) < 0) {
				max = ele;
			}
			if (Double.compare(min, ele) > 0) {
				min = ele;
			}
		}

		double average = averageCount / size;
		fakeAverage = (max + min) / (2);
		double tmp = fakeAverage / average;
		double omega = tmp < 1 ? tmp : 1 / tmp;
		SmooothGate smotgt = new SmooothGate(average, fakeAverage, omega);
		return smotgt;
	}

	public MaxMinRemaker getMaxMinRemaker(ArrayList<EvaluatedCandidate> candidateSet) {
		MaxMinRemaker rs = new MaxMinRemaker();
		int size = candidateSet.size();
		double max = MIN;
		double min = MAX;
		double averageCount = 0;
		double ele = 0;
		double[] remakeCandidateSet = new double[size];
		EvaluatedCandidate element;
		for (int i = 0; i < size; i++) {
			element = candidateSet.get(i);
			ele = remakeCandidateSet[i] = element.getFitness();
			averageCount += ele;
			if (Double.compare(max, ele) < 0) {
				max = ele;
			}
			if (Double.compare(min, ele) > 0) {
				min = ele;
			}
		}
		double fakeAverage = 0;
		double average = averageCount / size;
		fakeAverage = (max + min) / (2);
		rs.setMax(max);
		rs.setMin(min);
		rs.setAverage(average);
		rs.setFakeAverage(fakeAverage);
		rs.setArrayData(remakeCandidateSet);
		return rs;
	}

}
