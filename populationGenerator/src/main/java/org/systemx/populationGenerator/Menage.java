package org.systemx.populationGenerator;

public class Menage {
	
	private int index;
	private Double ipondi;
	
	public Menage(int index, Double ipondi) {
		super();
		this.index = index;
		this.ipondi = ipondi;
		
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Double getIpondi() {
		return ipondi;
	}
	public void setIpondi(Double ipondi) {
		this.ipondi = ipondi;
	}
	@Override
	public String toString() {
		return "Menage [index=" + index + ", ipondi=" + ipondi + "]";
	}
}
