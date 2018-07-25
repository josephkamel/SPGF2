package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.systemx.elements.DepCantonIrisElement;
import org.systemx.elements.REVENUEElement;
import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.supportClasses.AgeGroup;
import org.systemx.populationGenerator.supportClasses.CommuneDecils;
import org.systemx.populationGenerator.supportClasses.Counter;
import org.systemx.populationGenerator.supportClasses.DepartementDecils;
import org.systemx.populationGenerator.supportClasses.ProgressBar;
import org.systemx.populationGenerator.supportClasses.SocioProfessionalCategory;
import org.systemx.elements.POPIRISElement;
import org.systemx.elements.PUMSElement;

public class IrisAndCantonList {

	List<Iris> irisList = new ArrayList<Iris>();
	List<Canton> cantonList = new ArrayList<Canton>();
	List<CantonDep> cantonDepList = new ArrayList<CantonDep>();

	public IrisAndCantonList() {
		super();
	}

	public List<Iris> getIrisList() {
		return irisList;
	}

	public void setIrisList(List<Iris> irisList) {
		this.irisList = irisList;
	}

	public void writeToPUMSElement(PUMSElement pumsElement) {

	}

	// public void filterPopulationByNBPI() {
	// for (int i = 0; i < irisList.size(); i++) {
	// irisList.get(i).filterByNBPI();
	// }
	// }

	public void initiateIrisList(POPIRISElement popIrisElement, Config config) {
		System.out.println("Parsing Irises "+popIrisElement.getIris().size()+" To List: ");
		ProgressBar pb = new ProgressBar(popIrisElement.getIris().size(), "Parsing Irises "+popIrisElement.getIris().size()+" To List: ");
		pb.setInverse(false);

		for (int i = 0; i < popIrisElement.getIris().size(); i++) {
			Iris iris = new Iris();
			iris.parseIris(popIrisElement.getIris().get(i), config);
			irisList.add(iris);
			pb.update(i);
		}
		System.out.println();
		System.out.println("Parsing Irises To List Finished!");
		System.out.println();
	}
	
