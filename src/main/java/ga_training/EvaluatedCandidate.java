package ga_training;

public class EvaluatedCandidate implements Comparable<EvaluatedCandidate> {

	// tap hop danh gia ung vien trong quan the

	private GENE candidate;
	private double index = 0;

	public GENE getCandidate() {
		return candidate;
	}

	public void setCandidate(GENE candidate) {
		this.candidate = candidate;
	}

	public double getFitness() {
		return index;
	}

	public void setIndex(double index) {
		this.index = index;
	}

	@Override
	public int compareTo(EvaluatedCandidate o) {
		return -1 * Double.compare(this.getFitness(), o.getFitness());
	}
}
