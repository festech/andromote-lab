package edu.pw.elka.andromote.andromotelogger;

import org.apache.log4j.Logger;

/**
 * Działanie: 1. Utworzenie obiektu AndroMoteLogger 2. wywołanie funkcji
 * ConfigureLogger 3. debug, error logging
 * 
 * @author Maciej Gzik
 * 
 */
public class AndroMoteLogger {
	private Logger logger = null;

	public AndroMoteLogger(Class clazz) {
		this.logger = Logger.getLogger(clazz);
	}

	/**
	 * Konfiguracja loggera.
	 * 
	 * @param logFileName
	 *            nazwa pliku z logami
	 * @param sysLogServerAddress
	 * 			  adres IP serwera sysLog na który mają być przesyłane logi. 
	 * 			  Format adresu: "adresIP:port"
	 * 			  Jeśli nie ustawiony - logi nie będą wysyłane na serwer sysLog 
	 **/
	public static void ConfigureLogger(String logFileName, String sysLogServerAddress) {
		ConfigureLog4J.configure(logFileName, sysLogServerAddress);
	}

	/**
	 * Debug do logCat i log4j
	 * 
	 * @param TAG
	 *            tag (LogCat)
	 * @param message
	 *            wiadomość
	 */
	public void debug(String TAG, String message) {
		// Log.d(TAG, message);
		logger.debug(message);

	}

	/**
	 * log error
	 * 
	 * @param TAG
	 *            tag
	 * @param e
	 *            błąd
	 */
	public void error(String TAG, Throwable e) {
		logger.error(e.getMessage(), e);
		// Log.e(TAG, e.getMessage(), e);
	}
	
	/**
	 * log error
	 * 
	 * @param TAG
	 *            tag (LogCat)
	 * @param message
	 *            wiadomość
	 */
	public void error(String TAG, String message) {
		logger.error(message);
		// Log.e(TAG, e.getMessage(), e);
	}
}
