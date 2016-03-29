package edu.pw.elka.andromote.commons.api;


import edu.pw.elka.andromote.commons.Packet;
import edu.pw.elka.andromote.commons.api.exceptions.BroadcastReceiverClientNotSetException;

/**
 * Interfejs dla klasy odbierającej wiadomości od platformy mobilnej. Sposób
 * przekazania ich do opisywanej klasy może być różny w zależności od
 * zastosowanego rozwiązania, ale najczęściej stosowanym w projekcie AndroMote i
 * zalecanym przez autora w celu pełnej integracji z pozostałą częścią projektu
 * jest odbieranie obiektów Intent za pomocą klasy BroadcastReceivera
 * (rozszerzającego klase implementującą ten interfejs), w których umieszczone
 * są obiekty typu {@link Packet}. Z obiektu implementującego ten interfejs moga
 * korzystać klasy implementujące {@link IAndroMoteDeviceReceiverClient}
 * 
 * @author Maciej Gzik
 * 
 */
public interface IMessagesFromDeviceReceiver {
	/**
	 * Rozpoczęcie odbierania wiadomości z podłączonego urządzenia. Wiadomości
	 * są generowane przez obsługujące połączenia klasy i wysyłane w postaci
	 * obiektów Intent z pakeitami przechowującymi informacje. W tej metodzie
	 * powinien zostać zaincijalizowany BroadcastReceiver z filtrami intencji,
	 * które podłączone urządzenie może wysyłać. Programista implementujący API
	 * powinie zadbać o wybór odpowiednich filtrów do przechwytywania wiadomości
	 * w zależności od typu platformy podłączonej do telefonu. Lista
	 * identyfikatorów actionType intencji zdefiniowanych dla projektu AndroMote
	 * została zawarta w klasie: {@link IntentsIdentifiers}. Przykładem
	 * implementacji odbiornika wiadomości jest abstrakcyjna klasa
	 * {@link AndroMoteMobilePlatformApiAbstract}
	 * 
	 * @throws BroadcastReceiverClientNotSetException
	 *             Wyjątek rzucany w przypadku braku ustawienia klienta
	 *             dbierjącego wiadomości z receivera.
	 * 
	 * @return flaga informująca o tym czy udało się zainicjować receiver dla
	 *         wiadomości z podłączonego urządzenia.
	 */
	public boolean startMessagesListener() throws BroadcastReceiverClientNotSetException;

	/**
	 * Zakończenie odbierani informacji z podłączonego urzadzenia zewnętrznego.
	 * Metoda powinna być wywoływana przed końcem pracy aplikacji lub wczesniej.
	 * 
	 * @return flaga informująca o tym czy udało się wyłączyć nasłuchiwanie
	 *         wiadomości.
	 */
	public boolean stopMessagesListener() throws BroadcastReceiverClientNotSetException;

	/**
	 * Funkcja powinna zostać wywołana po odebraniu wiadomości z platformy
	 * mobilnej i należy zinterpretować w niej odebraną wiadomość.
	 * 
	 * @param pack
	 *            Odebrany od platformy mobilnej pakiet {@link Packet}.
	 * 
	 * @throws BroadcastReceiverClientNotSetException
	 *             Wyjątek rzucany w przypadku braku ustawienia klienta
	 *             dbierjącego wiadomości z receivera.
	 */
	public void mobilePlatformMessageReceived(Packet pack) throws BroadcastReceiverClientNotSetException;

}
