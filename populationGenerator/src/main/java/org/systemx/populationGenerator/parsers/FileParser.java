package org.systemx.populationGenerator.parsers;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.systemx.populationGenerator.supportClasses.ProgressBar;

public class FileParser {

	public void parseFile(String path, List<List<String>> element, String elementName) throws IOException {
		File file = new File(path);
		List<String> lines = readFile(file);
		System.out.println("Parsing "+elementName);

		List<String> firstLine = Arrays.asList(lines.get(0).split(";"));


		int number = lines.size();
		if (number > lines.size()) {
			number = lines.size();
		}

		ProgressBar progressBar = new ProgressBar(lines.size(), "Parsing "+elementName);
		progressBar.setInverse(false);

		Map<Integer, Integer> attributeIndexs = new HashMap<Integer, Integer>();

		for (int i = 0; i < firstLine.size(); i++) {
			attributeIndexs.put(i, attributeIndex(firstLine.get(i), element));
		}

		for (int i = 1; i < number; i++) {
			List<String> splitList = Arrays.asList(lines.get(i).split(";"));
			int attributeIndex;

			for (int j = 0; j < splitList.size(); j++) {
				attributeIndex = attributeIndexs.get(j);
				if (attributeIndex != -1) {
					element.get(attributeIndex).add(splitList.get(j));
				}
			}
			
			progressBar.update(i);
		}
		System.out.println();
		System.out.println("Parsing "+elementName+" done!");
		System.out.println();
	}

	
	public List<List<String>> parseFile(String path, String elementName) throws IOException {
		File file = new File(path);
		List<String> lines = readFile(file);
		System.out.println("Parsing "+elementName+":");

		List<List<String>> element = new ArrayList<List<String>>();
		
		List<String> firstLine = Arrays.asList(lines.get(0).split(";"));
		
		for (int i = 0; i < firstLine.size(); i++) {
			List<String> col = new ArrayList<String>();
			col.add(firstLine.get(i));
			element.add(col);
		}

		int number = lines.size();
		if (number > lines.size()) {
			number = lines.size();
		}

		ProgressBar progressBar = new ProgressBar(lines.size(), "Parsing "+elementName);
		progressBar.setInverse(false);

		Map<Integer, Integer> attributeIndexs = new HashMap<Integer, Integer>();

		for (int i = 0; i < firstLine.size(); i++) {
			attributeIndexs.put(i, attributeIndex(firstLine.get(i), element));
		}

		for (int i = 1; i < number; i++) {
			List<String> splitList = Arrays.asList(lines.get(i).split(";"));
			int attributeIndex;

			for (int j = 0; j < splitList.size(); j++) {
				attributeIndex = attributeIndexs.get(j);
				if (attributeIndex != -1) {
					element.get(attributeIndex).add(splitList.get(j));
				}
			}
			
			progressBar.update(i);
		}
		
		System.out.println();
		System.out.println("Parsing "+elementName+" done!");
		System.out.println();
		
		return element;
	}
	
