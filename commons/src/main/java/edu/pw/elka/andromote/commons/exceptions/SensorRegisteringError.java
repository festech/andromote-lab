package edu.pw.elka.andromote.commons.exceptions;

/**
 * Błąd rzucany po nieprawidłowej rejestracji sensorów urządzenia. Przykładem
 * takiego zachowania może być próba skrętu o określoną liczbę stopni, jeżeli
 * urządzenie nie posiada modułu pozwalającego na określenie aktualnej pozycji
 * telefonu.
 * 
 * @author Maciej Gzik
 * 
 */
public class SensorRegisteringError extends Exception {

	private static final long serialVersionUID = 1L;

}
