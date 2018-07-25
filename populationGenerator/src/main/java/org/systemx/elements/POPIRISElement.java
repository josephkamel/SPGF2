package org.systemx.elements;

import java.util.ArrayList;
import java.util.List;
import org.systemx.populationGenerator.supportClasses.ProgressBar;

public class POPIRISElement {

	private List<List<String>> element = new ArrayList<List<String>>();

	private List<String> iris = new ArrayList<String>();
	private List<String> dep = new ArrayList<String>();
	private List<String> nbrMenages = new ArrayList<String>();
	private List<String> nbrPopulation = new ArrayList<String>();
	private List<String> nbrHommes = new ArrayList<String>();
	private List<String> nbrFemmes = new ArrayList<String>();
	
	private List<List<String>> AgeGroupList= new ArrayList<List<String>>();
	private List<List<String>> SocioProfessionalCategoryList= new ArrayList<List<String>>();
	
	private List<String> AgeGroupRanges = new ArrayList<String>();
	private List<String> SocioProfessionalCategoryRanges = new ArrayList<String>();

	
	public POPIRISElement(List<List<String>> element) {
		super();
		
		this.element = element;
		
		for (int i = 0; i < element.size(); i++) {
			String title = element.get(i).get(0);
			
			switch (title) {
			case "iris":
				iris = element.get(i);
				break;
			case "dep":
				dep = element.get(i);
				break;
			case "nbrMenages":
				nbrMenages = element.get(i);
				break;
			case "nbrPopulation":
				nbrPopulation = element.get(i);
				break;
			case "nbrHommes":
				nbrHommes = element.get(i);
				break;
			case "nbrFemmes":
				nbrFemmes = element.get(i);
				break;
			default:
				if(title.contains("AgeGroup")){
					AgeGroupList.add(element.get(i));
					AgeGroupRanges.add(title);
				}else if(title.contains("SocioProfessionalCategory")){
					SocioProfessionalCategoryList.add(element.get(i));
					SocioProfessionalCategoryRanges.add(title);
				}
				break;
			}	
		}
	}

	public void println(int linesNumber) {
		for (int i = 0; i < getElement().size(); i++) {
			System.out.println(getElement().get(i).get(0) + ":" + getElement().get(i).size());
		}

		if(linesNumber>getElement().get(0).size()){
			linesNumber = getElement().get(0).size();
		}
		
		for (int j = 0; j < linesNumber; j++) {
			for (int i = 0; i < getElement().size(); i++) {
				System.out.print(getElement().get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	public void cleanList() {
		int removed = 0;
		System.out.println("Cleaning MarginalData "+getElement().get(0).size()+" List:");
		ProgressBar pb = new ProgressBar(getElement().get(0).size()-1, "Cleaning MarginalData "+getElement().get(0).size()+" List:");
		for (int i = getElement().get(0).size()-1; i >= 0; i--) {
			for (int j = 1; j < getElement().size(); j++) {
				if(!isNumeric(getElement().get(j).get(i))){
					removeElement(i);
					removed ++;
					break;
				}
			}
			pb.update(i);
		}
		System.out.println();
		System.out.println("Removed MarginalData elements:" + removed);
		System.out.println();
	}
	
	public List<String> getIris() {
		return iris;
	}

	public void setIris(List<String> iris) {
		this.iris = iris;
	}

	public List<String> getDep() {
		return dep;
	}

	public void setDep(List<String> dep) {
		this.dep = dep;
	}

	public List<List<String>> getAgeGroupList() {
		return AgeGroupList;
	}

	public void setAgeGroupList(List<List<String>> ageGroupList) {
		AgeGroupList = ageGroupList;
	}

	public List<List<String>> getSocioProfessionalCategoryList() {
		return SocioProfessionalCategoryList;
	}

	public void setSocioProfessionalCategoryList(List<List<String>> socioProfessionalCategoryList) {
		SocioProfessionalCategoryList = socioProfessionalCategoryList;
	}

	public List<String> getSocioProfessionalCategoryRanges() {
		return SocioProfessionalCategoryRanges;
	}

	public List<String> getAgeGroupRanges() {
		return AgeGroupRanges;
	}

	public void setSocioProfessionalCategoryRanges(List<String> socioProfessionalCategoryRanges) {
		SocioProfessionalCategoryRanges = socioProfessionalCategoryRanges;
	}

	public void setAgeGroupRanges(List<String> ageGroupRanges) {
		AgeGroupRanges = ageGroupRanges;
	}

	public void removeNotInDep(List<String> dep) {
		int removed = 0;
		System.out.println("Removing MarginalData "+getElement().get(0).size()+" not in dep started:");
		ProgressBar pb = new ProgressBar(getElement().get(0).size()-1, "Removing MarginalData "+getElement().get(0).size()+" not in dep started:");
		for (int i = getElement().get(0).size() - 1; i >= 0; i--) {
				if(!dep.contains(this.dep.get(i))){
					removeElement(i);
					removed ++;
				}
				pb.update(i);
		}
		System.out.println();
		System.out.println("Removed MarginalData elements:"+ removed);
		System.out.println();
	}
	
	public List<List<String>> getElement() {
		return element;
	}

	public void setElement(List<List<String>> element) {
		this.element = element;
	}

	public void removeElement(int index) {
		if (getElement().get(0).size() > index) {
			for (int i = 0; i < getElement().size(); i++) {
				getElement().get(i).remove(index);
			}
		}
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	
	public List<String> getNbrMenages() {
		return nbrMenages;
	}

	public void setNbrMenages(List<String> nbrMenages) {
		this.nbrMenages = nbrMenages;
	}

	public List<String> getNbrHommes() {
		return nbrHommes;
	}

	public void setNbrHommes(List<String> nbrHommes) {
		this.nbrHommes = nbrHommes;
	}

	public List<String> getNbrFemmes() {
		return nbrFemmes;
	}

	public void setNbrFemmes(List<String> nbrFemmes) {
		this.nbrFemmes = nbrFemmes;
	}

	public List<String> getNbrPopulation() {
		return nbrPopulation;
	}

	public void setNbrPopulation(List<String> nbrPopulation) {
		this.nbrPopulation = nbrPopulation;
	}	

}
