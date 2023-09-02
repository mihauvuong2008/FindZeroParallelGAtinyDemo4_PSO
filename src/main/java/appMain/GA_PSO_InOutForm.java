package appMain;

import pso_network.Space;

public class GA_PSO_InOutForm {
	private Space space;
	private int sizeOfPopulation;
	private int loopTotal;

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public int getSizeOfPopulation() {
		return sizeOfPopulation;
	}

	public void setSizeOfPopulation(int sizeOfPopulation) {
		this.sizeOfPopulation = sizeOfPopulation;
	}

	public int getLoopTotal() {
		return loopTotal;
	}

	public void setLoopTotal(int loopTotal) {
		this.loopTotal = loopTotal;
	}

}
