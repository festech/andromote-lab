package edu.pw.elka.andromote.commons.api;

import edu.pw.elka.andromote.commons.api.exceptions.MobilePlatformException;

/**
 * Interfejs opsiujący funkcje aplikacji implementującej połączenie z dowolnym
 * urządzeniem zewnętrznym z systemem Android przy wykorzystaniu zewnętrznego
 * mikrokontrolera np. IOIO.
 * 
 * @author Maciej Gzik
 * @author Sebastian Łuczak
 * 
 */
public interface IAndroMoteApi {

	/**
	 * Funkcja odpowiedzialna za rozpoczęcie komunikacji aplikacji z urządzeniem
	 * zewnętrznym o zdefiniowanej nazwie przesłanej w parametrze. W przypadku
	 * nierozpoznania nazwy obsługiwanego urządzenia rzucany zostaje wyjątek.
	 * 
	 * @param deviceName
	 *            nazwa podłączanego urządzenia. Wszystkie nazwy zostały
	 *            zdefiniowane w typie {@link MobilePlatformType} w klasie
	 *            {@link DeviceDefinitions}
	 * @return Flaga informująca o statusie wykonania połączenia.
	 * @throws {@link MobilePlatformException}
	 * @throws {@link UnknownDeviceException}
	 */
	public boolean startCommunicationWithDevice() throws MobilePlatformException;

	/**
	 * Zatrzymanie komunikacji z urządzeniem zewnętrznym.
	 * 
	 * @return Flaga informująca o statusie wykonania operacji.
	 * @throws MobilePlatformException
	 * @throws UnsupportedOperationException
	 */
	public boolean stopCommunicationWithDevice() throws MobilePlatformException, UnsupportedOperationException;

	/**
	 * Sprawdzenie stanu komunikacji z podłączonym urządzeniem.
	 * 
	 * @return Flaga statusu.
	 * @throws MobilePlatformException
	 * @throws UnsupportedOperationException
	 */
	public boolean checkIfConnectionIsActive() throws MobilePlatformException, UnsupportedOperationException;

	/**
	 * Wysłanie wiadomości do urządzenia zewnętrznego.
	 * 
	 * @param pack
	 *            Obiekt wiadomości przesyłanej do podłączonego urządzenia.
	 * @return
	 * @throws MobilePlatformException
	 * @throws UnsupportedOperationException
	 */
	public boolean sendMessageToDevice(IPacket pack) throws MobilePlatformException, UnsupportedOperationException;
}
