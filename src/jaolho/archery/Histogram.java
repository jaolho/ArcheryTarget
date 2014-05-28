package jaolho.archery;

import java.util.ArrayList;

public class Histogram {

	private double rangeMin, rangeMax;
	private int binCount;
	private double binWidth;
	private ArrayList<Double> data = new ArrayList<Double>();
	private int[] bins;
	
	/**
	 * @param rangeMin Inclusive
	 * @param rangeMax Exclusive
	 * @param binCount
	 */
	public Histogram(double rangeMin, double rangeMax, int binCount) {
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		this.binCount = binCount;
		this.binWidth = (rangeMax - rangeMin) / binCount;
	}
	
	public void updateBins() {
		bins = new int[binCount];
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).doubleValue() >= rangeMin || data.get(i).doubleValue() < rangeMax) {
				int binIndex = (int) Math.floor((data.get(i).doubleValue() - rangeMin) / binWidth);
				bins[binIndex]++;
			}
		}
	}

	public void addData(double[] data) {
		for (int i = 0; i < data.length; i++) {
			addData(data[i]);
		}
	}
	
	public void addData(double d){
		this.data.add(d);
	}
	
	public String toString() {
		String result = "";
		int sum = 0;
		for (int i = 0; i < bins.length; i++) {
			sum += bins[i];
		}
		for (int i = 0; i < bins.length; i++) {
			result += i + ": " + String.format("%.2f", bins[i] / (double)sum * 100) + "%" + (i < bins.length - 1 ? " | " : "");
		}
		return result;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
