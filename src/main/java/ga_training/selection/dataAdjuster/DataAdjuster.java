package ga_training.selection.dataAdjuster;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ga_training.EvaluatedCandidate;

public class DataAdjuster {

	private final static double MAX = Double.MAX_VALUE;
	private final static double MIN = 0;// Double.MIN_VALUE;

	public String ZERO_PATTERN(int SIZE) {
		String rs = "";
		for (int i = 0; i < SIZE; i++) {
			rs += "0";
		}
		return rs;

	}

	public double rawDoubletoDouble(double sigma, double Range, int size) {
		if (Double.isInfinite(sigma) || Double.isNaN(sigma)) {
			sigma = Double.MAX_VALUE;
		}
		if (Double.isInfinite(Range)) {
			Range = Double.MAX_VALUE;
		}
		sigma = Math.abs(sigma);
		DecimalFormat df = new DecimalFormat("0.#");
		df.setMaximumFractionDigits((int) (Range + 0.5));
		String f = ZERO_PATTERN(size) + df.format(sigma);
		int flength = f.length();
		String rsStr = f.substring(flength - size, flength);
		double rs = Double.valueOf(rsStr) / Math.pow(10, size);
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
		if (Double.isInfinite(max)) {
			max = MAX;
		}
		if (size == 0)
			return new SmooothGate(0, 0, 0);
		double average = averageCount / size;
		fakeAverage = (max + min) / (2);
		if (average == 0 || Double.isInfinite(average) || Double.isInfinite(fakeAverage))
			return new SmooothGate(0, 0, 0);
		double tmp = fakeAverage / average;
		double omega = ((tmp < 1) ? tmp : (1 / tmp));
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
