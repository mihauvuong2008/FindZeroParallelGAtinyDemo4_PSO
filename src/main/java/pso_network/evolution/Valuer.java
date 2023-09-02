package pso_network.evolution;

import java.util.ArrayList;

import pso_network.Individual;
import pso_network.Position;
import pso_network.training.PSO_InOut;

public class Valuer {

	public void populationValue(ArrayList<Individual> population) {
		for (Individual ele : population) {
			double _value = value(ele.getPosition());
//			System.out.println("_value: " + _value);
			ele.setValue(_value);
		}
	}

	public double _x(double x, double y, double z) {
//		System.out.println("x: " + x + ", y: " + y + ", z: " + z);
		return x + y + z;
	}

	private double y(double x) {
//		double y = 2d * x * x - 10d * x + 12.5d;
		double y = 2d * x * x - 8d * x + 8d;
//		double y = (Math.pow(x, 2) + Math.pow(Math.E, Math.pow(x, 2 * x)) + x * Math.pow(4, x)) / (2 * x + 1)
//				+ (2 * x + Math.pow(3, 2 * x) - 1) / (5 * Math.pow(x, 3) + Math.log10(x) - 1);
//		System.out.println("y: " + y);

		if (Double.isNaN(y))
			y = Double.MAX_VALUE;
		y = PSO_InOut.y(x);
		return y;
	}

	public double value(Position position) {
		double x = _x(position.getX(), position.getY(), position.getZ());
//		System.out.println(y);
		double value = 1d / (0.01 + Math.abs(y(x)));
//		System.out.println("fc_value: " + valu + ", y(x): " + y(x));
		return value;
	}

}
