package edu.pw.elka.andromote.functionalityframework;

import android.app.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import edu.pw.elka.andromote.commons.hardware.devices.ElectronicDeviceFactory;
import edu.pw.elka.andromote.functionalityframework.exceptions.NoSuchFeatureException;
import edu.pw.elka.andromote.functionalityframework.exceptions.NoSuchFunctionException;
import edu.pw.elka.andromote.functionalityframework.functions.Function;
import edu.pw.elka.andromote.functionalityframework.functions.FunctionFactory;
import edu.pw.elka.andromote.hardwareapi.ElectronicsController;

public enum FunctionManager {
	INSTANCE;
	private final Logger log = LoggerFactory.getLogger(FunctionManager.class);
	private final Map<String, Function> reservedActions = new HashMap<String, Function>();
	private FunctionFactory functionFactory;
	private CapabilitiesAnalyzer capabilitiesAnalyzer;

	/**
	 *
	 * Inicjalizuje singleton FunctionManager. Dostarcza do warstwy funkcjonalności niezbędną fabrykę konkretną funkcji,
	 * obiekt odpowiedzialny za sprawdzanie dostępności funkcji w przypadku konkretnego robota AM
	 * i fabrykę konkretną urządzeń zewnętrznych.   
	 * Metoda musi zostać wywołana przed rozpoczęciem korzystania ze szkieletu AndroMote2
	 *
	 * @param application
	 * @param capabilitiesAnalyzer
	 * @param functionFactory
	 * @param deviceFactory
	 */
	public void init(Application application, CapabilitiesAnalyzer capabilitiesAnalyzer, FunctionFactory functionFactory, 
			ElectronicDeviceFactory deviceFactory) {
		ElectronicsController.INSTANCE.init(application, deviceFactory);
		this.functionFactory = functionFactory;
		this.capabilitiesAnalyzer = capabilitiesAnalyzer;
		capabilitiesAnalyzer.checkCurrentCapabilities();
	}
	
	public void onDestroy() {
		ElectronicsController.INSTANCE.destroy();
	}
	
	/**
	 * Przygotowuje {@link FunctionManager} na potrzeby przetwarzania skryptu
	 * Metoda powinna zostać uruchomiona na początku pzetwarzania skryptu 
	 */
	public void prepare() {
		capabilitiesAnalyzer.checkCurrentCapabilities();
		log.info(capabilitiesAnalyzer.getAvailableFeatures());
	}
	
	/**
	 * Sprząta po przetworzeniu skryptu
	 * Metoda powinna zostać uruchomiona na końcu pzetwarzania skryptu 
	 */
	public void cleanup() {
		for (Map.Entry<String, Function> actionEntry : reservedActions.entrySet()) {
			actionEntry.getValue().cleanup();
		}
		reservedActions.clear();
	}
	
	/**
	 * Zwraca akcję danego typu i dodaje ją do mapy akcji z kluczem varName
	 * @param feature typ akcji
	 * @param varName nazwa zmiennej androCode
	 * @return skonfigurowana akcja lub null jeśli akcja danego typu nie jest dostępna na urządzeniu 
	 * @throws NoSuchFeatureException 
	 */
	public Function get(String feature, String varName) throws NoSuchFeatureException {
		Function function = null;
		if(capabilitiesAnalyzer.hasFeature(feature)) {
			function = functionFactory.create(feature);
			if(reservedActions.containsKey(varName)) {
				Function oldAction = reservedActions.get(varName);
				oldAction.cleanup();
			}
			reservedActions.put(varName, function);
		} else {
			throw new NoSuchFeatureException(feature.toString());
		}
		return function;
	}
	
	public void setParam(String varName, String propertyName, String value) throws NoSuchFunctionException {
		Function action = reservedActions.get(varName);
		if(action == null) {
			throw new NoSuchFunctionException(varName);
		}
		action.putParam(propertyName, value);
	}
	
	public Object execute(String varName) throws NoSuchFunctionException {
		Object result = null;
		Function action = reservedActions.get(varName);
		if(action == null) {
			throw new NoSuchFunctionException(varName);
		}
		log.debug("Running action " + action.getClass().getSimpleName());
		result = action.run();
		log.debug("Result: " + result);			
		log.info("Action is done");
		return result;
	}

	public void release(String varName) throws NoSuchFunctionException {
		Function action = reservedActions.get(varName);
		if(action == null) {
			throw new NoSuchFunctionException(varName);
		}
		log.debug("Releasing action " + action.getClass().getSimpleName());
		action.cleanup();
		reservedActions.remove(varName);
		log.debug("Action released.");
	}
}
