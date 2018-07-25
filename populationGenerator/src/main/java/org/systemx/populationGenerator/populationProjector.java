package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.systemx.populationGenerator.supportClasses.Counter;


public class populationProjector extends Thread{

	
	List<Iris> irisList = new ArrayList<Iris>();
	
	List<Iris> irisListOut = new ArrayList<Iris>();
	List<Canton> cantonList = new ArrayList<Canton>();
	List<CantonDep> cantonDepList = new ArrayList<CantonDep>();

	int finesse = 100;
	
	Counter counter;

	    @Override
	    public void run() {
			Map<String, Integer> inseeIndex = new HashMap<String, Integer>();

			for (int i = 0; i < cantonList.size(); i++) {
				for (int j = 0; j < cantonList.get(i).getInsee().size(); j++) {
					inseeIndex.put(cantonList.get(i).getInsee().get(j), i);
				}
			}

			Map<String, Integer> depIndex = new HashMap<String, Integer>();

			for (int i = 0; i < cantonDepList.size(); i++) {
				depIndex.put(cantonDepList.get(i).getCode_dept(), i);
			}
			
			for (int i = 0; i < irisList.size(); i++) {
				if (irisList.get(i).getPopulation().size() > 0) {
					irisList.get(i).projectPopulationWithIPONDIWithCO(finesse);
					
				} else {
					if (irisList.get(i).getNbrPopulation() > 0) {
						
						String codeInsee = irisList.get(i).getIRIS().substring(0, 5);
						String codeDep = irisList.get(i).getIRIS().substring(0, 2);

						if (inseeIndex.containsKey(codeInsee)
								&& cantonList.get(inseeIndex.get(codeInsee)).getPopulation().size() > 0) {

							List<Person> populationCan = new ArrayList<Person>();
							populationCan = cantonList.get(inseeIndex.get(codeInsee))
									.getProjectedPopulationWithCO(irisList.get(i).getNbrPopulation(), irisList.get(i), finesse);
							irisList.get(i).setPopulation(populationCan);
						} else {

							if (cantonDepList.get(depIndex.get(codeDep)).getPopulation().size() <= 0) {

								if(inseeIndex.containsKey(codeInsee) && cantonList.get(inseeIndex.get(codeInsee)).getPopulation().size() > 0){
									List<Person> populationCan = new ArrayList<Person>();
									populationCan = cantonList.get(inseeIndex.get(codeInsee))
											.getProjectedPopulationAllWithCO(irisList.get(i).getNbrPopulation(),irisList.get(i), finesse);
									irisList.get(i).setPopulation(populationCan);
									
								}else{
									List<Person> populationCan = new ArrayList<Person>();
									populationCan = cantonDepList.get(depIndex.get(codeDep))
											.getProjectedPopulationAllWithCO(irisList.get(i).getNbrPopulation(), irisList.get(i), finesse);
									irisList.get(i).setPopulation(populationCan);
								}
								

							} else {
								List<Person> populationCan = new ArrayList<Person>();
								populationCan = cantonDepList.get(depIndex.get(codeDep))
										.getProjectedPopulationWithCO(irisList.get(i).getNbrPopulation(), irisList.get(i), finesse);
								irisList.get(i).setPopulation(populationCan);
							}
						}
					}
				}
				counter.incCounter();
			}
			
			irisListOut.addAll(irisList);
	    }

	    
		public List<Iris> getIrisListOut() {
			return irisListOut;
		}

		public void setIrisList(List<Iris> irisList) {
			this.irisList = irisList;
		}



		public void setCantonList(List<Canton> cantonList) {
			this.cantonList = cantonList;
		}


		public void setCantonDepList(List<CantonDep> cantonDepList) {
			this.cantonDepList = cantonDepList;
		}


		public void setCounter(Counter counter) {
			this.counter = counter;
		}


		public void setFinesse(int finesse) {
			this.finesse = finesse;
		}
		
		
	
		
		
}
