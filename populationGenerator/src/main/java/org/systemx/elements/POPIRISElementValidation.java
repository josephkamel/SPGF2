package org.systemx.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.systemx.populationGenerator.Iris;
import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.supportClasses.AgeGroup;
import org.systemx.populationGenerator.supportClasses.SocioProfessionalCategory;

public class POPIRISElementValidation {

	POPIRISElement MarginalData;
	Config config;
	
	private List<List<String>> element = new ArrayList<List<String>>();

	private List<String> iris = new ArrayList<String>();
	private List<String> dep = new ArrayList<String>();
	private List<String> nbrPopulation = new ArrayList<String>();
	private List<String> nbrMenages = new ArrayList<String>();
	private List<String> nbrHommes = new ArrayList<String>();
	private List<String> nbrFemmes = new ArrayList<String>();
	private List<List<String>> AgeGroupList = new ArrayList<List<String>>();
	private List<List<String>> SocioProfessionalCategoryList = new ArrayList<List<String>>();
	
	private List<String> MenagesError = new ArrayList<String>();
	private List<String> GenderError = new ArrayList<String>();
	private List<String> AgeError = new ArrayList<String>();
	private List<String> SPCError = new ArrayList<String>();
	
	private List<String> TotalErrors = new ArrayList<String>();

	Map<String, Integer> IrisToIndex = new HashMap<String, Integer>();
	
	List<AgeGroup> AgeGroupClassList = new ArrayList<AgeGroup>();
	List<SocioProfessionalCategory> SocioProfessionalCategoryClassList = new ArrayList<SocioProfessionalCategory>();

	public POPIRISElementValidation(POPIRISElement pOPIRISElement, Config config) {
		super();
		
		MarginalData = pOPIRISElement;
		
		this.config = config;
		
		for (int i = 0; i < pOPIRISElement.getIris().size(); i++) {
			IrisToIndex.put(pOPIRISElement.getIris().get(i), i);
		}
		
		iris.add("iris");
		element.add(iris);
		dep.add("dep");
		element.add(dep);
		nbrPopulation.add("nbrPopulation");
		element.add(nbrPopulation);
		
		if (pOPIRISElement.getNbrMenages().size() > 0) {
			nbrMenages.add("nbrMenages");
			element.add(nbrMenages);
		}
		if (pOPIRISElement.getNbrHommes().size() > 0) {
			nbrHommes.add("nbrHommes");
			element.add(nbrHommes);
		}
		if (pOPIRISElement.getNbrFemmes().size() > 0) {
			nbrFemmes.add("nbrFemmes");
			element.add(nbrFemmes);
		}

		if(pOPIRISElement.getAgeGroupRanges().size()>0){
			for (int i = 0; i < pOPIRISElement.getAgeGroupList().size(); i++) {
				List<String> ageGroup = new ArrayList<String>();
				ageGroup.add(pOPIRISElement.getAgeGroupRanges().get(i));
				AgeGroupList.add(ageGroup);
				element.add(ageGroup);
				
				List<String> range = Arrays.asList(pOPIRISElement.getAgeGroupRanges().get(i).split("_"));
				AgeGroup ageGroupClass = new AgeGroup();
				ageGroupClass.setAgeStart(Integer.parseInt(range.get(1)));
				ageGroupClass.setAgeEnd(Integer.parseInt(range.get(2)));
				AgeGroupClassList.add(ageGroupClass);
			}
			
		}
		
		if(pOPIRISElement.getSocioProfessionalCategoryList().size()>0){
			for (int i = 0; i < pOPIRISElement.getSocioProfessionalCategoryList().size(); i++) {
				List<String> SocioProfessionalCategory = new ArrayList<String>();
				SocioProfessionalCategory.add(pOPIRISElement.getSocioProfessionalCategoryRanges().get(i));
				SocioProfessionalCategoryList.add(SocioProfessionalCategory);
				element.add(SocioProfessionalCategory);
				
				
				List<String> range = Arrays.asList(pOPIRISElement.getSocioProfessionalCategoryRanges().get(i).split("_"));
				SocioProfessionalCategory socioProfessionalCategoryClass = new SocioProfessionalCategory();
				socioProfessionalCategoryClass.setCategory(Integer.parseInt(range.get(1)));
				SocioProfessionalCategoryClassList.add(socioProfessionalCategoryClass);
				
			}
		}
		
		if(config.isMarginalErrorMenages()){
			MenagesError.add("MenagesError");
			element.add(MenagesError);
		}
		if(config.isMarginalErrorHommesFemmes()){
			GenderError.add("GenderError");
			element.add(GenderError);
		}
		if(config.isMarginalErrorAgeGroup()){
			AgeError.add("AgeError");
			element.add(AgeError);
		}
		if(config.isMarginalErrorSocioProfessionalCategory()){
			SPCError.add("SCPError");
			element.add(SPCError);
		}
		TotalErrors.add("TotalErrors");
		element.add(TotalErrors);
	}

	public void println(int linesNumber) {
		if (linesNumber > getElement().get(0).size()) {
			linesNumber = getElement().get(0).size();
		}

		for (int j = 0; j < linesNumber; j++) {
			for (int i = 0; i < getElement().size(); i++) {
				System.out.print(getElement().get(i).get(j) + "	");
			}
			System.out.println();
		}
	}

