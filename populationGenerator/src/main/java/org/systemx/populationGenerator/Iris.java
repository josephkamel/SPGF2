package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.supportClasses.AgeGroup;
import org.systemx.populationGenerator.supportClasses.Decil;
import org.systemx.populationGenerator.supportClasses.SocioProfessionalCategory;

public class Iris {

	private Config config; 
	
	private String IRIS;
	private String COM;
	private String DEP;
	private List<Decil> DECILS;

	private int nbrPopulation = 0;
	private int nbrMenages;
	private int nbrHommes;
	private int nbrFemmes;
	private List<AgeGroup> ageGroups = new ArrayList<AgeGroup>();
	private List<SocioProfessionalCategory> socioProfessionalCategory = new ArrayList<SocioProfessionalCategory>();

	List<String> element = new ArrayList<String>();
	private List<Person> population = new ArrayList<Person>();
	private List<Menage> menages = new ArrayList<Menage>();

	public Iris() {
		super();
	}

	void parseIris(String Iris, Config config) {
		this.config = config;
		IRIS = Iris;
		COM = IRIS.substring(0, 5);
		DEP = IRIS.substring(0, 2);

	}

	void parseRevenue(List<Integer> Decils) {
		DECILS = new ArrayList<Decil>();
		for (int i = 0; i <= Decils.size(); i++) {
			Decil dec = new Decil();
			if (i == 0) {
				dec.setRevenueStart(0);
				dec.setRevenueEnd(Decils.get(i));
			} else if (i == Decils.size()) {
				dec.setRevenueStart(Decils.get(i - 1));

				dec.setRevenueEnd(Decils.get(i - 1) * 2);
			} else {
				dec.setRevenueStart(Decils.get(i - 1));
				dec.setRevenueEnd(Decils.get(i));
			}
			DECILS.add(dec);
		}
	}

	public List<Integer> listOfDec() {
		List<Integer> DecilsList = new ArrayList<Integer>();

		for (int i = 0; i < DECILS.size() - 1; i++) {
			DecilsList.add(DECILS.get(i).getRevenueEnd());
		}

		return DecilsList;
	}

	public int getNbrPopulation() {
		return nbrPopulation;
	}

	public void setNbrPopulation(int nbrPopulation) {
		this.nbrPopulation = nbrPopulation;
	}

	public String getIRIS() {
		return IRIS;
	}

	public List<Person> getPopulation() {
		return population;
	}

	public void setPopulation(List<Person> population) {
		this.population = population;
	}

	public String getCOM() {
		return COM;
	}

	public void setCOM(String cOM) {
		COM = cOM;
	}

	public String getDEP() {
		return DEP;
	}

	public void setDEP(String dEP) {
		DEP = dEP;
	}

	// public void filterByNBPI(){
	// for (int i = 0; i < population.size(); i++) {
	// if(!isNumeric(population.get(i).getNBPI())){
	// population.remove(i);
	// }
	// }
	// }

	public void sortPopulationByNBPI() {
		Collections.sort(getPopulation(), new Comparator<Person>() {
			@Override
			public int compare(Person p1, Person p2) {
				double p1piece = Double.parseDouble(p1.getNBPI());
				double p2piece = Double.parseDouble(p2.getNBPI());
				if (p1piece > p2piece)
					return 1;
				if (p1piece < p2piece)
					return -1;
				return 0;
			}
		});
	}

	public void sortPopulationByNUMMI() {
		Collections.sort(getPopulation(), new Comparator<Person>() {
			@Override
			public int compare(Person p1, Person p2) {
				double p1menage = Double.parseDouble(p1.getNUMMI());
				double p2menage = Double.parseDouble(p2.getNUMMI());
				if (p1menage > p2menage)
					return 1;
				if (p1menage < p2menage)
					return -1;
				return 0;
			}
		});
	}

	public void projectPopulation() {
		int initialPopulation = population.size();
		int index = 0;

		int lastMenage = Integer.parseInt(population.get(population.size() - 1).getNUMMI());

		while (population.size() < nbrPopulation) {
			int workingMenage = Integer.parseInt(population.get(index).getNUMMI());
			while (population.get(index).getNUMMI().matches(String.valueOf(workingMenage))) {
				Person newPerson = new Person(population.get(index));
				newPerson.setNUMMI(String.valueOf(lastMenage));
				population.add(newPerson);
				index++;
			}
			lastMenage++;
		}
		// System.out.println("IRIS:"+ IRIS + " initial:" +initialPopulation + "
		// currentPopulation:"+ population.size());
	}

