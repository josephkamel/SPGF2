package org.systemx.elements;

import java.util.ArrayList;
import java.util.List;

import org.systemx.populationGenerator.supportClasses.ProgressBar;

public class PUMSElement {

	private List<List<String>> element;

	private List<String> CANTVILLE;
	private List<String> NUMMI;
	private List<String> AGED;
	private List<String> COUPLE;
	private List<String> CS1;
	private List<String> DEPT;
	private List<String> DIPL_15;
	private List<String> EMPL;
	private List<String> ETUD;
	private List<String> GARL;
	private List<String> ILETUD;
	private List<String> ILT;
	private List<String> INFAM;
	private List<String> INPER;
	private List<String> IPONDI;
	private List<String> IRIS;
	private List<String> NA17;
	private List<String> NBPI;
	private List<String> NE18FR;
	private List<String> NE6FR;
	private List<String> NENFR;
	private List<String> NPERR;
	private List<String> NUMF;
	private List<String> SEXE;
	private List<String> STATR;
	private List<String> TACT;
	private List<String> TP;
	private List<String> TRANS;
	private List<String> VOIT;
	private List<String> REVENUE;

	public PUMSElement() {
		super();
		
		element = new ArrayList<List<String>>();
		
		CANTVILLE = new ArrayList<String>();
		NUMMI = new ArrayList<String>();
		AGED = new ArrayList<String>();
		COUPLE = new ArrayList<String>();
		CS1 = new ArrayList<String>();
		DEPT = new ArrayList<String>();
		DIPL_15 = new ArrayList<String>();
		EMPL = new ArrayList<String>();
		ETUD = new ArrayList<String>();
		GARL = new ArrayList<String>();
		ILETUD = new ArrayList<String>();
		ILT = new ArrayList<String>();
		INFAM = new ArrayList<String>();
		INPER = new ArrayList<String>();
		IPONDI = new ArrayList<String>();
		IRIS = new ArrayList<String>();
		NA17 = new ArrayList<String>();
		NBPI = new ArrayList<String>();
		NE18FR = new ArrayList<String>();
		NE6FR = new ArrayList<String>();
		NENFR = new ArrayList<String>();
		NPERR = new ArrayList<String>();
		NUMF = new ArrayList<String>();
		SEXE = new ArrayList<String>();
		STATR = new ArrayList<String>();
		TACT = new ArrayList<String>();
		TP = new ArrayList<String>();
		TRANS = new ArrayList<String>();
		VOIT = new ArrayList<String>();
		REVENUE = new ArrayList<String>();

		CANTVILLE.add("CANTVILLE");
		NUMMI.add("NUMMI");
		AGED.add("AGED");
		COUPLE.add("COUPLE");
		CS1.add("CS1");
		DEPT.add("DEPT");
		DIPL_15.add("DIPL_15");
		EMPL.add("EMPL");
		ETUD.add("ETUD");
		GARL.add("GARL");
		ILETUD.add("ILETUD");
		ILT.add("ILT");
		INFAM.add("INFAM");
		INPER.add("INPER");
		IPONDI.add("IPONDI");
		IRIS.add("IRIS");
		NA17.add("NA17");
		NBPI.add("NBPI");
		NE18FR.add("NE18FR");
		NE6FR.add("NE6FR");
		NENFR.add("NENFR");
		NPERR.add("NPERR");
		NUMF.add("NUMF");
		SEXE.add("SEXE");
		STATR.add("STATR");
		TACT.add("TACT");
		TP.add("TP");
		TRANS.add("TRANS");
		VOIT.add("VOIT");
		REVENUE.add("REVENUE");

		element.add(CANTVILLE);
		element.add(NUMMI);
		element.add(AGED);
		element.add(COUPLE);
		element.add(CS1);
		element.add(DEPT);
		element.add(DIPL_15);
		element.add(EMPL);
		element.add(ETUD);
		element.add(GARL);
		element.add(ILETUD);
		element.add(ILT);
		element.add(INFAM);
		element.add(INPER);
		element.add(IPONDI);
		element.add(IRIS);
		element.add(NA17);
		element.add(NBPI);
		element.add(NE18FR);
		element.add(NE6FR);
		element.add(NENFR);
		element.add(NPERR);
		element.add(NUMF);
		element.add(SEXE);
		element.add(STATR);
		element.add(TACT);
		element.add(TP);
		element.add(TRANS);
		element.add(VOIT);
		element.add(REVENUE);
	}

