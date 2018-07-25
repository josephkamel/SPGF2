package org.systemx.populationGenerator.supportClasses;

import java.util.ArrayList;
import java.util.List;

public class CommuneDecils {

	private String Name;
	List<Integer> Decils = new ArrayList<Integer>();

	private int counter;

	public CommuneDecils(String name, int decSize) {
		super();
		Name = name;
		for (int i = 0; i < decSize; i++) {
			Decils.add(0);
		}
		
	}

	public void addIris(List<Integer> irisDecils) {
		if (irisDecils.get(0) != 0) {
			counter++;
			for (int i = 0; i < irisDecils.size(); i++) {
				Decils.set(i, Decils.get(i) + irisDecils.get(i));
			}

		}
	}

	public void calculateAverage() {
		if (counter > 0) {
			for (int i = 0; i < Decils.size(); i++) {
				Decils.set(i, Decils.get(i)/counter);
			}
		}
	}

	public List<Integer> listOfDec() {
		return Decils;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