	public void projectPopulationWithIPONDI() {
		int index = 0;

		setMenagesList();

		List<Person> populationTemp = new ArrayList<Person>();
		populationTemp.addAll(population);

		int newMenage = 1 + Integer.parseInt(population.get(population.size() - 1).getNUMMI());

		while (populationTemp.size() < nbrPopulation) {
			index = getSampleMenageIndex();

			List<Person> menage = getMenage(index);
			for (int i = 0; i < menage.size(); i++) {
				Person newPerson = new Person(menage.get(i));
				newPerson.setNUMMI(String.valueOf(newMenage));
				populationTemp.add(newPerson);
			}
			newMenage++;
		}

		population.clear();
		population.addAll(populationTemp);
	}

	public void projectPopulationWithIPONDIWithCO(int finesse) {
		int index = 0;
		setMenagesList();

		boolean valid = false;
		int counter = 0;
		double leastError = Double.MAX_VALUE;

		List<Person> populationLeastError = new ArrayList<Person>();

		while (!valid) {
			List<Person> populationTemp = new ArrayList<Person>();
			populationTemp.addAll(population);

			int newMenage = 1 + Integer.parseInt(population.get(population.size() - 1).getNUMMI());

			while (populationTemp.size() < nbrPopulation) {
				index = getSampleMenageIndex();

				List<Person> menage = getMenage(index);
				for (int i = 0; i < menage.size(); i++) {
					Person newPerson = new Person(menage.get(i));
					newPerson.setNUMMI(String.valueOf(newMenage));
					populationTemp.add(newPerson);
				}
				newMenage++;
			}
			double currentError = 0;

			currentError = currentError + getMarginalErrorAgeGroups(populationTemp);

			if (currentError < leastError) {
				leastError = currentError;
				populationLeastError.clear();
				populationLeastError.addAll(populationTemp);

			}
			counter++;
			if (leastError == 0) {
				valid = true;
			}

			if (counter > finesse) {
				valid = true;
			}
		}

		population.clear();
		population.addAll(populationLeastError);
	}

	public double getMarginalError(List<Person> populationTemp) {
		double error = 0;
		if(config.isMarginalErrorMenages()){
			error = error + getMarginalErrorNbrMenages(populationTemp);
		}
		if(config.isMarginalErrorHommesFemmes()){
			error = error + getMarginalErrorNbrHommesFemmes(populationTemp);	
		}
		if (config.isMarginalErrorAgeGroup()) {
			error = error + getMarginalErrorAgeGroups(populationTemp);
		}
		if (config.isMarginalErrorSocioProfessionalCategory()) {
			error = error + getMarginalErrorSocioProfessionalCategory(populationTemp);
		}		
		return error;
	}

	public double getMarginalErrorSocioProfessionalCategory(List<Person> populationTemp) {
		Map<Integer, Integer> SPCToNumber = new HashMap<Integer, Integer>();
		for (int i = 0; i < socioProfessionalCategory.size(); i++) {
			SPCToNumber.put(i, 0);
		}

		for (int i = 0; i < populationTemp.size(); i++) {
			int CS = Integer.parseInt(populationTemp.get(i).getCS1());
			

			for (int j = 0; j < socioProfessionalCategory.size(); j++) {
				if (socioProfessionalCategory.get(j).personInGroup(CS)) {
					SPCToNumber.put(j, SPCToNumber.get(j) + 1);
				}
			}
		}

		double error = 0;
		for (int i = 0; i < socioProfessionalCategory.size(); i++) {
			error = error + socioProfessionalCategory.get(i).getMarginalError(SPCToNumber.get(i));
		}
		return error/socioProfessionalCategory.size();
	}

	public double getMarginalErrorAgeGroups(List<Person> populationTemp) {
		Map<Integer, Integer> groupToNumber = new HashMap<Integer, Integer>();
		for (int i = 0; i < ageGroups.size(); i++) {
			groupToNumber.put(i, 0);
		}

		for (int i = 0; i < populationTemp.size(); i++) {
			int age = Integer.parseInt(populationTemp.get(i).getAGED());

			for (int j = 0; j < ageGroups.size(); j++) {
				if (ageGroups.get(j).personInGroup(age)) {
					groupToNumber.put(j, groupToNumber.get(j) + 1);
				}
			}
		}

		double error = 0;
		for (int i = 0; i < ageGroups.size(); i++) {
			error = error + ageGroups.get(i).getMarginalError(groupToNumber.get(i));
		}
		return error/ageGroups.size();
	}

	public double getMarginalErrorNbrMenages(List<Person> populationTemp) {
		int nbrMenages = 0;
		int menageOld = -1;
		int menageNew = -1;
		for (int i = 0; i < populationTemp.size(); i++) {
			menageNew = Integer.parseInt(populationTemp.get(i).getNUMMI());
			if (menageNew != menageOld) {
				nbrMenages++;
				menageOld = menageNew;
			}
		}
		double error = 0;
		
		if (this.nbrMenages == 0) {
			error = Math.abs(((double) this.nbrMenages - (double) nbrMenages));
		}else{
			error = Math.abs(((double) this.nbrMenages - (double) nbrMenages) / (double) this.nbrMenages);
		}
		
		return error;
	}

