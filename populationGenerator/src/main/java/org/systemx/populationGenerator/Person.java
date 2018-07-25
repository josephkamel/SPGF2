package org.systemx.populationGenerator;

import java.util.ArrayList;
import java.util.List;

import org.systemx.elements.PUMSElement;

public class Person {

	private String CANTVILLE;
	private String NUMMI;
	private String AGED;
	private String COUPLE;
	private String CS1;
	private String DEPT;
	private String DIPL_15;
	private String EMPL;
	private String ETUD;
	private String GARL;
	private String ILETUD;
	private String ILT;
	private String INFAM;
	private String INPER;
	private String IPONDI;
	private String IRIS;
	private String NA17;
	private String NBPI;
	private String NE18FR;
	private String NE6FR;
	private String NENFR;
	private String NPERR;
	private String NUMF;
	private String SEXE;
	private String STATR;
	private String TACT;
	private String TP;
	private String TRANS;
	private String VOIT;

	private int REVENUE = 0;

	public Person() {
		super();
	}

	public Person(Person person) {
		super();
		CANTVILLE = person.getCANTVILLE();
		NUMMI = person.getNUMMI();
		AGED = person.getAGED();
		COUPLE = person.getCOUPLE();
		CS1 = person.getCS1();
		DEPT = person.getDEPT();
		DIPL_15 = person.getDIPL_15();
		EMPL = person.getEMPL();
		ETUD = person.getETUD();
		GARL = person.getGARL();
		ILETUD = person.getILETUD();
		ILT = person.getILT();
		INFAM = person.getINFAM();
		INPER = person.getINPER();
		IPONDI = person.getIPONDI();
		IRIS = person.getIRIS();
		NA17 = person.getNA17();
		NBPI = person.getNBPI();
		NE18FR = person.getNE18FR();
		NE6FR = person.getNE6FR();
		NENFR = person.getNENFR();
		NPERR = person.getNPERR();
		NUMF = person.getNUMF();
		SEXE = person.getSEXE();
		STATR = person.getSTATR();
		TACT = person.getTACT();
		TP = person.getTP();
		TRANS = person.getTRANS();
		VOIT = person.getVOIT();
		REVENUE = person.getREVENUE();
	}

	public List<String> serialisePerson() {
		List<String> element = new ArrayList<String>();
		
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
		
		element.add(String.valueOf(REVENUE));
		return element;

	}

	void parseElement(PUMSElement pums, int index) {
		CANTVILLE = pums.getCANTVILLE().get(index);
		NUMMI = pums.getNUMMI().get(index);
		AGED = pums.getAGED().get(index);
		COUPLE = pums.getCOUPLE().get(index);
		CS1 = pums.getCS1().get(index);
		DEPT = pums.getDEPT().get(index);
		DIPL_15 = pums.getDIPL_15().get(index);
		EMPL = pums.getEMPL().get(index);
		ETUD = pums.getETUD().get(index);
		GARL = pums.getGARL().get(index);
		ILETUD = pums.getILETUD().get(index);
		ILT = pums.getILT().get(index);
		INFAM = pums.getINFAM().get(index);
		INPER = pums.getINPER().get(index);
		IPONDI = pums.getIPONDI().get(index);
		IRIS = pums.getIRIS().get(index);
		NA17 = pums.getNA17().get(index);
		NBPI = pums.getNBPI().get(index);
		NE18FR = pums.getNE18FR().get(index);
		NE6FR = pums.getNE6FR().get(index);
		NENFR = pums.getNENFR().get(index);
		NPERR = pums.getNPERR().get(index);
		NUMF = pums.getNUMF().get(index);
		SEXE = pums.getSEXE().get(index);
		STATR = pums.getSTATR().get(index);
		TACT = pums.getTACT().get(index);
		TP = pums.getTP().get(index);
		TRANS = pums.getTRANS().get(index);
		VOIT = pums.getVOIT().get(index);
	}

	@Override
	public String toString() {
		return "Person [SEXE=" + SEXE + ", AGED=" + AGED + ", EMPL=" + EMPL + ", NUMMI=" + NUMMI + ", IRIS=" + IRIS
				+ "]";
	}