	public PUMSElement(PUMSElement pumsElement) {
		super();
		element.clear();
		element.addAll(pumsElement.element);
	}
	
	public void cleanList() {
		int removed = 0;
		System.out.println("Cleaning Pums "+ NUMMI.size() +" List:");
		ProgressBar pb = new ProgressBar(NUMMI.size(),"Cleaning Pums "+ NUMMI.size() +" List:");

		PUMSElement pumsElementTemp = new PUMSElement();

		for (int i = NUMMI.size()-1; i >= 0; i--) {
				if(isNumeric(NUMMI.get(i))){
					pumsElementTemp.addOneElement(element, i);
				}else{
					removed ++;
				}
			pb.update(i);
		}

		for (int i = 0; i < element.size(); i++) {
			element.get(i).clear();
		}

		for (int i = 0; i < element.size(); i++) {
			element.get(i).addAll(pumsElementTemp.getElement().get(i));
		}

		System.out.println();
		System.out.println("Removed Pums elements:"+ removed);
		System.out.println();
	}
	
	public void recycleList() {
		int removed = 0;
		System.out.println("Cleaning Pums "+ NUMMI.size() +" List:");
		ProgressBar pb = new ProgressBar(NUMMI.size(),"Cleaning Pums "+ NUMMI.size() +" List:");

		double largestNummi = 0;
		for (int i = NUMMI.size()-1; i >= 0; i--) {
			if(isNumeric(NUMMI.get(i))){
				if(Double.parseDouble(NUMMI.get(i)) >largestNummi){
					largestNummi = Double.parseDouble(NUMMI.get(i));
				}
			}
		}
		
		PUMSElement pumsElementTemp = new PUMSElement();
		for (int i = NUMMI.size()-1; i >= 0; i--) {
				if(isNumeric(NUMMI.get(i))){
					pumsElementTemp.addOneElement(element, i);
				}else{
					element.get(i).set(1, "999999"+(largestNummi+1));
					largestNummi = largestNummi+1;
				}
			pb.update(i);
		}

		for (int i = 0; i < element.size(); i++) {
			element.get(i).clear();
		}

		for (int i = 0; i < element.size(); i++) {
			element.get(i).addAll(pumsElementTemp.getElement().get(i));
		}

		System.out.println();
		System.out.println("Removed Pums elements:"+ removed);
		System.out.println();
	}
	
	public void removeNotInDep(List<Integer> dep) {
		int removed = 0;
		System.out.println("Removing "+element.get(0).size()+" pums not in dep started:");
		ProgressBar pb = new ProgressBar(element.get(0).size()-1, "Removing "+element.get(0).size()+" pums not in dep started:");
		for (int i = element.get(0).size() - 1; i >= 0; i--) {
				if(!dep.contains(Integer.parseInt(IRIS.get(i).substring(0, 2)))){
					removeElement(i);
					removed ++;
				}
				pb.update(i);
		}
		System.out.println("Removed elements:"+ removed);
	}
	
	
	public void removeElement(int index) {
		if (element.get(0).size() > index) {
			for (int i = 0; i < element.size()-1; i++) {
				element.get(i).remove(index);
			}
		}
	}

	private void addOneElement(List<List<String>> element,int index) {
		for (int i = 0; i < element.size()-1; i++) {
			this.element.get(i).add(element.get(i).get(index));
		}
	}
	
	public void addOneElement(List<String> oneElement) {
		for (int i = 0; i < element.size(); i++) {
			element.get(i).add(oneElement.get(i));
		}
	}

	//test
	public void printLines(int numberStart,int numberEnd){	
		if(numberStart>element.get(0).size()){
			numberStart = element.get(0).size();
		}
		
		if(numberEnd>element.get(0).size()){
			numberEnd = element.get(0).size();
		}
		
		for (int i = 0; i < element.size(); i++) {
			System.out.println(element.get(i).get(0)+ ":"+ element.get(i).size());
		}
		
		for (int j = numberStart; j < numberEnd; j++) {
			for (int i = 0; i < element.size()-1; i++) {
				System.out.print(element.get(i).get(j)+"	");
			}
			System.out.println();
		}
	}

	public List<String> getOneElement(int index) {
		List<String> oneElement = new ArrayList<String>();
		for (int i = 0; i < element.size(); i++) {
			oneElement.add(element.get(i).get(index));
		}
		return oneElement;
	}

	public List<List<String>> getElement() {
		return element;
	}

