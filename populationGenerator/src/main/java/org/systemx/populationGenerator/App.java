package org.systemx.populationGenerator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.systemx.populationGenerator.GUI.MainFrame;
import org.systemx.populationGenerator.config.Config;
import org.systemx.populationGenerator.config.ConfigGroup;
import org.systemx.populationGenerator.supportClasses.Counter;
import org.systemx.populationGenerator.supportClasses.ProgressBar;


public class App {

	static String inputPath = "C:\\Users\\User\\Desktop\\input-popgen\\input\\";

	static String pathPums = inputPath + "pums.txt";
	static String pathConfig = inputPath + "config.xml";
	static String pathCantDepReg = inputPath + "cant_dep_reg.csv";
	static String pathMarginalData = inputPath + "marginal_data_iris.csv";
	static String pathRevenue = inputPath + "revenue_iris.csv";

	static String pathPumsFil = inputPath + "pums_filtered.txt";

	static String pathPUMSOut = inputPath + "output\\SynPop_generated.txt";
	static String pathValdiation = inputPath + "output\\marginal_data_generated.csv";

	public static void main(String[] args) {
		
		if(args.length == 0) {
			MainFrame.run();
		}else {
			String configPath = args[0];
			
			if(configPath.matches("runGUI")){
				MainFrame.run();
			}else{
				Config config;
				try {
					config = ConfigGroup.read(configPath);
					run(config);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void run(Config config) {
		projectPopulationThread thread = new projectPopulationThread();
		thread.setConfig(config);
		thread.start();
	}
	

	public static void run(Config config, JProgressBar jProgressBar, JLabel jLabel,  JPanel jPanel) {
		ProgressBar.setProgressBar(jProgressBar);
		ProgressBar.setjLabel(jLabel);
		ProgressBar.setjPanel(jPanel);
		
		Counter.setProgressBar(jProgressBar);
		Counter.setjLabel(jLabel);
		Counter.setjPanel(jPanel);
		
		projectPopulationThread thread = new projectPopulationThread();
		thread.setConfig(config);
		thread.start();
	}



}
