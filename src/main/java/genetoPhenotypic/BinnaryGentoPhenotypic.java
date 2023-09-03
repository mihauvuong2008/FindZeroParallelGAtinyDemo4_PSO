package genetoPhenotypic;

import java.math.BigDecimal;

public class BinnaryGentoPhenotypic {
	public int miniDecimal_to_binary(double i) {
		return (int) (i + 0.5);
	}

	public int binary_to_Decimal(int[] array) {
		int decimal = 0;
		int size = array.length;
		for (int i = 0; i < size; i++) {
			decimal = decimal << 1 | array[i];
			// System.out.println("decimal: " + decimal);
		}
		return decimal;
	}

	public int binary_to_Decimal(double[] array) {
		int decimal = 0;
		int size = array.length;
		for (int i = 0; i < size; i++) {
			decimal = decimal << 1 | (array[i] >= 0.5 ? 1 : 0);
		}
		return decimal;
	}

	public static int printBinaryform(int number) {
		int remainder;

		if (number <= 1) {
			System.out.print(number);
			return number; // KICK OUT OF THE RECURSION
		}

		remainder = number % 2;
		printBinaryform(number >> 1);
		return remainder;
	}

	static public int[] toBin(int num) {
		int[] ret = new int[8];
		for (int i = 7, p = 0; i >= 0; i--, p++) {
			ret[i] = (num / 2 * p) % 2;
		}
		return ret;
	}

	public static double convertFromBinaryToDec(double[] output) {
		long j = 0;
		int len = output.length;
		int _len = len - 1;
		for (int i = 0; i < len; i++) {
			if (output[i] >= 0.5) {
				j += Math.pow(2, _len - i);
			}
		}
		return j / 1000000000;
	}

	public final static double convertFromBinaryToNegativeDec___BASIC(double[] output) {
		long /** more digital resource */
		j = 0;
		int len = output.length - 1;
		int _len = len - 1;
		for (int i = 0; i < len; i++) {
			if (output[i] >= 0.5) {
				j += Math.pow(2, _len - i);
			}
		}
		int negative = output[len] > 0.5 ? 1 : -1;
		return negative * ((double) j / 1000000000);
	}

	public final static double convertFromBinaryToNegativeDec(double[] output) {
		long /** more digital resource */
		j = 0;
		int len = output.length - 1;
		int _len = len - 1;
		for (int i = 0; i < len; i++) {
			if (output[i] >= 0.5) {
				j += Math.pow(2, _len - i);
			}
		}
		int negative = (output[len] > 0.5) ? 1 : -1;
		BigDecimal bdj = new BigDecimal(j);
		BigDecimal _rs = bdj.divide(new BigDecimal(1000000000));
		double rs = negative * _rs.doubleValue();
		return rs;
	}

	public static double[] DecToBinary(int no, int LenOfGen) {
		int i = 0, temp[] = new int[LenOfGen];
		double binary[];
		while (no > 0) {
			temp[i++] = (int) (no % 2);
			no /= 2;
		}
		binary = new double[i];
		int k = 0;
		for (int j = i - 1; j >= 0; j--) {
			binary[k++] = temp[j];
		}
		return binary;
	}

}