	public void addIris(Iris iris) {

		int NbrMenage=0;
		int NbrHommes=0;
		int NbrFemmes=0;
		
		List<Integer> AgeGroups = new ArrayList<Integer>();		
		List<Integer> SocioProfessionalCategories = new ArrayList<Integer>();
		
		for (int j = 0; j < AgeGroupClassList.size(); j++) {
			AgeGroups.add(0);
		}
		for (int j = 0; j < SocioProfessionalCategoryClassList.size(); j++) {
			SocioProfessionalCategories.add(0);
		}
		
		for (int i = 0; i < iris.getPopulation().size(); i++) {

			if (i > 0) {
				if (!iris.getPopulation().get(i).getNUMMI().matches(iris.getPopulation().get(i - 1).getNUMMI())) {
					NbrMenage++;
				}
			}
			if (iris.getPopulation().get(i).getSEXE().matches("1")) {
				NbrHommes++;
			} else {
				NbrFemmes++;
			}

			int age = Integer.parseInt(iris.getPopulation().get(i).getAGED());
			for (int j = 0; j < AgeGroupClassList.size(); j++) {
				if(AgeGroupClassList.get(j).personInGroup(age)){
					AgeGroups.set(j, AgeGroups.get(j)+1);
				}
			}

			int csp= Integer.parseInt(iris.getPopulation().get(i).getCS1());
			for (int j = 0; j < SocioProfessionalCategoryClassList.size(); j++) {
				if(SocioProfessionalCategoryClassList.get(j).personInGroup(csp)){
					SocioProfessionalCategories.set(j, SocioProfessionalCategories.get(j)+1);
				}
			}
		}
		
		
		this.iris.add(iris.getIRIS());
		this.dep.add(iris.getDEP());
		this.nbrPopulation.add(String.valueOf( iris.getNbrPopulation()));
		this.nbrMenages.add(String.valueOf(NbrMenage));
		this.nbrHommes.add(String.valueOf(NbrHommes));
		this.nbrFemmes.add(String.valueOf(NbrFemmes));
		
		for (int i = 0; i < this.AgeGroupList.size(); i++) {
			this.AgeGroupList.get(i).add(String.valueOf(AgeGroups.get(i)));
		}
		
		for (int i = 0; i < this.SocioProfessionalCategoryList.size(); i++) {
			this.SocioProfessionalCategoryList.get(i).add(String.valueOf(SocioProfessionalCategories.get(i)));
		}
		
		
		if(config.isMarginalErrorMenages()){
			MenagesError.add(String.valueOf(iris.getMarginalErrorNbrMenages(iris.getPopulation())));
		}
		if(config.isMarginalErrorHommesFemmes()){
			GenderError.add(String.valueOf(iris.getMarginalErrorNbrHommesFemmes(iris.getPopulation())));
		}
		if(config.isMarginalErrorAgeGroup()){
			AgeError.add(String.valueOf(iris.getMarginalErrorAgeGroups(iris.getPopulation())));
		}
		if(config.isMarginalErrorSocioProfessionalCategory()){
			SPCError.add(String.valueOf(iris.getMarginalErrorSocioProfessionalCategory(iris.getPopulation())));
		}
		TotalErrors.add("");
	}
	
	public void finaliseErrors(String totalTime){
		int index = 1;
		double totaError=0;
		
		if(config.isMarginalErrorMenages()){
			double total = 0;
			for (int i = 1; i < MenagesError.size(); i++) {
				total = total + Double.parseDouble(MenagesError.get(i));
			}
			totaError = totaError + total;
			TotalErrors.add(index,"MenagesErrorTotal");
			index++;
			TotalErrors.add(index,String.valueOf(total));
			index++;
		}
		if(config.isMarginalErrorHommesFemmes()){
			double total = 0;
			for (int i = 1; i < GenderError.size(); i++) {
				total = total + Double.parseDouble(GenderError.get(i));
			}
			totaError = totaError + total;
			TotalErrors.add(index,"GenderErrorTotal");
			index++;
			TotalErrors.add(index,String.valueOf(total));
			index++;
		}
		if(config.isMarginalErrorAgeGroup()){
			double total = 0;
			for (int i = 1; i < AgeError.size(); i++) {
				total = total + Double.parseDouble(AgeError.get(i));
			}
			totaError = totaError + total;
			TotalErrors.add(index,"AgeErrorTotal");
			index++;
			TotalErrors.add(index,String.valueOf(total));
			index++;
		}
		if(config.isMarginalErrorSocioProfessionalCategory()){
			double total = 0;
			for (int i = 1; i < SPCError.size(); i++) {
				total = total + Double.parseDouble(SPCError.get(i));
			}
			totaError = totaError + total;
			TotalErrors.add(index,"SPCErrorTotal");
			index++;
			TotalErrors.add(index,String.valueOf(total));
			index++;
		}
		
		TotalErrors.add(index, "TotalError");
		index++;
		TotalErrors.add(index, String.valueOf(totaError));
		index++;
		TotalErrors.add(index, "TotalTime");
		index++;
		TotalErrors.add(index, totalTime);

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

}
