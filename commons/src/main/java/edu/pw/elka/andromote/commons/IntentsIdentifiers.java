package edu.pw.elka.andromote.commons;

/**
 * Klasa definiująca identyfikatory obiektów Intent przesyłanych pomiędzy
 * modułami apliakcji AndroMote - każda zmienna to pole z getAction() intencji.
 * 
 * 
 * @author Maciej Gzik
 * 
 */
public class IntentsIdentifiers {

	/**
	 * Identyfikator obiektów Intent rozsyłanych przez serwis komunikacji z
	 * serwerem po odebraniu pakietu z niego. Obiekt Intent zawiera pole
	 * dodatkowe, które jest odebranym pakietem.
	 */
	public static String ACTION_ANDROID_CLIENT_PACKET_RECEIVED = "andro_mote.server_service.packet_received";

	/**
	 * Identyfikator intencji startującej serwis dla komunikacji z serwerem. Nie
	 * zawiera pól dodatkowych.
	 */
	public static String ACTION_START_CLIENT_SERVICE = "andro_mote.server_service.start_client_service";

	/**
	 * Identyfikator akcji wykonanej przez serwis połączenia z serwerem np. o
	 * utracie połączenia z serwerem.
	 */
	public static String ACTION_CLIENT_SERVICE = "andro_mote.server_service.client_service";

	/**
	 * Identyfikator akcji odbieranych przez serwis klienta. Aby przekazać
	 * polecenie do serwisu połączenia z serwerem należy wywołać funkcję
	 * android.content.ContextWrapper.sendBroadcast(Intent intent). Jako
	 * parametr należy przekazać obiekt Intent z uzupełnionym wybranym
	 * identyfiaktorem polem action.
	 */
	public static String ACTION_CLIENT_SERVICE_RECEIVER = "andro_mote.server_service.client_service_receiver";

	/**
	 * Identyfikator intencji startującej serwis kontroli silników. Nie zawiera
	 * pól dodatkowych.
	 */
	public static String ACTION_ENGINES_CONTROLLER = "edu.pw.elka.andromote.hardwareapi.ioio_service.IOIOLooperManagerService";

	/**
	 * Identyfikator intencji broadcastowych odbieranych przez sterownik
	 * urządzenia zawnętrznego.. Intencja zaiwiera pole z przesyłanym pakietem.
	 */
	public static String ACTION_MESSAGE_TO_CONTROLLER = "andro_mote.message_to_controller";

	/**
	 * Identyfikator intencji wykonanego przez platformę mobilną kroku. Intencja
	 * zawiera informacje o wykonanym kroku. Zawartość: Pakiet z informacjami o
	 * wykonanym kroku.
	 */
	public static String ACTION_ENGINE_STEP = "andro_mote.engine_service.engine_step";

	/**
	 * Identyfikator intencji akcji wykonanej przez urządzenie zewnęttrzne np.
	 * platformę mobilną.
	 */
	public static String ACTION_MESSAGE_FROM_DEVICE = "andro_mote.action_from_device";

	/**
	 * Start serwisu obsługi połączenia BT.
	 */
	public static String ACTION_START_BT_SERVICE = "andro_mote.bluetooth.service_start";

	/**
	 * Nazwa intecji odbieranych przez serwis BT.
	 */
	public static String ACTION_BT_SERVICE_RECEIVER = "andro_mote.bluetooth.service_receiver";

	/**
	 * Nazwa intecji wysyłanych przez serwis BT.
	 */
	public static String ACTION_BT_SERVICE_ACTION = "andro_mote.bluetooth.service_action";

}
