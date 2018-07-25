package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Canton {

	private List<String> insee;
	private String code_cant;
	private String code_dept;
	private String code_reg;

	private List<Person> population = new ArrayList<Person>();

	private List<Person> populationAll = new ArrayList<Person>();

	private List<Menage> menages = new ArrayList<Menage>();

	private List<Menage> menagesAll = new ArrayList<Menage>();

	public Canton() {
		super();
	}
	

	public Canton(Canton canton) {
		super();
		this.insee = canton.getInsee();
		this.code_cant = canton.getCode_cant();
		this.code_dept = canton.getCode_dept();
		this.code_reg = canton.getCode_reg();
		this.population = new ArrayList<Person>(canton.getPopulation());
		this.populationAll =  new ArrayList<Person>(canton.getPopulationAll());
		this.menages  = new ArrayList<Menage>(canton.getMenages());
		this.menagesAll  = new ArrayList<Menage>(canton.getMenagesAll());
	}



	public List<String> getInsee() {
		return insee;
	}

	public void setInsee(List<String> insee) {
		this.insee = insee;
	}

	public String getCode_cant() {
		return code_cant;
	}

	public void setCode_cant(String code_cant) {
		this.code_cant = code_cant;
	}

	public String getCode_dept() {
		return code_dept;
	}

	public void setCode_dept(String code_dept) {
		this.code_dept = code_dept;
	}

	public String getCode_reg() {
		return code_reg;
	}

	public void setCode_reg(String code_reg) {
		this.code_reg = code_reg;
	}

	public List<Person> getPopulation() {
		return population;
	}

	public void setPopulation(List<Person> population) {
		this.population = population;
	}

	public List<Menage> getMenages() {
		return menages;
	}

	public void setMenages(List<Menage> menages) {
		this.menages = menages;
	}

	public List<Person> getPopulationAll() {
		return populationAll;
	}

	public void setPopulationAll(List<Person> populationAll) {
		this.populationAll = populationAll;
	}
	
	public List<Menage> getMenagesAll() {
		return menagesAll;
	}


	public void setMenagesAll(List<Menage> menagesAll) {
		this.menagesAll = menagesAll;
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
		Collections.sort(getPopulationAll(), new Comparator<Person>() {
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

	public List<Person> getProjectedPopulation(int totalPopulation, String iris) {
		int index = 0;

		setMenagesList();

		List<Person> populationTemp = new ArrayList<Person>();

		int newMenage = 1 + Integer.parseInt(population.get(population.size() - 1).getNUMMI());

		while (populationTemp.size() < totalPopulation) {
			index = getSampleMenageIndex();

			List<Person> menage = getMenage(index);
			for (int i = 0; i < menage.size(); i++) {
				Person newPerson = new Person(menage.get(i));
				newPerson.setNUMMI(String.valueOf(newMenage));
				newPerson.setIRIS(iris);
				newPerson.setCANTVILLE(iris.substring(0, 2) + code_cant);
				newPerson.setDEPT(iris.substring(0, 2));
				populationTemp.add(newPerson);
			}
			newMenage++;
		}

		return populationTemp;
	}

	public List<Person> getProjectedPopulationWithCO(int totalPopulation, Iris iris, int finesse) {
		int index = 0;

		setMenagesList();

		boolean valid = false;
		int counter = 0;
		double leastError = Double.MAX_VALUE;

		List<Person> populationLeastError = new ArrayList<Person>();

		while (!valid) {
			List<Person> populationTemp = new ArrayList<Person>();
			int newMenage = 1 + Integer.parseInt(population.get(population.size() - 1).getNUMMI());
			while (populationTemp.size() < totalPopulation) {
				index = getSampleMenageIndex();

				List<Person> menage = getMenage(index);
				for (int i = 0; i < menage.size(); i++) {
					Person newPerson = new Person(menage.get(i));
					newPerson.setNUMMI(String.valueOf(newMenage));
					newPerson.setIRIS(iris.getIRIS());
					newPerson.setCANTVILLE(iris.getIRIS().substring(0, 2) + code_cant);
					newPerson.setDEPT(iris.getIRIS().substring(0, 2));
					populationTemp.add(newPerson);
				}
				newMenage++;
			}

			double currentError = iris.getMarginalError(populationTemp);

			if (currentError < leastError) {
				leastError = currentError;
				populationLeastError.clear();
				populationLeastError.addAll(populationTemp);
			}

			counter++;
			if (counter > finesse) {
				valid = true;
			}
		}

		return populationLeastError;
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

		// System.out.println("===========================");
		// for (int i = 0; i < menages.size(); i++) {
		// System.out.println(menages.get(i).toString());
		// }

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
				// System.out.println("----------------------------------");
				// System.out.println("i:" + i +"/" + menages.size());
				// System.out.println(menages.get(i).getIpondi()+"/"
				// +cumulativeProbability+"/" +totalP);
				return menages.get(i).getIndex();
			}
		}
		return menages.get(random.nextInt(menages.size() - 1)).getIndex();
	}

	public List<Person> getProjectedPopulationAll(int totalPopulation, String iris) {
		int index = 0;
		List<Person> populationStore = new ArrayList<Person>();
		populationStore.addAll(population);
		setMenagesAllList();

		List<Person> populationTemp = new ArrayList<Person>();
		// populationTemp.addAll(population);
		int newMenage = 1 + Integer.parseInt(populationAll.get(populationAll.size() - 1).getNUMMI());

		while (populationTemp.size() < totalPopulation) {
			index = getSampleMenageAllIndex();

			List<Person> menage = getMenageAll(index);
			for (int i = 0; i < menage.size(); i++) {
				Person newPerson = new Person(menage.get(i));
				newPerson.setNUMMI(String.valueOf(newMenage));
				newPerson.setIRIS(iris);
				newPerson.setCANTVILLE(iris.substring(0, 2) + code_cant);
				newPerson.setDEPT(iris.substring(0, 2));
				populationTemp.add(newPerson);
			}
			newMenage++;
		}

		return populationTemp;
	}

	public List<Person> getProjectedPopulationAllWithCO(int totalPopulation, Iris iris,int finesse) {
		int index = 0;
		List<Person> populationStore = new ArrayList<Person>();
		populationStore.addAll(population);
		setMenagesAllList();
		boolean valid = false;
		int counter = 0;
		double leastError = Double.MAX_VALUE;

		List<Person> populationLeastError = new ArrayList<Person>();

		while (!valid) {

			List<Person> populationTemp = new ArrayList<Person>();
			int newMenage = 1 + Integer.parseInt(populationAll.get(populationAll.size() - 1).getNUMMI());

			while (populationTemp.size() < totalPopulation) {
				index = getSampleMenageAllIndex();

				List<Person> menage = getMenageAll(index);
				for (int i = 0; i < menage.size(); i++) {
					Person newPerson = new Person(menage.get(i));
					newPerson.setNUMMI(String.valueOf(newMenage));
					newPerson.setIRIS(iris.getIRIS());
					newPerson.setCANTVILLE(iris.getIRIS().substring(0, 2) + code_cant);
					newPerson.setDEPT(iris.getIRIS().substring(0, 2));
					populationTemp.add(newPerson);
				}
				newMenage++;
			}

			double currentError = iris.getMarginalError(populationTemp);

			if (currentError < leastError) {
				leastError = currentError;
				populationLeastError.clear();
				populationLeastError.addAll(populationTemp);
			}

			counter++;
			if (counter > finesse) {
				valid = true;
			}
		}

		return populationLeastError;
	}

	private List<Person> getMenageAll(int index) {
		List<Person> menage = new ArrayList<Person>();
		int workingMenage = Integer.parseInt(populationAll.get(index).getNUMMI());

		while ((populationAll.size() > index)
				&& populationAll.get(index).getNUMMI().matches(String.valueOf(workingMenage))) {
			menage.add(populationAll.get(index));
			index++;
		}

		return menage;
	}

	public Integer getSampleMenageAllIndex() {
		Random random = new Random();
		double totalP = 0;

		for (Menage menage : menagesAll) {
			totalP = totalP + menage.getIpondi();
		}

		double p = random.nextDouble() * totalP;
		double cumulativeProbability = 0.0;

		for (int i = 0; i < menagesAll.size(); i++) {
			cumulativeProbability += menagesAll.get(i).getIpondi();
			if (p <= cumulativeProbability) {
				// System.out.println("----------------------------------");
				// System.out.println("i:" + i +"/" + menagesAll.size());
				// System.out.println(menagesAll.get(i).getIpondi()+"/"
				// +cumulativeProbability+"/" +totalP);
				return menagesAll.get(i).getIndex();
			}
		}
		return menagesAll.get(random.nextInt(menagesAll.size() - 1)).getIndex();
	}

	public void setMenagesAllList() {
		int initialPopulation = populationAll.size();
		String menage = "";

		menagesAll = new ArrayList<Menage>();

		for (int i = 0; i < initialPopulation; i++) {
			if (!menage.matches(populationAll.get(i).getNUMMI())) {
				menage = populationAll.get(i).getNUMMI();
				menagesAll.add(new Menage(i, Double.parseDouble(populationAll.get(i).getIPONDI())));
			}
		}

		Collections.sort(menagesAll, new Comparator<Menage>() {
			public int compare(Menage m1, Menage m2) {
				if (m1.getIpondi() > m2.getIpondi())
					return -1;
				if (m1.getIpondi() < m2.getIpondi())
					return 1;
				return 0;
			}
		});

		// System.out.println("===========================");
		// for (int i = 0; i < menages.size(); i++) {
		// System.out.println(menages.get(i).toString());
		// }

	}

}