	public String getCANTVILLE() {
		return CANTVILLE;
	}

	public void setCANTVILLE(String cANTVILLE) {
		CANTVILLE = cANTVILLE;
	}

	public String getNUMMI() {
		return NUMMI;
	}

	public void setNUMMI(String nUMMI) {
		NUMMI = nUMMI;
	}

	public String getAGED() {
		return AGED;
	}

	public void setAGED(String aGED) {
		AGED = aGED;
	}

	public String getCOUPLE() {
		return COUPLE;
	}

	public void setCOUPLE(String cOUPLE) {
		COUPLE = cOUPLE;
	}

	public String getCS1() {
		return CS1;
	}

	public void setCS1(String cS1) {
		CS1 = cS1;
	}

	public String getDEPT() {
		return DEPT;
	}

	public void setDEPT(String dEPT) {
		DEPT = dEPT;
	}

	public String getDIPL_15() {
		return DIPL_15;
	}

	public void setDIPL_15(String dIPL_15) {
		DIPL_15 = dIPL_15;
	}

	public String getEMPL() {
		return EMPL;
	}

	public void setEMPL(String eMPL) {
		EMPL = eMPL;
	}

	public String getETUD() {
		return ETUD;
	}

	public void setETUD(String eTUD) {
		ETUD = eTUD;
	}

	public String getGARL() {
		return GARL;
	}

	public void setGARL(String gARL) {
		GARL = gARL;
	}

	public String getILETUD() {
		return ILETUD;
	}

	public void setILETUD(String iLETUD) {
		ILETUD = iLETUD;
	}

	public String getILT() {
		return ILT;
	}

	public void setILT(String iLT) {
		ILT = iLT;
	}

	public String getINFAM() {
		return INFAM;
	}

	public void setINFAM(String iNFAM) {
		INFAM = iNFAM;
	}

	public String getINPER() {
		return INPER;
	}

	public void setINPER(String iNPER) {
		INPER = iNPER;
	}

	public String getIPONDI() {
		return IPONDI;
	}

	public void setIPONDI(String iPONDI) {
		IPONDI = iPONDI;
	}

	public String getIRIS() {
		return IRIS;
	}

	public void setIRIS(String iRIS) {
		IRIS = iRIS;
	}

	public String getNA17() {
		return NA17;
	}

	public void setNA17(String nA17) {
		NA17 = nA17;
	}

	public String getNBPI() {
		return NBPI;
	}

	public void setNBPI(String nBPI) {
		NBPI = nBPI;
	}

	public String getNE18FR() {
		return NE18FR;
	}

	public void setNE18FR(String nE18FR) {
		NE18FR = nE18FR;
	}

	public String getNE6FR() {
		return NE6FR;
	}

	public void setNE6FR(String nE6FR) {
		NE6FR = nE6FR;
	}

	public String getNENFR() {
		return NENFR;
	}

	public void setNENFR(String nENFR) {
		NENFR = nENFR;
	}

	public String getNPERR() {
		return NPERR;
	}

	public void setNPERR(String nPERR) {
		NPERR = nPERR;
	}

	public String getNUMF() {
		return NUMF;
	}

	public void setNUMF(String nUMF) {
		NUMF = nUMF;
	}

	public String getSEXE() {
		return SEXE;
	}

	public void setSEXE(String sEXE) {
		SEXE = sEXE;
	}

	public String getSTATR() {
		return STATR;
	}

	public void setSTATR(String sTATR) {
		STATR = sTATR;
	}

	public String getTACT() {
		return TACT;
	}

	public void setTACT(String tACT) {
		TACT = tACT;
	}

	public String getTP() {
		return TP;
	}

	public void setTP(String tP) {
		TP = tP;
	}

	public String getTRANS() {
		return TRANS;
	}

	public void setTRANS(String tRANS) {
		TRANS = tRANS;
	}

	public String getVOIT() {
		return VOIT;
	}

	public void setVOIT(String vOIT) {
		VOIT = vOIT;
	}

	public int getREVENUE() {
		return REVENUE;
	}

	public void setREVENUE(int rEVENUE) {
		REVENUE = rEVENUE;
	}

	

}
