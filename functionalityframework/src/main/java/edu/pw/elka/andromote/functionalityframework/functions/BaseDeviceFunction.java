package edu.pw.elka.andromote.functionalityframework.functions;

import android.content.Context;

/**
 * Klasa abstrakcyjna, która służy do definiowania funkcji opartych na wykorzystywaniu urządzeń zewnętrznych
 * lub możliwości urządzenia Android
 * @author Sebastian Luczak
 *
 */
public abstract class BaseDeviceFunction extends BaseFunction {	
	public BaseDeviceFunction(Context context) {
		super(context);
	}
	
	@Override
	public void cleanup() {
		logger.info("Cleanning up");
	}	
}
