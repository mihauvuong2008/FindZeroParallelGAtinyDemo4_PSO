package appMain;

public final class FindZeroInout {
	private static final double MAX = Double.MAX_VALUE;

	public FindZeroInout() {
		// TODO Auto-generated constructor stub
	}

	public final static double y(double x) {
//		return (Math.pow(x, 2) + Math.pow(Math.E, Math.pow(x, 2 * x)) + x * Math.pow(4, x)) / (2 * x + 1)
//				+ (2 * x + Math.pow(3, 2 * x) - 1) / (5 * Math.pow(x, 3) + Math.log10(x) - 1);

//		return 2 * Math.pow(x, 5) + 3 * Math.pow(x, 4) - 8 * Math.pow(x, 3) + 2 * Math.pow(x, 2) + 1;

		return Math.sin(Math.sin(x) * x + Math.sin(x + 1));
	}

	public final static double getUpgradedx(double upgrade, long upgradeLen, double dNAres) {
//		if (upgradeLen == 0) {
//			return dNAres;
//		}
		return upgrade + (double) dNAres / upgradeLen;
	}

	public final static double getUpgradedy(double valueLevel, double y) {
		if (Double.isNaN(y)) {
			y = MAX;
		}
//		double rs = 1 / (0.01 + (Math.abs(y) * Math.pow(1.1, valueLevel)));
		double rs = 1 / (0.01 + (Math.pow(Math.abs(y), valueLevel)));
//		System.out.println("rs: " + rs + " y " + y);
		return rs;// luong gia cang cao cang gan loi giai

	}

//	double y = Math.pow(Math.abs(-1 * Math.pow(x, 4) + 6 * Math.pow(x, 3) - 3 * Math.pow(x, 2) + 3 * x + 1),
//			getValueLevel());

}
