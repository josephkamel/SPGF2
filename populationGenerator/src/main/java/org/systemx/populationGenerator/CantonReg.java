package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.systemx.elements.DepCantonIrisElement;

public class CantonReg {

	private List<Person> population = new ArrayList<Person>();
	
	private List<Person> populationAll = new ArrayList<Person>();
	
	private List<Menage> menages = new ArrayList<Menage>();

	public CantonReg() {
		super();
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
	
	double totalP = 0;

	public List<Person> getProjectedPopulationAll(long totalPopulation){
		int index = 0;
		List<Person> populationStore = new ArrayList<Person>();
		populationStore.addAll(population);
		
		population.clear();
		population.addAll(populationAll);
		
		setMenagesList();

		for (Menage menage : menages) {
			totalP = totalP + menage.getIpondi();
		}

		List<Person> populationTemp = new ArrayList<Person>();	
		populationTemp.addAll(population);
		
		int newMenage = 1+Integer.parseInt(population.get(population.size()-1).getNUMMI());

		int timeCount = 0;
		long timeLeft = 0;
		long timeold = System.currentTimeMillis();
		
		while(populationTemp.size()<totalPopulation){
			index = getSampleMenageIndex();
			
			List<Person> menage = getMenage(index);
			for (int i = 0; i < menage.size(); i++) {
				Person newPerson = new Person(menage.get(i));
				newPerson.setNUMMI(String.valueOf(newMenage));
				populationTemp.add(newPerson);
			}
			newMenage++;
			
			if(timeCount == 1000){
				timeLeft = (System.currentTimeMillis() - timeold) * (totalPopulation - populationTemp.size())/timeCount;
				timeold = System.currentTimeMillis();
				timeCount = 0;	
			}
			timeCount++;
			
			System.out.println("getProjectedPopulationAll " +populationTemp.size() + "/" + totalPopulation + " timeLeft:" + timeLeft/60000);
			
		}
	
		
		population.clear();
		population.addAll(populationStore);
	
		return populationTemp;
	}
	
	public List<Person> getProjectedPopulation(int totalPopulation){
		int index = 0;
		
		setMenagesList();
		
		List<Person> populationTemp = new ArrayList<Person>();
	//	populationTemp.addAll(population);
		
		int newMenage = 1+Integer.parseInt(population.get(population.size()-1).getNUMMI());
		

		while(populationTemp.size()<totalPopulation){
			index = getSampleMenageIndex();
			
			List<Person> menage = getMenage(index);
			for (int i = 0; i < menage.size(); i++) {
				Person newPerson = new Person(menage.get(i));
				newPerson.setNUMMI(String.valueOf(newMenage));
				populationTemp.add(newPerson);
			}
			newMenage++;
		}
		
		return populationTemp;
	}
	
	private List<Person> getMenage(int index){
		List<Person> menage = new ArrayList<Person>();
		int workingMenage = Integer.parseInt(population.get(index).getNUMMI());
		
		while((population.size() > index) && population.get(index).getNUMMI().matches(String.valueOf(workingMenage))){
			menage.add(population.get(index));
			index++;
		}
		
		return menage;
	}
	
	public void setMenagesList(){
		int initialPopulation = population.size();
		String menage = "";

		menages = new ArrayList<Menage>();
		
		for (int i = 0; i < initialPopulation; i++) {
			if(!menage.matches(population.get(i).getNUMMI())){
				menage = population.get(i).getNUMMI();
				menages.add(new Menage(i, Double.parseDouble(population.get(i).getIPONDI())));
			}
		}
		
		Collections.sort(menages, new Comparator<Menage>() {
			  public int compare(Menage m1, Menage m2) {
			    if (m1.getIpondi() > m2.getIpondi()) return -1;
			    if (m1.getIpondi() < m2.getIpondi()) return 1;
			    return 0;
			  }});
		
//		System.out.println("===========================");
//		for (int i = 0; i < menages.size(); i++) {
//			System.out.println(menages.get(i).toString());
//		}
		
	}
	
	public Integer getSampleMenageIndex() {
		Random random = new Random();

		double p = random.nextDouble() * totalP;
		double cumulativeProbability = 0.0;

		for (int i = 0; i < menages.size(); i++) {
			cumulativeProbability += menages.get(i).getIpondi();
			if (p <= cumulativeProbability) {
//				System.out.println("----------------------------------");
//				System.out.println("i:" + i +"/" + menages.size());
//				System.out.println(menages.get(i).getIpondi()+"/" +cumulativeProbability+"/" +totalP);
				return menages.get(i).getIndex();
			}
		}
		return menages.get(random.nextInt(menages.size() - 1)).getIndex();
	}


}
