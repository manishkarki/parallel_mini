package edu.coursera.parallel;

import static edu.rice.pcdp.PCDP.async;
import static edu.rice.pcdp.PCDP.finish;

/**
 * @author mkarki
 */
public class ArraySum {

	private int seqArraySum(int[] a) {
		long startTime = System.nanoTime();
		int sum1 = 0;
		int sum2 = 0;

		//sum of lower half
		for(int i = 0; i< a.length/2; i++) {
			sum1 += a[i];
		}

		for(int i = a.length/2; i< a.length; i++) {
			sum2 += a[i];
		}
		//combine the sums
		int sum = sum1 + sum2;
		long computationalTime = System.nanoTime() - startTime;
		printResults("seqArraySum", computationalTime, sum);
		return sum;
	}

	private double parallelArraySum(int[] a) {
		long startTime = System.nanoTime();
		final Integer[] sum1 = {0};
		int sum2 = 0;

		//sum of lower half
		finish(() -> async(() -> {
			for(int i = 0; i< a.length/2; i++) {
				sum1[0] += (a[i]);
			}
		}));

		for(int i = a.length/2; i< a.length; i++) {
			sum2 += a[i];
		}
		//combine the sums
		double sum = sum1[0] + sum2;
		long computationalTime = System.nanoTime() - startTime;
		printResults("parallelArraySum", computationalTime, sum);
		return sum;
	}

	private void printResults(String arraySum, long computationalTime, double sum) {
		System.out.printf("%s took %8.3f ms and sum is %8.3f\n", arraySum, computationalTime / 1e6, sum);
	}

	public static void main(String[] args) {
		ArraySum arraySum = new ArraySum();
		int [] a = {1, 2, 3, 5, 6, 7, 8, 9, 20, 22, 26, 29, 31, 35, 37, 39, 100};
		arraySum.seqArraySum(a);
		arraySum.parallelArraySum(a);
	}
}
