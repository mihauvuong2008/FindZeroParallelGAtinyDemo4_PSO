package pso_network.training;

import appMain.FindZeroInout;
import appMain.GA_PSO_InOutForm;
import pso_network.Space;

public class PSO_InOut {

	double result;
	int maxspeedSpaceStep = 10;
	int sizeOfPopulation;
	Space space;
	int loopTotal;

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public PSO_InOut(GA_PSO_InOutForm inPso_InOutForm) {
		this.sizeOfPopulation = inPso_InOutForm.getSizeOfPopulation();
		space = inPso_InOutForm.getSpace();
		loopTotal = inPso_InOutForm.getLoopTotal();
	}

	public void action() {
		PSOTrainer trainer = new PSOTrainer(sizeOfPopulation, maxspeedSpaceStep, space, loopTotal);
		result = trainer.train();
		System.out.println("PSO result: " + result + ", loopTotal: " + loopTotal + ", sizeOfPopulation: "
				+ sizeOfPopulation + ", maxspeedSpaceStep: " + maxspeedSpaceStep + ", spaceX: " + space.getMaxX()
				+ ", spaceX: " + space.getMinX());
	}

	static public double y(double x) {
		return FindZeroInout.y(x);
	}
}
