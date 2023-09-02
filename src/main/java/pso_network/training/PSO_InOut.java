package pso_network.training;

import appMain.FindZeroInout;
import appMain.GA_PSO_InOutForm;
import pso_network.Space;

public class PSO_InOut {

	private double result;
	private static double upgrade;
	private int maxspeedSpaceStep = 10;
	private int sizeOfPopulation;
	private Space space;
	private int loopTotal;

	public double getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(double upgrade) {
		PSO_InOut.upgrade = upgrade;
	}

	public double getResult() {
		return result;
	}

	public PSO_InOut(GA_PSO_InOutForm inPso_InOutForm) {
		this.sizeOfPopulation = inPso_InOutForm.getSizeOfPopulation();
		space = inPso_InOutForm.getSpace();
		loopTotal = inPso_InOutForm.getLoopTotal();
	}

	public void action() {
		PSOTrainer trainer = new PSOTrainer(sizeOfPopulation, maxspeedSpaceStep, space, loopTotal);
		result = upgrade + trainer.train();
		System.out.println("PSO result: " + result + ", loopTotal: " + loopTotal + ", sizeOfPopulation: "
				+ sizeOfPopulation + ", maxspeedSpaceStep: " + maxspeedSpaceStep + ", spaceX: " + space.getMaxX()
				+ ", spaceX: " + space.getMinX());
	}

	static public double y(double x) {
		return FindZeroInout.y(upgrade + x);
	}
}