	public void fileFilterDep(String pathIn, String pathOut, List<List<String>> element, List<String> dep, String elementName)
			throws IOException {
		File file = new File(pathIn);
		List<String> lines = readFile(file);
		System.out.println("Filtering "+elementName);

		File fileOut = new File(pathOut);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileOut, false)));
		String stdout = "";

		List<String> firstLine = Arrays.asList(lines.get(0).split(";"));

		int number = lines.size();
		if (number > lines.size()) {
			number = lines.size();
		}
		ProgressBar progressBar = new ProgressBar(lines.size(),"Filtering "+elementName);
		progressBar.setInverse(false);

		Map<Integer, Integer> attributeIndexs = new HashMap<Integer, Integer>();

		int depIndex = 26;

		for (int i = 0; i < firstLine.size(); i++) {
			attributeIndexs.put(i, attributeIndex(firstLine.get(i), element));

			if (firstLine.get(i).matches("DEPT")) {
				depIndex = i;
			}
		}

		int attributeIndexFirstLine;
		List<String> splitListFirstLine = Arrays.asList(lines.get(0).split(";"));
		String stdoutFirstLine = "";
		for (int j = 0; j < splitListFirstLine.size(); j++) {
			attributeIndexFirstLine = attributeIndexs.get(j);
			if (attributeIndexFirstLine != -1) {
				stdoutFirstLine = stdoutFirstLine + splitListFirstLine.get(j);
				stdoutFirstLine = stdoutFirstLine + ";";
			}
		}
		out.println(stdoutFirstLine);

		for (int i = 1; i < number; i++) {
			List<String> splitList = Arrays.asList(lines.get(i).split(";"));
			int attributeIndex;

			if (dep.contains(splitList.get(depIndex))) {

				stdout = "";
				for (int j = 0; j < splitList.size(); j++) {
					attributeIndex = attributeIndexs.get(j);
					if (attributeIndex != -1) {
						stdout = stdout + splitList.get(j);
						stdout = stdout + ";";
					}
				}

				out.println(stdout);
			} else {
				//System.out.println("DEPT OUT " + splitList.get(depIndex));
			}

			progressBar.update(i);
		}

		out.close();

		System.out.println();
		System.out.println("Filtering element done!");
		System.out.println();
	}
	
	public void fileFilterIris(String pathIn, String pathOut, List<List<String>> element, List<Integer> iris)
			throws IOException {
		File file = new File(pathIn);
		List<String> lines = readFile(file);
		System.out.println("Filtering element:");

		File fileOut = new File(pathOut);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileOut, false)));
		String stdout = "";

		List<String> firstLine = Arrays.asList(lines.get(0).split(","));
		// for (int i = 1; i < lines.size(); i++) {
		int number = lines.size();
		if (number > lines.size()) {
			number = lines.size();
		}
		ProgressBar progressBar = new ProgressBar(lines.size(),"Filtering element:");
		progressBar.setInverse(false);

		Map<Integer, Integer> attributeIndexs = new HashMap<Integer, Integer>();

		int irisIndex = 0;

		for (int i = 0; i < firstLine.size(); i++) {
			attributeIndexs.put(i, attributeIndex(firstLine.get(i), element));

			if (firstLine.get(i).matches("IRIS")) {
				irisIndex = i;
			}
		}

		int attributeIndexFirstLine;
		List<String> splitListFirstLine = Arrays.asList(lines.get(0).split(","));
		String stdoutFirstLine = "";
		for (int j = 0; j < splitListFirstLine.size(); j++) {
			attributeIndexFirstLine = attributeIndexs.get(j);
			if (attributeIndexFirstLine != -1) {
				stdoutFirstLine = stdoutFirstLine + splitListFirstLine.get(j);
				stdoutFirstLine = stdoutFirstLine + ",";
			}
		}
		out.println(stdoutFirstLine);

		for (int i = 1; i < number; i++) {
			List<String> splitList = Arrays.asList(lines.get(i).split(","));
			int attributeIndex;

			if (isNumeric(splitList.get(irisIndex)) && iris.contains(Integer.parseInt(splitList.get(irisIndex)))) {
				System.out.println("IRIS IN " + splitList.get(irisIndex));

				stdout = "";
				for (int j = 0; j < splitList.size(); j++) {
					attributeIndex = attributeIndexs.get(j);
					if (attributeIndex != -1) {
						stdout = stdout + splitList.get(j);
						stdout = stdout + ",";
					}
				}

				out.println(stdout);
			} else {
				System.out.println("IRIS OUT " + splitList.get(irisIndex));
			}

			progressBar.update(i);
		}

		out.close();

		System.out.println("Filtering element done!");
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

	public void writeFile(String path, List<List<String>> element, String elementName) throws IOException {
		File file = new File(path);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
		String s = "";
		System.out.println("Writing "+elementName);
		ProgressBar progressBar = new ProgressBar(element.get(0).size(),"Writing "+elementName);
		progressBar.setInverse(false);

		for (int i = 0; i < element.get(0).size(); i++) {
			s = "";
			for (int j = 0; j < element.size(); j++) {
				s = s + element.get(j).get(i);
				if (j + 1 < element.size()) {
					s = s + ";";
				}
			}
			out.println(s);
			progressBar.update(i);
		}
		System.out.println();
		System.out.println("Writing "+elementName+" done!");
		System.out.println();
		out.close();
	}

	private List<String> readFile(File fin) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fin));
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}

	private int attributeIndex(String attributeName, List<List<String>> element) {
		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).get(0).matches(attributeName)) {
				return i;
			}
		}
		return -1;
	}

}
