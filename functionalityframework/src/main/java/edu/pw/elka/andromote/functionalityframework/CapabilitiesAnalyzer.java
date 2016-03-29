package edu.pw.elka.andromote.functionalityframework;



public interface CapabilitiesAnalyzer {
	
	public boolean hasFeature(String feature);

	public void checkCurrentCapabilities();

	public String getAvailableFeatures();
}