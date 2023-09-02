package ga_training.aiEvolution;

import appMain.FindZeroInout;
import ga_training.GENE;
import genetoPhenotypic.BinnaryGentoPhenotypic;

public class Valuer {

	private double valueLevel = 0; // luong gia cang cao cang gan loi giai
	private double upgrade = 0; // reinforcement learning
	private long upgradeLen = 1;
	private int maxUpgradeLen = 1000;

	public double getValueLevel() {
		return valueLevel;
	}

	public void setValueLevel(double valueLevel) {
		this.valueLevel = valueLevel;
	}

	public double getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(double upgrade) {
		this.upgrade = upgrade;
		int len = getNumLen(upgrade);
		upgradeLen = (long) Math.pow(10, len);
		if (upgradeLen >= maxUpgradeLen) {
			upgradeLen = maxUpgradeLen;
		}
//		System.out.println("len: " + len + " upgradeLe: " + upgradeLen);
	}

	private int getNumLen(double number) {
		String value = String.valueOf(number);
		int count = 0;
		for (char c : value.toCharArray()) {
			if (c != '.') {
				count++;
			}
		}
		return count;
	}

	public void setUpgradeLen(long upgradeLen) {
		this.upgradeLen = upgradeLen;
	}

	public long getUpgradeLen() {
		return upgradeLen;
	}

	public final double getValue(GENE g) {
		double dNAres = BinnaryGentoPhenotypic.convertFromBinaryToNegativeDec(g.getGene());
		double x = FindZeroInout.getUpgradedx(upgrade, upgradeLen, dNAres);
		double y = FindZeroInout.y(x);
		return FindZeroInout.getUpgradedy(getValueLevel(), y);
	}

	public final double getpartnerValue(double x) {
		double y = FindZeroInout.y(x);
		return FindZeroInout.getUpgradedy(getValueLevel(), y);
	}

	@SuppressWarnings("unused")
	private final double valueBuilder(double y) {
		return Math.pow(1 / (0.01 + Math.abs(y)), /* change (remake) digital resource */getValueLevel());// luong gia
																											// cang cao
		// cang gan loi giai
	}
}
