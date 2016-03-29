package edu.pw.elka.andromote.functionalityframework.exceptions;

public class NoSuchFunctionException extends FunctionalityFrameworkException {

	public NoSuchFunctionException(String actionName) {
		super("No such function exception " + actionName); 
	}

}
