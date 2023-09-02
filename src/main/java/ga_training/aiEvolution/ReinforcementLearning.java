package ga_training.aiEvolution;

import java.util.ArrayList;
import ga_training.EvaluatedCandidate;

public class ReinforcementLearning {
	private int topSize = 100;

	public int getTopSize() {
		return topSize;
	}

	public void setTopSize(int topSize) {
		this.topSize = topSize;
	}

	public ArrayList<EvaluatedCandidate> getTopResult(ArrayList<EvaluatedCandidate> candidateSet) {
		// TODO Auto-generated method stub
		ArrayList<EvaluatedCandidate> rs = new ArrayList<>();
		int len;
		for (EvaluatedCandidate evaluatedCandidate : candidateSet) {
			if (rs.size() == 0)
				rs.add(evaluatedCandidate);
			len = rs.size();
			if (rs.get(len - 1).getFitness() < evaluatedCandidate.getFitness()) {
				push(rs, evaluatedCandidate);
			}
		}
		return rs;
	}

	private void push(ArrayList<EvaluatedCandidate> rs, EvaluatedCandidate evaluatedCandidate) {
		// TODO Auto-generated method stub
		rs.add(evaluatedCandidate);
		if (rs.size() > topSize)
			rs.remove(0);
	}

}