	public double getMarginalErrorNbrHommesFemmes(List<Person> populationTemp) {
		int nbrHommes = 0;
		int nbrFemmes = 0;
		for (int i = 0; i < populationTemp.size(); i++) {
			int Sexe = Integer.parseInt(populationTemp.get(i).getSEXE());
			if (Sexe == 1) {
				nbrHommes++;
			} else {
				nbrFemmes++;
			}
		}
		
		double errorH = 0;
		double errorF = 0;

		if (this.nbrHommes == 0) {
			errorH = Math.abs(((double) this.nbrHommes - (double) nbrHommes));
		}else{
			errorH = Math.abs(((double) this.nbrHommes - (double) nbrHommes) / (double) this.nbrHommes);
		}
		
		if (this.nbrFemmes == 0) {
			errorF = Math.abs(((double) this.nbrFemmes - (double) nbrFemmes));
		}else{
			errorF = Math.abs(((double) this.nbrFemmes - (double) nbrFemmes) / (double) this.nbrFemmes);
		}
		
		return (errorH + errorF)/2;
	}

	private List<Person> getMenage(int index) {
		List<Person> menage = new ArrayList<Person>();
		int workingMenage = Integer.parseInt(population.get(index).getNUMMI());

		while ((population.size() > index) && population.get(index).getNUMMI().matches(String.valueOf(workingMenage))) {
			menage.add(population.get(index));
			index++;
		}

		return menage;
	}

	public void setMenagesList() {
		int initialPopulation = population.size();
		String menage = "";

		menages = new ArrayList<Menage>();

		for (int i = 0; i < initialPopulation; i++) {
			if (!menage.matches(population.get(i).getNUMMI())) {
				menage = population.get(i).getNUMMI();
				menages.add(new Menage(i, Double.parseDouble(population.get(i).getIPONDI())));
			}
		}

		Collections.sort(menages, new Comparator<Menage>() {
			public int compare(Menage m1, Menage m2) {
				if (m1.getIpondi() > m2.getIpondi())
					return -1;
				if (m1.getIpondi() < m2.getIpondi())
					return 1;
				return 0;
			}
		});

	}

	public Integer getSampleMenageIndex() {
		Random random = new Random();
		double totalP = 0;

		for (Menage menage : menages) {
			totalP = totalP + menage.getIpondi();
		}

		double p = random.nextDouble() * totalP;
		double cumulativeProbability = 0.0;

		for (int i = 0; i < menages.size(); i++) {
			cumulativeProbability += menages.get(i).getIpondi();
			if (p <= cumulativeProbability) {
				return menages.get(i).getIndex();
			}
		}
		return menages.get(random.nextInt(menages.size() - 1)).getIndex();
	}

	public void assignRevenue() {
		double totalPopulation = population.size();
		for (int i = 0; i < population.size(); i++) {
			double decilClass = (double) i / totalPopulation;
			int revenue = 0;
			for (double j = 0; j < 1; j = j + 0.1) {
				double lowerBound = j;
				double upperBound = j + 0.1;
				if (isBetween(decilClass, lowerBound, upperBound)) {
					int decil = (int) (upperBound * 10);
					revenue = DECILS.get(decil).getRandomRevenue();
				}
			}
			population.get(i).setREVENUE(revenue);
		}
	}

	private boolean isBetween(double x, double lower, double upper) {
		return lower <= x && x <= upper;
	}

	public List<Decil> getDECILS() {
		return DECILS;
	}

	public void setDECILS(List<Decil> dECILS) {
		DECILS = dECILS;
	}

	public List<AgeGroup> getAgeGroups() {
		return ageGroups;
	}

	public void setAgeGroups(List<AgeGroup> ageGroups) {
		this.ageGroups = ageGroups;
	}

	public int getNbrMenages() {
		return nbrMenages;
	}

	public void setNbrMenages(int nbrMenages) {
		this.nbrMenages = nbrMenages;
	}

	public int getNbrHommes() {
		return nbrHommes;
	}

	public void setNbrHommes(int nbrHommes) {
		this.nbrHommes = nbrHommes;
	}

	public int getNbrFemmes() {
		return nbrFemmes;
	}

	public void setNbrFemmes(int nbrFemmes) {
		this.nbrFemmes = nbrFemmes;
	}

	public List<SocioProfessionalCategory> getSocioProfessionalCategory() {
		return socioProfessionalCategory;
	}

	public void setSocioProfessionalCategory(List<SocioProfessionalCategory> socioProfessionalCategory) {
		this.socioProfessionalCategory = socioProfessionalCategory;
	}

}
