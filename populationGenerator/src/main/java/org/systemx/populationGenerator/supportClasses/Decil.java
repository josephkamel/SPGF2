package org.systemx.populationGenerator.supportClasses;

import java.util.Random;

public class Decil {

	int revenueStart;
	int revenueEnd;

	public int getRevenueStart() {
		return revenueStart;
	}

	public void setRevenueStart(int revenueStart) {
		this.revenueStart = revenueStart;
	}

	public int getRevenueEnd() {
		return revenueEnd;
	}

	public void setRevenueEnd(int revenueEnd) {
		this.revenueEnd = revenueEnd;
	}

	public int getRandomRevenue(){
		return randInt(revenueStart, revenueEnd);
	}
	
	private int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
}
