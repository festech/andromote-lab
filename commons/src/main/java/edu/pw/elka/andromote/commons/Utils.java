package edu.pw.elka.andromote.commons;

/**
 * Klasa pomocnicza udostępniająca funkcje obliczniowe, informacyjne oraz
 * diagnostyczne na temat maszyny wirtualnej i systemu Android.
 * 
 * @author Maciej Gzik
 * 
 */

public class Utils {
	/**
	 * Zaokrąglanie doubla do dwóch liczb po przecinku.
	 * 
	 * @param doubleToRound
	 *            Liczba do zaokrąglenia.
	 * @return wynik zaokrąglenia
	 */
	public static double roundDouble(double doubleToRound) {
		return (double) Math.round(doubleToRound * 100) / 100;
	}

	/**
	 * Funkcja zwraca rozmiar obiektów znajdujących się na stercie.
	 * 
	 * @return rozmiar obiektów na stercie
	 */
	public static long getHeapSize() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Funkcja zwraca maksymalny rozmiar pamięci przydzielony uruchomionemu
	 * programowi w systemie.
	 * 
	 * @return maksymalny rozmiar pamięci jaki może wykorzystać program w
	 *         systemie, w którym pracuje
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * Funkcja zwraca rozmiar dostępnej pamięci.
	 * 
	 * @return Rozmiar wolnej pamięci
	 */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

}
