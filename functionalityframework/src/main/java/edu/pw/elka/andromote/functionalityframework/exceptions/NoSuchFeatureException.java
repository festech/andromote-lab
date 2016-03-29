package edu.pw.elka.andromote.functionalityframework.exceptions;

public class NoSuchFeatureException extends FunctionalityFrameworkException {

	public NoSuchFeatureException(String actionName) {
		super("No such feature exception " + actionName); 
	}

}