	public void initiateRevenueElement(REVENUEElement revenueElement) {
		System.out.println("Parsing Revenue "+irisList.size()+" To List: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Parsing Revenue "+irisList.size()+" To List: ");
		pb.setInverse(false);

		Map<String, Integer> irisIndex = new HashMap<String, Integer>();

		for (int i = 0; i < revenueElement.getElement().get(0).size(); i++) {
			irisIndex.put(revenueElement.getElement().get(0).get(i), i);
		}

		for (int i = 0; i < irisList.size(); i++) {

			int irisIndexTemp = -1;
			List<Integer> decList = new ArrayList<Integer>();

			if (irisIndex.containsKey(irisList.get(i).getIRIS())) {
				irisIndexTemp = irisIndex.get(irisList.get(i).getIRIS());
				for (int j = 1; j < revenueElement.getElement().size(); j++) {
					if (isNumeric(revenueElement.getElement().get(j).get(irisIndexTemp))) {
						decList.add((int) Double.parseDouble(revenueElement.getElement().get(j).get(irisIndexTemp)));
					} else {
						decList.add(0);
					}
				}
			} else {
				for (int j = 1; j < revenueElement.getElement().size(); j++) {
					decList.add(0);
				}
			}
			
			irisList.get(i).parseRevenue(decList);
			pb.update(i);
		}
		System.out.println();
		System.out.println("Parsing Revenue To List Finished!");
		System.out.println();
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void initiatecantonList(DepCantonIrisElement depCanIris) {
		System.out.println("Parsing Canton "+depCanIris.getElement().get(0).size()+" To List: ");
		ProgressBar pb = new ProgressBar(depCanIris.getElement().get(0).size(), "Parsing Canton "+depCanIris.getElement().get(0).size()+" To List: ");
		pb.setInverse(false);

		List<String> cantAdded = new ArrayList<String>();
		for (int i = 0; i < depCanIris.getElement().get(0).size(); i++) {

			String cantCode = depCanIris.getCode_cant().get(i);

			if (!cantAdded.contains(cantCode)) {
				Canton canton = new Canton();

				canton.setCode_cant(depCanIris.getCode_cant().get(i));
				canton.setCode_dept(depCanIris.getCode_dept().get(i));
				canton.setCode_reg(depCanIris.getCode_reg().get(i));

				List<String> codeInsee = new ArrayList<String>();

				codeInsee.add(depCanIris.getInsee().get(i));

				canton.setInsee(codeInsee);

				cantAdded.add(cantCode);
				cantonList.add(canton);

			} else {
				cantonList.get(cantAdded.indexOf(cantCode)).getInsee().add(depCanIris.getInsee().get(i));
			}

			pb.update(i);
		}
		System.out.println();
		System.out.println("Parsing Canton To List Finished!");
		System.out.println();
	}

	public void initiatecantonDepList(List<String> Dep) {
		System.out.println("Parsing CantonDep "+Dep.size()+" To List: ");
		ProgressBar pb = new ProgressBar(Dep.size(), "Parsing CantonDep "+Dep.size()+" To List: ");
		pb.setInverse(false);

		for (int i = 0; i < Dep.size(); i++) {

			CantonDep cantonDep = new CantonDep();
			cantonDep.setCode_cant("99");
			cantonDep.setCode_dept(Dep.get(i));

			cantonDepList.add(cantonDep);

			pb.update(i);
		}
		System.out.println();
		System.out.println("Parsing CantonDep To List Finished!");
		System.out.println();
	}

	public void fillEmptyDecilsCommunes() {
		List<CommuneDecils> communes = new ArrayList<CommuneDecils>();
		for (int i = 0; i < irisList.size(); i++) {
			int communeIndex = -1;
			for (int j = 0; j < communes.size(); j++) {
				if (communes.get(j).getName().matches(irisList.get(i).getCOM())) {
					communeIndex = j;
					break;
				}
			}

			if (communeIndex == -1) {
				CommuneDecils com = new CommuneDecils(irisList.get(i).getCOM(), irisList.get(i).listOfDec().size());
				com.addIris(irisList.get(i).listOfDec());
				communes.add(com);
			} else {
				communes.get(communeIndex).addIris(irisList.get(i).listOfDec());
			}
		}

		Map<String, Integer> comIndex = new HashMap<String, Integer>();

		for (int i = 0; i < communes.size(); i++) {
			communes.get(i).calculateAverage();
			comIndex.put(communes.get(i).getName(), i);
		}

		for (int i = 0; i < irisList.size(); i++) {
			boolean emptyDecil = false;
			for (int j = 0; j < irisList.get(i).getDECILS().size(); j++) {
				if (irisList.get(i).getDECILS().get(j).getRevenueEnd() == 0) {
					emptyDecil = true;
				}
			}

			if (emptyDecil) {
				int comIndexLocal = comIndex.get(irisList.get(i).getCOM());
				irisList.get(i).parseRevenue(communes.get(comIndexLocal).listOfDec());
			}
		}
	}

	public void fillEmptyDecilsDepartements() {
		List<DepartementDecils> departements = new ArrayList<DepartementDecils>();
		for (int i = 0; i < irisList.size(); i++) {
			int DepartementsIndex = -1;
			for (int j = 0; j < departements.size(); j++) {
				if (departements.get(j).getName().matches(irisList.get(i).getDEP())) {
					DepartementsIndex = j;
					break;
				}
			}

			if (DepartementsIndex == -1) {
				DepartementDecils dep = new DepartementDecils(irisList.get(i).getDEP(),
						irisList.get(i).listOfDec().size());
				dep.addIris(irisList.get(i).listOfDec());
				departements.add(dep);
			} else {
				departements.get(DepartementsIndex).addIris(irisList.get(i).listOfDec());
			}
		}

		Map<String, Integer> depIndex = new HashMap<String, Integer>();

		for (int i = 0; i < departements.size(); i++) {
			departements.get(i).calculateAverage();
			depIndex.put(departements.get(i).getName(), i);
		}

		for (int i = 0; i < irisList.size(); i++) {

			boolean emptyDecil = false;
			for (int j = 0; j < irisList.get(i).getDECILS().size(); j++) {
				if (irisList.get(i).getDECILS().get(j).getRevenueEnd() == 0) {
					emptyDecil = true;
				}
			}

			if (emptyDecil) {
				int depIndexLocal = depIndex.get(irisList.get(i).getDEP());
				irisList.get(i).parseRevenue(departements.get(depIndexLocal).listOfDec());
			}
		}
	}

	// public void parseIrisElement(IRISElement irisElement) {
	// System.out.println("Parsing Irises To List: ");
	// ProgressBar pb = new ProgressBar(irisElement.getElement().get(0).size());
	// pb.setInverse(false);
	//
	// for (int i = 0; i < irisElement.getElement().get(0).size(); i++) {
	// Iris iris = new Iris();
	// iris.parseElement(irisElement.getElement(), i);
	// irisList.add(iris);
	// pb.update(i);
	// }
	// System.out.println("Parsing Irises To List Finished!");
	// }

	public void addPeopleToIrisAndCantons(PUMSElement pums) {
		System.out.println("Adding People "+pums.getIRIS().size()+" To Iris: ");
		ProgressBar pb = new ProgressBar(pums.getIRIS().size(), "Adding People "+pums.getIRIS().size()+" To Iris: ");
		pb.setInverse(false);

		int pumsSize = pums.getIRIS().size();
		int listSize = irisList.size();

		Map<String, Integer> irisIndex = new HashMap<String, Integer>();
		Map<Double, Integer> cantonIndex = new HashMap<Double, Integer>();

		Map<String, Integer> cantonDepIndex = new HashMap<String, Integer>();

		for (int i = 0; i < irisList.size(); i++) {
			irisIndex.put(irisList.get(i).getIRIS(), i);
		}
		for (int i = 1; i < cantonList.size(); i++) {
			cantonIndex.put(Double.parseDouble(cantonList.get(i).getCode_cant()), i);
		}

		for (int i = 0; i < cantonDepList.size(); i++) {
			cantonDepIndex.put(cantonDepList.get(i).getCode_dept(), i);
		}

		for (int i = 1; i < pumsSize; i++) {

			if (irisIndex.containsKey(pums.getIRIS().get(i))) {
				Person person = new Person();
				person.parseElement(pums, i);
				irisList.get(irisIndex.get(pums.getIRIS().get(i))).getPopulation().add(person);

				String dep = person.getCANTVILLE().substring(0, 2);
				String cantS = person.getCANTVILLE().substring(2, 4);

				Double cant = Double.parseDouble(cantS);

				if (cantonIndex.containsKey(cant)) {
					cantonList.get(cantonIndex.get(cant)).getPopulationAll().add(person);
				}

				cantonDepList.get(cantonDepIndex.get(dep)).getPopulationAll().add(person);

			} else {
				Person person = new Person();
				person.parseElement(pums, i);
				String dep = person.getCANTVILLE().substring(0, 2);
				String cantS = person.getCANTVILLE().substring(2, 4);

				Double cant = Double.parseDouble(cantS);

				if (!(cant == 99) && cantonIndex.containsKey(cant)) {
					cantonList.get(cantonIndex.get(cant)).getPopulation().add(person);
				} else {
					cantonDepList.get(cantonDepIndex.get(dep)).getPopulation().add(person);
				}

				if (cantonIndex.containsKey(cant)) {
					cantonList.get(cantonIndex.get(cant)).getPopulationAll().add(person);
				}

				cantonDepList.get(cantonDepIndex.get(dep)).getPopulationAll().add(person);

			}
			pb.update(i);
		}
		System.out.println();
		System.out.println("Adding People To Iris Finished!");
		System.out.println();
	}

	public void addPeopleToIris(PUMSElement pums) {
		System.out.println("Adding People To Iris: ");
		ProgressBar pb = new ProgressBar(pums.getIRIS().size(), "Adding People To Iris: ");
		pb.setInverse(false);

		int pumsSize = pums.getIRIS().size();
		int listSize = irisList.size();

		Map<String, Integer> irisIndex = new HashMap<String, Integer>();

		for (int i = 0; i < irisList.size(); i++) {
			irisIndex.put(irisList.get(i).getIRIS(), i);
		}

		for (int i = 0; i < pumsSize; i++) {
			if (irisIndex.containsKey(pums.getIRIS().get(i))) {
				Person person = new Person();
				person.parseElement(pums, i);
				irisList.get(irisIndex.get(pums.getIRIS().get(i))).getPopulation().add(person);
			}
			pb.update(i);
		}
		System.out.println("Adding People To Iris Finished!");
	}

	public void sortPopulationByNBPI() {
		System.out.println("Sorting Population "+irisList.size()+" By NBPI: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Sorting Population "+irisList.size()+" By NBPI: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {
			irisList.get(i).sortPopulationByNBPI();
			pb.update(i);
		}
		System.out.println();
		System.out.println("Sorting Finished!");
		System.out.println();
	}

	public void sortPopulationByNUMMI() {
		System.out.println("Sorting Population By NUMMI IRIS: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Sorting Population By NUMMI IRIS: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {
			irisList.get(i).sortPopulationByNUMMI();
			pb.update(i);
		}
		System.out.println();
		System.out.println("Sorting Population By NUMMI CANT: ");
		pb = new ProgressBar(cantonList.size(), "Sorting Population By NUMMI CANT: ");
		pb.setInverse(false);
		for (int i = 0; i < cantonList.size(); i++) {
			cantonList.get(i).sortPopulationByNUMMI();
			pb.update(i);
		}
		System.out.println();
		System.out.println("Sorting Population By NUMMI CANTDEP: ");
		pb = new ProgressBar(cantonDepList.size(),"Sorting Population By NUMMI CANTDEP: ");
		pb.setInverse(false);
		for (int i = 0; i < cantonDepList.size(); i++) {
			cantonDepList.get(i).sortPopulationByNUMMI();
			pb.update(i);
		}
		System.out.println();
		System.out.println("Sorting Finished!");
		System.out.println();
	}

	public void assignRevenue() {
		System.out.println("Assigning Revenue "+irisList.size()+" To Population: ");
		ProgressBar pb = new ProgressBar(irisList.size(),"Assigning Revenue "+irisList.size()+" To Population: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {
			irisList.get(i).assignRevenue();
			pb.update(i);
		}
		System.out.println();
		System.out.println("Assigning Revenue To Population Finished!");
		System.out.println();
	}

	public void projectPopulationV1() {
		System.out.println("Projecting population V1: ");
		ProgressBar pb = new ProgressBar(irisList.size(),"Projecting population V1: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {

			if (irisList.get(i).getPopulation().size() > 0) {
				irisList.get(i).projectPopulation();
			} else {
				if (irisList.get(i).getNbrPopulation() > 0) {
					// EXPERIMENTAL================
					int indexAfter = i + 1;
					if (indexAfter == irisList.size()) {
						indexAfter = 0;
					}
					while (irisList.get(indexAfter).getPopulation().size() == 0) {
						indexAfter++;
						if (indexAfter == irisList.size()) {
							indexAfter = 0;
						}
					}

					if (irisList.get(indexAfter).getPopulation().size() > irisList.get(i).getNbrPopulation()) {
						for (int j = 0; j < irisList.get(i).getNbrPopulation(); j++) {
							irisList.get(i).getPopulation().add(irisList.get(indexAfter).getPopulation().get(j));

						}
					} else {
						irisList.get(i).getPopulation().addAll(irisList.get(indexAfter).getPopulation());
					}

					for (int j = 0; j < irisList.get(i).getPopulation().size(); j++) {
						irisList.get(i).getPopulation().get(j).setIRIS(irisList.get(i).getIRIS());
					}

					irisList.get(i).projectPopulation();

					// EXPERIMENTAL================
				}
			}

			pb.update(i);
		}
		System.out.println("Projecting population Finished!");
	}

	public void projectPopulationV2() {
		System.out.println("Projecting population V2: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Projecting population V2: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {

			if (irisList.get(i).getPopulation().size() > 0) {
				irisList.get(i).projectPopulationWithIPONDI();
			} else {
				if (irisList.get(i).getNbrPopulation() > 0) {
					// EXPERIMENTAL================
					int indexAfter = i + 1;
					if (indexAfter == irisList.size()) {
						indexAfter = 0;
					}
					while (irisList.get(indexAfter).getPopulation().size() == 0) {
						indexAfter++;
						if (indexAfter == irisList.size()) {
							indexAfter = 0;
						}
					}

					if (irisList.get(indexAfter).getPopulation().size() > irisList.get(i).getNbrPopulation()) {
						for (int j = 0; j < irisList.get(i).getNbrPopulation(); j++) {
							irisList.get(i).getPopulation().add(irisList.get(indexAfter).getPopulation().get(j));

						}
					} else {
						irisList.get(i).getPopulation().addAll(irisList.get(indexAfter).getPopulation());
					}

					for (int j = 0; j < irisList.get(i).getPopulation().size(); j++) {
						irisList.get(i).getPopulation().get(j).setIRIS(irisList.get(i).getIRIS());
					}

					irisList.get(i).projectPopulationWithIPONDI();

					// EXPERIMENTAL================
				}
			}
			pb.update(i);
		}
		System.out.println("Projecting population Finished!");
	}

	public void projectPopulationWithCANTON() {
		System.out.println("Projecting population CANTON: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Projecting population CANTON: ");
		pb.setInverse(false);

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
				irisList.get(i).projectPopulationWithIPONDI();
			} else {
				if (irisList.get(i).getNbrPopulation() > 0) {

					String codeInsee = irisList.get(i).getIRIS().substring(0, 5);
					String codeDep = irisList.get(i).getIRIS().substring(0, 2);

					if (inseeIndex.containsKey(codeInsee)
							&& cantonList.get(inseeIndex.get(codeInsee)).getPopulation().size() > 0) {

						List<Person> populationCan = new ArrayList<Person>();
						populationCan = cantonList.get(inseeIndex.get(codeInsee)).getProjectedPopulation(
								irisList.get(i).getNbrPopulation(), irisList.get(i).getIRIS());
						irisList.get(i).setPopulation(populationCan);
					} else {

						if (cantonDepList.get(depIndex.get(codeDep)).getPopulation().size() <= 0) {

							if (inseeIndex.containsKey(codeInsee)
									&& cantonList.get(inseeIndex.get(codeInsee)).getPopulation().size() > 0) {
								List<Person> populationCan = new ArrayList<Person>();
								populationCan = cantonList.get(inseeIndex.get(codeInsee)).getProjectedPopulationAll(
										irisList.get(i).getNbrPopulation(), irisList.get(i).getIRIS());
								irisList.get(i).setPopulation(populationCan);

								System.err.println("Cant" + cantonList.get(inseeIndex.get(codeInsee)).getCode_cant());

							} else {
								List<Person> populationCan = new ArrayList<Person>();
								populationCan = cantonDepList.get(depIndex.get(codeDep)).getProjectedPopulationAll(
										irisList.get(i).getNbrPopulation(), irisList.get(i).getIRIS());
								irisList.get(i).setPopulation(populationCan);
								System.err.println("Cant" + cantonList.get(inseeIndex.get(codeInsee)).getCode_cant());
								System.err.println("Dep" + cantonDepList.get(depIndex.get(codeDep)).getCode_dept());
							}

						} else {
							List<Person> populationCan = new ArrayList<Person>();
							populationCan = cantonDepList.get(depIndex.get(codeDep)).getProjectedPopulation(
									irisList.get(i).getNbrPopulation(), irisList.get(i).getIRIS());
							irisList.get(i).setPopulation(populationCan);
						}
					}
				}
			}
			pb.update(i);
		}
		System.out.println("Projecting population Finished!");
	}

	public void projectPopulationWithCO(int finesse) {

		System.out.println("Projecting population CO: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Projecting population CO: ");
		pb.setInverse(false);

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
						populationCan = cantonList.get(inseeIndex.get(codeInsee)).getProjectedPopulationWithCO(
								irisList.get(i).getNbrPopulation(), irisList.get(i), finesse);
						irisList.get(i).setPopulation(populationCan);
					} else {

						if (cantonDepList.get(depIndex.get(codeDep)).getPopulation().size() <= 0) {

							if (inseeIndex.containsKey(codeInsee)
									&& cantonList.get(inseeIndex.get(codeInsee)).getPopulation().size() > 0) {
								List<Person> populationCan = new ArrayList<Person>();
								populationCan = cantonList.get(inseeIndex.get(codeInsee))
										.getProjectedPopulationAllWithCO(irisList.get(i).getNbrPopulation(),
												irisList.get(i), finesse);
								irisList.get(i).setPopulation(populationCan);

							} else {
								List<Person> populationCan = new ArrayList<Person>();
								populationCan = cantonDepList.get(depIndex.get(codeDep))
										.getProjectedPopulationAllWithCO(irisList.get(i).getNbrPopulation(),
												irisList.get(i), finesse);
								irisList.get(i).setPopulation(populationCan);
							}

						} else {
							List<Person> populationCan = new ArrayList<Person>();
							populationCan = cantonDepList.get(depIndex.get(codeDep)).getProjectedPopulationWithCO(
									irisList.get(i).getNbrPopulation(), irisList.get(i), finesse);
							irisList.get(i).setPopulation(populationCan);
						}
					}
				}
			}
			pb.update(i);
		}
		System.out.println("Projecting population Finished!");
	}

	public String projectPopulationThreaded(int threadsNbr, int finesse) {

		long timeStart = System.currentTimeMillis();

		int irisListSize = irisList.size();

		System.out.println("Projecting population: Threads:" +threadsNbr+" Finesse:"+finesse+" NbrOfIris:"+ irisListSize);

		Counter counter = new Counter("Population Projector # ", irisListSize);

		int irisPerThread = irisListSize / threadsNbr;
		if (irisPerThread == 0) {
			irisPerThread = 100;
			threadsNbr = 1 + irisListSize / irisPerThread;
		}

		List<List<Iris>> irisListThreads = new ArrayList<List<Iris>>();

		int sizeF = 0;

		for (int i = 0; i < threadsNbr; i++) {
			List<Iris> tempIris;
			if (i < threadsNbr - 1) {
				tempIris = irisList.subList(sizeF, sizeF + irisPerThread);
				sizeF = sizeF + irisPerThread;
			} else {
				tempIris = irisList.subList(sizeF, irisListSize);
			}
			irisListThreads.add(tempIris);
		}

		List<populationProjector> threads = new ArrayList<populationProjector>();

		for (int i = 0; i < threadsNbr; i++) {
			populationProjector thread = new populationProjector();

			List<CantonDep> cantonDepListTemp = new ArrayList<CantonDep>();
			for (CantonDep cd : cantonDepList) {
				cantonDepListTemp.add(new CantonDep(cd));
			}
			thread.setCantonDepList(cantonDepListTemp);

			List<Canton> cantonListTemp = new ArrayList<Canton>();
			for (Canton c : cantonList) {
				cantonListTemp.add(new Canton(c));
			}
			thread.setCantonList(cantonListTemp);
			thread.setIrisList(irisListThreads.get(i));
			thread.setCounter(counter);
			thread.setFinesse(finesse);
			thread.start();
			threads.add(thread);
		}

		for (populationProjector thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		irisList.clear();

		for (int i = 0; i < threads.size(); i++) {
			irisList.addAll(threads.get(i).getIrisListOut());
		}

		long milliseconds = (System.currentTimeMillis() - timeStart);

		int seconds = (int) (milliseconds / 1000) % 60;
		int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
		int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

		System.out.println("Total Time: " + hours + "h" + minutes + "m" + seconds + "s");
		System.out.println();
		
		return (hours + "h" + minutes + "m" + seconds + "s");
	}

	public void addPopulationSizeMarginalData(POPIRISElement popirisElement) {
		System.out.println("Adding TotalPopulation "+irisList.size()+" To Iris: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Adding TotalPopulation "+irisList.size()+" To Iris: ");
		pb.setInverse(false);
		for (int i = 0; i < irisList.size(); i++) {
			if (popirisElement.getIris().contains(irisList.get(i).getIRIS())) {
				int index = popirisElement.getIris().indexOf(irisList.get(i).getIRIS());
				irisList.get(i).setNbrPopulation(Integer.parseInt(popirisElement.getNbrPopulation().get(index)));
			}
			pb.update(i);
		}
		System.out.println();
		System.out.println("Adding TotalPopulation To Iris Finished!");
		System.out.println();
	}

	public void addAgeGroupsMarginalData(POPIRISElement popirisElement) {
		System.out.println("Adding AgeGroups "+ irisList.size() +" To Iris: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Adding AgeGroups "+ irisList.size() +" To Iris: ");
		pb.setInverse(false);

		List<List<String>> ageGroupsTemp = popirisElement.getAgeGroupList();
		
		for (int i = 0; i < irisList.size(); i++) {

			if (popirisElement.getIris().contains(irisList.get(i).getIRIS())) {
				int index = popirisElement.getIris().indexOf(irisList.get(i).getIRIS());

				for (int j = 0; j < ageGroupsTemp.size(); j++) {
					List<String> range = Arrays.asList(popirisElement.getAgeGroupRanges().get(j).split("_"));
					AgeGroup ageGroup = new AgeGroup();
					ageGroup.setAgeStart(Integer.parseInt(range.get(1)));
					ageGroup.setAgeEnd(Integer.parseInt(range.get(2)));
					ageGroup.setPopNbr(Integer.parseInt(ageGroupsTemp.get(j).get(index)));
					irisList.get(i).getAgeGroups().add(ageGroup);
				}
			}			

			pb.update(i);
		}
		
		System.out.println();
		System.out.println("Adding AgeGroups To Iris Finished!");
		System.out.println();
	}
	
	public void addNbrMenagesMarginalData(POPIRISElement popirisElement) {
		System.out.println("Adding NbrMenages "+irisList.size()+" To Iris: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Adding NbrMenages "+irisList.size()+" To Iris: ");
		pb.setInverse(false);

		List<String> nbrMenages = popirisElement.getNbrMenages();

		for (int i = 0; i < irisList.size(); i++) {
			if (popirisElement.getIris().contains(irisList.get(i).getIRIS())) {
				int index = popirisElement.getIris().indexOf(irisList.get(i).getIRIS());
				irisList.get(i).setNbrMenages(Integer.parseInt(nbrMenages.get(index)));
			}
			pb.update(i);
		}
		
		System.out.println();
		System.out.println("Adding NbrMenages To Iris Finished!");
		System.out.println();
	}
	
	public void addNbrHommeFemmesMarginalData(POPIRISElement popirisElement) {
		System.out.println("Adding NbrHommeFemme "+irisList.size()+" To Iris: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Adding NbrHommeFemme "+irisList.size()+" To Iris: ");
		pb.setInverse(false);

		List<String> nbrHommes = popirisElement.getNbrHommes();
		List<String> nbrFemmes = popirisElement.getNbrFemmes();

		for (int i = 0; i < irisList.size(); i++) {
			if (popirisElement.getIris().contains(irisList.get(i).getIRIS())) {
				int index = popirisElement.getIris().indexOf(irisList.get(i).getIRIS());
				irisList.get(i).setNbrHommes(Integer.parseInt(nbrHommes.get(index)));
				irisList.get(i).setNbrFemmes(Integer.parseInt(nbrFemmes.get(index)));
			}
			pb.update(i);
		}
		
		System.out.println();
		System.out.println("Adding NbrHommeFemme To Iris Finished!");
		System.out.println();
	}

	
	public void addSocioProfessionalCategoryMarginalData(POPIRISElement popirisElement) {
		System.out.println("Adding SocioProfessionalCategories "+irisList.size()+" To Iris: ");
		ProgressBar pb = new ProgressBar(irisList.size(), "Adding SocioProfessionalCategories "+irisList.size()+" To Iris: ");
		pb.setInverse(false);

		List<List<String>> socioProfessionalCategories = popirisElement.getSocioProfessionalCategoryList();

		for (int i = 0; i < irisList.size(); i++) {
			if (popirisElement.getIris().contains(irisList.get(i).getIRIS())) {
				int index = popirisElement.getIris().indexOf(irisList.get(i).getIRIS());
				for (int j = 0; j < socioProfessionalCategories.size(); j++) {
					List<String> cat = Arrays.asList(popirisElement.getSocioProfessionalCategoryRanges().get(j).split("_"));
					SocioProfessionalCategory socioProfessionalCategory = new SocioProfessionalCategory();
					socioProfessionalCategory.setCategory(Integer.parseInt(cat.get(1)));
					socioProfessionalCategory.setPopNbr(Integer.parseInt(socioProfessionalCategories.get(j).get(index)));
					irisList.get(i).getSocioProfessionalCategory().add(socioProfessionalCategory);
				}
			}
			pb.update(i);
		}
		
		System.out.println();
		System.out.println("Adding SocioProfessionalCategories To Iris Finished!");
		System.out.println();
	}

	public void printIrises(int irisesNumber) {
		if (irisesNumber > irisList.size()) {
			irisesNumber = irisList.size();
		}

		for (int i = 0; i < irisesNumber; i++) {
			if (irisList.get(i).getPopulation().size() > 2) {
				System.out.println("==================IRIS NAME:" + irisList.get(i).getIRIS() + "Population:"
						+ irisList.get(i).getNbrPopulation());
				for (int j = 0; j < irisList.get(i).getPopulation().size(); j++) {
					System.out.println(irisList.get(i).getPopulation().get(j));
				}
			}
		}
	}

}
