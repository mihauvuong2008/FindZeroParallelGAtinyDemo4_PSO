package ga_training.aiEvolution;

import java.util.Random;

public class PlanOfchilds {
	int numOfChild = 2;
	double nChildPlan = 0.5;
	float gama = 0.5f;
	Random ran;
	int plcls = 0;

	public PlanOfchilds() {
		// TODO Auto-generated constructor stub
		ran = new Random();
	}

	public int getNumofchildbyPlan() {
		double negative = (ran.nextDouble() - ran.nextDouble());
		double plan = gama + (negative > 0 ? (negative + nChildPlan) : (negative - nChildPlan));
		plcls = (int) (numOfChild + plan);
		return plcls;
	}

	public void setupPlandescr(float gama, double nChildPlan, int numOfChild) {
		this.gama = gama;
		this.nChildPlan = nChildPlan;
		this.numOfChild = numOfChild;
	}

	public int getPlcls() {
		return plcls;
	}

	public int getNumOfChild() {
		return numOfChild;
	}

	public void setNumOfChild(int numOfChild) {
		this.numOfChild = numOfChild;
	}

	public double getNChildPlan() {
		return nChildPlan;
	}

	public void setNChildPlan(double nChildPlan) {
		this.nChildPlan = nChildPlan;
	}

	public float getGama() {
		return gama;
	}

	public void setGama(float gama) {
		this.gama = gama;
	}

}