	public void setElement(List<List<String>> element) {
		this.element = element;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public List<String> getCANTVILLE() {
		return CANTVILLE;
	}

	public void setCANTVILLE(List<String> cANTVILLE) {
		CANTVILLE = cANTVILLE;
	}

	public List<String> getNUMMI() {
		return NUMMI;
	}

	public void setNUMMI(List<String> nUMMI) {
		NUMMI = nUMMI;
	}

	public List<String> getAGED() {
		return AGED;
	}

	public void setAGED(List<String> aGED) {
		AGED = aGED;
	}

	public List<String> getCOUPLE() {
		return COUPLE;
	}

	public void setCOUPLE(List<String> cOUPLE) {
		COUPLE = cOUPLE;
	}

	public List<String> getCS1() {
		return CS1;
	}

	public void setCS1(List<String> cS1) {
		CS1 = cS1;
	}

	public List<String> getDEPT() {
		return DEPT;
	}

	public void setDEPT(List<String> dEPT) {
		DEPT = dEPT;
	}

	public List<String> getDIPL_15() {
		return DIPL_15;
	}

	public void setDIPL_15(List<String> dIPL_15) {
		DIPL_15 = dIPL_15;
	}

	public List<String> getEMPL() {
		return EMPL;
	}

	public void setEMPL(List<String> eMPL) {
		EMPL = eMPL;
	}

	public List<String> getETUD() {
		return ETUD;
	}

	public void setETUD(List<String> eTUD) {
		ETUD = eTUD;
	}

	public List<String> getGARL() {
		return GARL;
	}

	public void setGARL(List<String> gARL) {
		GARL = gARL;
	}

	public List<String> getILETUD() {
		return ILETUD;
	}

	public void setILETUD(List<String> iLETUD) {
		ILETUD = iLETUD;
	}

	public List<String> getILT() {
		return ILT;
	}

	public void setILT(List<String> iLT) {
		ILT = iLT;
	}

	public List<String> getINFAM() {
		return INFAM;
	}

	public void setINFAM(List<String> iNFAM) {
		INFAM = iNFAM;
	}

	public List<String> getINPER() {
		return INPER;
	}

	public void setINPER(List<String> iNPER) {
		INPER = iNPER;
	}

	public List<String> getIPONDI() {
		return IPONDI;
	}

	public void setIPONDI(List<String> iPONDI) {
		IPONDI = iPONDI;
	}

	public List<String> getIRIS() {
		return IRIS;
	}

	public void setIRIS(List<String> iRIS) {
		IRIS = iRIS;
	}

	public List<String> getNA17() {
		return NA17;
	}

	public void setNA17(List<String> nA17) {
		NA17 = nA17;
	}

	public List<String> getNBPI() {
		return NBPI;
	}

	public void setNBPI(List<String> nBPI) {
		NBPI = nBPI;
	}

	public List<String> getNE18FR() {
		return NE18FR;
	}

	public void setNE18FR(List<String> nE18FR) {
		NE18FR = nE18FR;
	}

	public List<String> getNE6FR() {
		return NE6FR;
	}

	public void setNE6FR(List<String> nE6FR) {
		NE6FR = nE6FR;
	}

	public List<String> getNENFR() {
		return NENFR;
	}

	public void setNENFR(List<String> nENFR) {
		NENFR = nENFR;
	}

	public List<String> getNPERR() {
		return NPERR;
	}

	public void setNPERR(List<String> nPERR) {
		NPERR = nPERR;
	}

	public List<String> getNUMF() {
		return NUMF;
	}

	public void setNUMF(List<String> nUMF) {
		NUMF = nUMF;
	}

	public List<String> getSEXE() {
		return SEXE;
	}

	public void setSEXE(List<String> sEXE) {
		SEXE = sEXE;
	}

	public List<String> getSTATR() {
		return STATR;
	}

	public void setSTATR(List<String> sTATR) {
		STATR = sTATR;
	}

	public List<String> getTACT() {
		return TACT;
	}

	public void setTACT(List<String> tACT) {
		TACT = tACT;
	}

	public List<String> getTP() {
		return TP;
	}

	public void setTP(List<String> tP) {
		TP = tP;
	}

	public List<String> getTRANS() {
		return TRANS;
	}

	public void setTRANS(List<String> tRANS) {
		TRANS = tRANS;
	}

	public List<String> getVOIT() {
		return VOIT;
	}

	public void setVOIT(List<String> vOIT) {
		VOIT = vOIT;
	}

	public List<String> getREVENUE() {
		return REVENUE;
	}

	public void setREVENUE(List<String> rEVENUE) {
		REVENUE = rEVENUE;
	}
	
	
}
