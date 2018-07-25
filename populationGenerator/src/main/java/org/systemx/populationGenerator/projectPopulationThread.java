package org.systemx.populationGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.systemx.elements.DepCantonIrisElement;
import org.systemx.elements.POPIRISElement;
import org.systemx.elements.POPIRISElementValidation;
import org.systemx.elements.PUMSElement;
import org.systemx.elements.REVENUEElement;
import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.parsers.FileParser;
import org.systemx.populationGenerator.supportClasses.ProgressBar;

public class projectPopulationThread extends Thread{

	Config config;
	
	public void setConfig(Config config) {
		this.config = config;
	}

	@Override
    public void run() {
		projectPopulation(config);
    }
	
	static void projectPopulation(Config config) {
		
		String pathPums = config.getPumsPath();
		String pathCantDepReg = config.getCantDepRegPath();
		String pathMarginalData = config.getMarginalDataPath();
		String pathRevenue = config.getRevenuePath();

		String pathPumsFil = config.getPumsFilteredPath();
		String pathPUMSOut = config.getOutputPath() + "Synthetic_Population_generated.txt";
		String pathValdiation = config.getOutputPath() + "marginal_data_generated.csv";

		int nbrThreads = config.getNbrThreads();
		int finesse = config.getFinesse();
		
		String deps = config.getDepString();
		
		List<String> depList = Arrays.asList(deps.split("-"));
		boolean WithRevenue = config.isAddRevenue();
		
		FileParser fileParser = new FileParser();
		PUMSElement pumsElement = new PUMSElement();

		DepCantonIrisElement depCantonIrisElement = new DepCantonIrisElement();
		IrisAndCantonList irisCantList = new IrisAndCantonList();

		try {
			fileParser.fileFilterDep(pathPums, pathPumsFil, pumsElement.getElement(), depList, "Pums not in dep");
			pumsElement = new PUMSElement();
			fileParser.parseFile(pathPumsFil, pumsElement.getElement(), "Pums");
			POPIRISElement popirisElement = new POPIRISElement(fileParser.parseFile(pathMarginalData, "Marginal Data"));
			fileParser.parseFile(pathCantDepReg, depCantonIrisElement.getElement(), "Cant Dep Reg");

			pumsElement.cleanList();
			//pumsElement.recycleList();
			popirisElement.cleanList();
			popirisElement.removeNotInDep(depList);

			irisCantList.initiateIrisList(popirisElement, config);
			irisCantList.initiatecantonList(depCantonIrisElement);
			irisCantList.initiatecantonDepList(depList);
			irisCantList.addPeopleToIrisAndCantons(pumsElement);

			irisCantList.addPopulationSizeMarginalData(popirisElement);
			irisCantList.addAgeGroupsMarginalData(popirisElement);
			irisCantList.addNbrHommeFemmesMarginalData(popirisElement);
			irisCantList.addNbrMenagesMarginalData(popirisElement);
			irisCantList.addSocioProfessionalCategoryMarginalData(popirisElement);
			irisCantList.sortPopulationByNUMMI();

			String runTime = "";
			runTime = irisCantList.projectPopulationThreaded(nbrThreads, finesse);

			if (WithRevenue) {
				REVENUEElement revenueElement = new REVENUEElement();
				fileParser.parseFile(pathRevenue, revenueElement.getElement(), "Revenue");
				revenueElement.cleanList();
				irisCantList.initiateRevenueElement(revenueElement);
				irisCantList.fillEmptyDecilsCommunes();
				irisCantList.fillEmptyDecilsDepartements();
				irisCantList.sortPopulationByNBPI();
				irisCantList.assignRevenue();
			}

			POPIRISElementValidation POPIRISElementValidation = new POPIRISElementValidation(popirisElement, config);

			System.out.println("Preparing Write Elements:");
			ProgressBar pb = new ProgressBar(irisCantList.getIrisList().size(), "Preparing Write Elements");

			PUMSElement pumsWrite = new PUMSElement();
			for (int i = 0; i < irisCantList.getIrisList().size(); i++) {
				for (int j = 0; j < irisCantList.getIrisList().get(i).getPopulation().size(); j++) {
					pumsWrite.addOneElement(irisCantList.getIrisList().get(i).getPopulation().get(j).serialisePerson());
				}
				POPIRISElementValidation.addIris(irisCantList.getIrisList().get(i));
				pb.update(i);
			}

			POPIRISElementValidation.finaliseErrors(runTime);

			System.out.println();
			System.out.println("Preparing Write Elements Finished!");
			System.out.println();

			fileParser.writeFile(pathValdiation, POPIRISElementValidation.getElement(), "marginal_data_generated");
			fileParser.writeFile(pathPUMSOut, pumsWrite.getElement(), "Synthetic_Population_generated");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
