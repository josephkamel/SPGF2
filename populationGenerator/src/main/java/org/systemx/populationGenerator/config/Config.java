package org.systemx.populationGenerator.config;

public class Config {
	
	public int NbrThreads;
	public int Finesse;
	public String DepString;	
	public boolean AddRevenue;
	
	public String PumsPath = "";
	public String MarginalDataPath = "";
	public String CantDepRegPath = "";
	public String RevenuePath = "";
	public String PumsFilteredPath = "";
	public String OutputPath = "";
	
	public boolean MarginalErrorMenages;
	public boolean MarginalErrorHommesFemmes;
	public boolean MarginalErrorAgeGroup;
	public boolean MarginalErrorSocioProfessionalCategory;
	
	public int getNbrThreads() {
		return NbrThreads;
	}
	public void setNbrThreads(int nbrThreads) {
		NbrThreads = nbrThreads;
	}
	public int getFinesse() {
		return Finesse;
	}
	public void setFinesse(int finesse) {
		Finesse = finesse;
	}
	public String getDepString() {
		return DepString;
	}
	public void setDepString(String depString) {
		DepString = depString;
	}
	public boolean isAddRevenue() {
		return AddRevenue;
	}
	public void setAddRevenue(boolean addRevenue) {
		AddRevenue = addRevenue;
	}
	public String getPumsPath() {
		return PumsPath;
	}
	public void setPumsPath(String pumsPath) {
		PumsPath = pumsPath;
	}
	public String getMarginalDataPath() {
		return MarginalDataPath;
	}
	public void setMarginalDataPath(String marginalDataPath) {
		MarginalDataPath = marginalDataPath;
	}
	public String getCantDepRegPath() {
		return CantDepRegPath;
	}
	public void setCantDepRegPath(String cantDepRegPath) {
		CantDepRegPath = cantDepRegPath;
	}
	public String getRevenuePath() {
		return RevenuePath;
	}
	public void setRevenuePath(String revenuePath) {
		RevenuePath = revenuePath;
	}
	public String getPumsFilteredPath() {
		return PumsFilteredPath;
	}
	public void setPumsFilteredPath(String pumsFilteredPath) {
		PumsFilteredPath = pumsFilteredPath;
	}
	public String getOutputPath() {
		return OutputPath;
	}
	public void setOutputPath(String outputPath) {
		OutputPath = outputPath;
	}
	public boolean isMarginalErrorMenages() {
		return MarginalErrorMenages;
	}
	public void setMarginalErrorMenages(boolean marginalErrorMenages) {
		MarginalErrorMenages = marginalErrorMenages;
	}
	public boolean isMarginalErrorHommesFemmes() {
		return MarginalErrorHommesFemmes;
	}
	public void setMarginalErrorHommesFemmes(boolean marginalErrorHommesFemmes) {
		MarginalErrorHommesFemmes = marginalErrorHommesFemmes;
	}
	public boolean isMarginalErrorAgeGroup() {
		return MarginalErrorAgeGroup;
	}
	public void setMarginalErrorAgeGroup(boolean marginalErrorAgeGroup) {
		MarginalErrorAgeGroup = marginalErrorAgeGroup;
	}
	public boolean isMarginalErrorSocioProfessionalCategory() {
		return MarginalErrorSocioProfessionalCategory;
	}
	public void setMarginalErrorSocioProfessionalCategory(boolean marginalErrorSocioProfessionalCategory) {
		MarginalErrorSocioProfessionalCategory = marginalErrorSocioProfessionalCategory;
	}

}
