package org.systemx.elements;

import java.util.ArrayList;
import java.util.List;

import org.systemx.populationGenerator.supportClasses.ProgressBar;

public class REVENUEElement {
	private List<List<String>> element = new ArrayList<List<String>>();
	private List<String> IRIS;
	private List<String> DEC_D113;
	private List<String> DEC_D213;
	private List<String> DEC_D313;
	private List<String> DEC_D413;
	private List<String> DEC_D513;
	private List<String> DEC_D613;
	private List<String> DEC_D713;
	private List<String> DEC_D813;
	private List<String> DEC_D913;

	public REVENUEElement() {
		super();
		IRIS = new ArrayList<String>();
		DEC_D113 = new ArrayList<String>();
		DEC_D213 = new ArrayList<String>();
		DEC_D313 = new ArrayList<String>();
		DEC_D413 = new ArrayList<String>();
		DEC_D513 = new ArrayList<String>();
		DEC_D613 = new ArrayList<String>();
		DEC_D713 = new ArrayList<String>();
		DEC_D813 = new ArrayList<String>();
		DEC_D913 = new ArrayList<String>();

		IRIS.add("IRIS");
		DEC_D113.add("DEC_D113");
		DEC_D213.add("DEC_D213");
		DEC_D313.add("DEC_D313");
		DEC_D413.add("DEC_D413");
		DEC_D513.add("DEC_D513");
		DEC_D613.add("DEC_D613");
		DEC_D713.add("DEC_D713");
		DEC_D813.add("DEC_D813");
		DEC_D913.add("DEC_D913");

		element.add(IRIS);
		element.add(DEC_D113);
		element.add(DEC_D213);
		element.add(DEC_D313);
		element.add(DEC_D413);
		element.add(DEC_D513);
		element.add(DEC_D613);
		element.add(DEC_D713);
		element.add(DEC_D813);
		element.add(DEC_D913);
	}

	public void println(int linesNumber) {
		for (int i = 0; i < getElement().size(); i++) {
			System.out.println(getElement().get(i).get(0) + ":" + getElement().get(i).size());
		}

		for (int j = 0; j < linesNumber; j++) {
			for (int i = 0; i < getElement().size(); i++) {
				System.out.print(getElement().get(i).get(j) + "	");
			}
			System.out.println();
		}
	}

	public void removeElement(int index) {
		if (getElement().get(0).size() > index) {
			for (int i = 0; i < getElement().size(); i++) {
				getElement().get(i).remove(index);
			}
		}
	}

	public void cleanList() {
		int removed = 0;
		System.out.println("Cleaning Revenue "+ element.get(0).size() +" List:");
		ProgressBar pb = new ProgressBar(getElement().get(0).size() - 1, "Cleaning Revenue "+ element.get(0).size() +" List:");
		for (int i = getElement().get(0).size() - 1; i >= 0; i--) {
			for (int j = 1; j < getElement().size(); j++) {
				if (!isNumeric(getElement().get(j).get(i))) {
					removeElement(i);
					removed++;
					break;
				}
			}
			pb.update(i);
		}
		System.out.println();
		System.out.println("Removed elements:" + removed);
		System.out.println();
	}

	public void removeNotInPopulation(PUMSElement pums) {
		int removed = 0;
		System.out.println("Removing " + IRIS.size() + " iris not in population started:");
		ProgressBar pb = new ProgressBar(IRIS.size() - 1, "Removing " + IRIS.size() + " iris not in population started:");
		for (int i = IRIS.size() - 1; i >= 0; i--) {
			if (!pums.getIRIS().contains(IRIS.get(i))) {
				removeElement(i);
				removed++;
			}
			pb.update(i);
		}
		System.out.println("Removed elements:" + removed);
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public List<List<String>> getElement() {
		return element;
	}

	public void setElement(List<List<String>> element) {
		this.element = element;
	}

	public void addElement(List<String> oneElement) {
		for (int i = 0; i < getElement().size(); i++) {
			getElement().get(i).add(oneElement.get(i));
		}
	}
}
