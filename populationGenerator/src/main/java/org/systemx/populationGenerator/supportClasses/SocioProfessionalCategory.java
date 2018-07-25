package org.systemx.populationGenerator.supportClasses;

public class SocioProfessionalCategory {
	
	int category;
	int popNbr;
		
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPopNbr() {
		return popNbr;
	}

	public void setPopNbr(int popNbr) {
		this.popNbr = popNbr;
	}

	public boolean personInGroup(int category){
		if(this.category == category){
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
		return "SocioProfessionalCategory [category=" + category + ", popNbr=" + popNbr + "]";
	}

}
