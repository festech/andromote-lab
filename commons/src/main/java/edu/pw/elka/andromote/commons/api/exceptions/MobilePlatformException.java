package edu.pw.elka.andromote.commons.api.exceptions;

/**
 * Wyjątek rzucany w przypadku wystąpienia błędu z platformą mobilną.
 * @author Maciej Gzik
 *
 */
public class MobilePlatformException extends Exception {

	private static final long serialVersionUID = 1L;

	public MobilePlatformException(String msg) {
		super(msg);
	}

}
