package edu.pw.elka.andromote.functionalityframework.functions;

public interface Function {
	/**
	 * Uruchamia wykonanie danej funkcji
	 * @return wynik działania funkcji (jeśli dotyczy)
	 */
	public Object run();
	
	/**
	 * Czyści i zwalnia zasoby systemowe po zakończenu korzystania z funkcji 
	 */
	public void cleanup();
	
	/**
	 * Ustawia parametr o nazwie propertyName na wartość value
	 * @param paramName Nazwa parametru funkcji
	 * @value Wartość do ustawienia  
	 */
	public void putParam(String paramName, String value);
}
