package org.systemx.elements;

import java.util.ArrayList;
import java.util.List;

public class DepCantonIrisElement {
	
	private List<List<String>> element = new ArrayList<List<String>>();

	private List<String> insee;
	private List<String> code_cant;
	private List<String> code_dept;
	private List<String> code_reg;
	
	
	public DepCantonIrisElement() {
		super();
		element = new ArrayList<List<String>>();
		
		insee = new ArrayList<String>();
		code_cant = new ArrayList<String>();
		code_dept = new ArrayList<String>();
		code_reg = new ArrayList<String>();

		insee.add("insee");
		code_cant.add("code_cant");
		code_dept.add("code_dept");
		code_reg.add("code_reg");
		
		element.add(insee);
		element.add(code_cant);
		element.add(code_dept);
		element.add(code_reg);
	}


	public List<String> getInsee() {
		return insee;
	}


	public void setInsee(List<String> insee) {
		this.insee = insee;
	}


	public List<String> getCode_cant() {
		return code_cant;
	}


	public void setCode_cant(List<String> code_cant) {
		this.code_cant = code_cant;
	}


	public List<String> getCode_dept() {
		return code_dept;
	}


	public void setCode_dept(List<String> code_dept) {
		this.code_dept = code_dept;
	}


	public List<String> getCode_reg() {
		return code_reg;
	}


	public void setCode_reg(List<String> code_reg) {
		this.code_reg = code_reg;
	}


	public List<List<String>> getElement() {
		return element;
	}


	public void setElement(List<List<String>> element) {
		this.element = element;
	}


	public void println(int linesNumber) {
		for (int i = 0; i < getElement().size(); i++) {
			System.out.println(getElement().get(i).get(0) + ":" + getElement().get(i).size());
		}

		if(linesNumber>getElement().get(0).size()){
			linesNumber = getElement().get(0).size();
		}
		
		for (int j = 0; j < linesNumber; j++) {
			for (int i = 0; i < getElement().size(); i++) {
				System.out.print(getElement().get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
	
}
