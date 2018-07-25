package org.systemx.populationGenerator.supportClasses;

public class AgeGroup {
	
	int ageStart;
	int ageEnd;
	int popNbr;
	
	public int getAgeStart() {
		return ageStart;
	}
	public void setAgeStart(int ageStart) {
		this.ageStart = ageStart;
	}
	public int getAgeEnd() {
		return ageEnd;
	}
	public void setAgeEnd(int ageEnd) {
		this.ageEnd = ageEnd;
	}
	public int getPopNbr() {
		return popNbr;
	}
	public void setPopNbr(int popNbr) {
		this.popNbr = popNbr;
	}
	
	public boolean personInGroup(int age){
		if(age>=ageStart && age <= ageEnd){
			return true;
		}else{
			return false;
		}
	}
	
	public double getMarginalError(int popNbrCalc){
		
		if(popNbr == 0){
			return Math.abs(((double)popNbr-(double)popNbrCalc));
		}
		
		return Math.abs(((double)popNbr-(double)popNbrCalc)/(double)popNbr);
	}
	
	@Override
	public String toString() {
		return "AgeGroup [ageStart=" + ageStart + ", ageEnd=" + ageEnd + ", popNbr=" + popNbr + "]";
	}
	

}
