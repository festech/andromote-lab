package edu.pw.elka.andromote.commons;

import java.io.Serializable;

/**
 * Opis protokołu kounikacji zastosowanego w projekcie AndroMote do sterowania
 * urzadzeniem zewnętrznym.
 * 
 * @author Maciej Gzik
 * 
 */
public class PacketType {

	/**
	 * Funkcja sprawdzająca czy pakiet jest pakietem opisującym ruch.
	 *
	 * @param packetType
	 *            sprawdzany numer pakietu
	 * @return flaga informująca o tym czy pakiet opisuje ruch
	 */
	public static boolean isPacketTypeMotion(IPacketType packetType) {
		if (packetType instanceof Motion) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Enumeracja typów pakietów opisujących status połączenia z serwerem.
	 * 
	 * @author Maciej Gzik
	 * 
	 */
	public enum Connection implements IPacketType {
		/**
		 * Pakiet rejestracyjny.
		 */
		REGISTER_NODE,

		/**
		 * Pozytywna odpowiedź na pakiet rejestracyjny z serwera.
		 */
		REGISTER_NODE_ACCEPT,

		/**
		 * Negatywna ospowiedź na pakiet rejestracyjny.
		 */
		REGISTER_NODE_REJECT,

		/**
		 * Informacja wysyłana przez AndroMote w przypadku prawidłowego
		 * rozłączenia z serwerem
		 */
		DISCONNECT_BY_CLIENT,

		/**
		 * Pakiet rozsyłany w aplikacji w przypadku przerwanego połączenia z
		 * serwerem.
		 */
		CONNECTION_LOST,

		/**
		 * Pakiet zawierający informacje o statusie połenia wifi
		 */
		NETWORK_STATUS,

		/**
		 * Zmiana sterowania na kontrolowane przez pc.
		 */
		SET_PC_CONTROLL_MODE,

		/**
		 * Zmiana sterowania na powrót do miejsca rozpoczęcia ruchu silników.
		 */
		SET_BACK_TO_START_POINT_CONTROLL_MODE,

		/**
		 * Pakiet z nazwą aktualnie sterowanego modelu AndroMote - konkretnie
		 * typu obslugiwanego modelu. Pakiet powinien zostać przesłany w
		 * intencji startującej serwis kontrolera silników. Zawartość pakietu:
		 * 
		 * - String deviceName - nazwa modelu, na podstawie której tworzony jest
		 * nowy obiekt modelu i podejmowane decyzje o sterowaniu silnikami.
		 */
		MODEL_NAME,

		/**
		 * Wiadomość podtrzymująca połączenie z serwerem.
		 */
		STILL_ALIVE,

		/**
		 * Wiadomość podtrzymująca połączenie z serwerem. Pola:
		 * 
		 * - int oldState - stary status połączenia
		 * 
		 * - int newState - nowy status połączenia z serwerem
		 */
		NODE_CONNECTION_STATUS_CHANGED,

		/**
		 * Pakiet zapytanie o aktualną konfigurację węzła mobilnego: tryb ruchu,
		 * sterowania, uruchomionych wątków, listenerów, itd. Węzeł zwraca wiele
		 * pakietów z informacjami.
		 */
		NODE_CONNECTION_STATUS_REQUEST,

		/**
		 * Pakiet zaiwera aktualny status połączenia węzła.
		 */
		NODE_CONNECTION_STATUS_RESPONSE,
	}

	/**
	 * Enumeracja typów pakietów do komunikacji ze sterownikiem silników.
	 * 
	 * @author Maciej Gzik
	 * 
	 */
	public enum Engine implements IPacketType {
		/**
		 * Ustawia tryb ruchu na stepper.
		 */
		SET_STEPPER_MODE,

		/**
		 * Potwierdzenie zmiany trybu ruchu na krokowy.
		 */
		STEPPER_MODE_RESPONSE,

		/**
		 * Pakiet z informacją o trybie ruchu węzła.
		 */
		MOTION_MODE_RESPONSE,

		/**
		 * Ustawia tryb ruchu na ciągły.
		 */
		SET_CONTINUOUS_MODE,

		/**
		 * Potwierdzenie zmiany trybu ruchu na ciągły.
		 */
		CONTINUOUS_MODE_RESPONSE,


		/**
		 * Zmiana prędkości silników. 
		 * Jeśli pojazd ma dwa silniki, to zmiana prędkości silnika lewej gąsienicy
		 * 
		 * Zawartość pakietu:
		 * 
		 * speed - typ double - nowa prędkość samochodu (przedział 0-1);
		 */
		SET_SPEED,

		/**
		 * Zmiana prędkości prawego silnika - opcjonalna
		 * 
		 * Zawartość pakietu:
		 * 
		 * speed - typ double - nowa prędkość samochodu (przedział 0-1);
		 */
		SET_SPEED_B,
		
		/**
		 * pobranie statystyk dotyczących ruchu węzła
		 */
		GET_MOVE_STATS,

		/**
		 * Zmiana czasu trwania kroku.
		 */
		SET_STEP_DURATION,

		/**
		 * Pakiet zawierający informację o wykonanym kroku. Zawartość pakietu:
		 * 
		 * - Motion stepType - nazwa wykonanego kroku
		 * 
		 * - long stepDuration - czas trwania wykonanego ruchu
		 * 
		 * - double speed - prędkość wykonanego kroku
		 */
		STEP_TAKEN_PACKET,

		/**
		 * Pakiet informujący o odrzuceniu pakeitu zlecającego wykonanie ruchu z
		 * powodu aktualnie wykonywanej akcji, np. zawracania, skrętu, itp.
		 * Zawartość pakietu:
		 * 
		 * 
		 * - Packet packet - odrzucony pakiet
		 */
		MOTION_COMMAND_REFUSE_OTHER_ACTION_EXECUTED,

		/**
		 * Pakiet informujący o niewykonaniu ruchu z powodu nieprawidłowego
		 * działania sensorów telefonu (akcelerometr/gps/pole magnetyczne).
		 * Pakiet jest zwracany przy wykonywaniu operacji skręcania o określony
		 * kąt.
		 */
		MOTION_COMMAND_REFUSED_SENSOR_ERROR,

		/**
		 * Pakiet modyfikujący wartość zmiennej sendStepExecutedPacket z klasy
		 * EnginesControllerService, która decyduje o tym czy po wykonanym kroku
		 * (w trybie krokowym) zostaje wysałne potwierdzenie jego wykonania.
		 * 
		 * Zmiana jest realizowana poprzez ustawienie zmiennej z klasy
		 * {@link Packet}: int newState. Wartość 1 == true; 0 == false;
		 */
		SET_STEP_EXECUTION_CONFIRMATION,

		/**
		 * Pakiet przesyłany przez serwis silników w momencie błędu i
		 * konieczności zakończenia działania serwisu. Pakiet nie zawiera
		 * dodatkowych informacji.
		 */
		ENGINE_SERVICE_STOP_ERROR,
	}

	/**
	 * Enumeracja typów pakietów dotyczących ruchu modelu. UWAGA: Pakiety ruchu
	 * mogą zawierać zmianę prędkości, jeżeli różna od zera.
	 * 
	 * @author Maciej Gzik
	 * @author Sebastian Łuczak
	 * 
	 */
	public enum Motion implements IPacketType {

		/**
		 * Zlecenie wykonania ruchu do przodu ze zmienną prędkością obrotu na gąsienicach.
		 * Dotyczy pojazdów gąsienicowych
		 */
		MOVE_CATERPILLAR_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu do przodu ze zmienną prędkością obrotu na gąsienicach.
		 * Dotyczy pojazdów gąsienicowych
		 */
		MOVE_CATERPILLAR,
		/**
		 * Ruch do przodu inicjowany przez AndroMote.
		 */
		MOVE_FORWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu do przodu.
		 */
		MOVE_FORWARD,

		/**
		 * Ruch do tyłu inicjowany przez AndroMote.
		 */
		MOVE_BACKWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu do tyłu.
		 */
		MOVE_BACKWARD,
		
		/**
		 * Ruch do przodu przy dwóch różnych prędkościach (np. lewa i prawa gąsienica) inicjowany przez AndroMote.
		 */
		MOVE_FORWARD_DIFFER_SPEED_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu do przodu przy dwóch różnych prędkościach (np. lewa i prawa gąsienica).
		 */
		MOVE_FORWARD_DIFFER_SPEED,

		/**
		 * Ruch do tyłu przy dwóch różnych prędkościach (np. lewa i prawa gąsienica) inicjowany przez AndroMote.
		 */
		MOVE_BACKWARD_DIFFER_SPEED_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu do tyłu przy dwóch różnych prędkościach (np. lewa i prawa gąsienica).
		 */
		MOVE_BACKWARD_DIFFER_SPEED,

		/**
		 * Ruch w lewo inicjowany przez AndroMote.
		 */
		MOVE_LEFT_FORWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu w lewo do przodu.
		 */
		MOVE_LEFT_FORWARD,

		/**
		 * Ruch w prawo inicjowany przez AndroMote.
		 */
		MOVE_RIGHT_FORWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu w prawo do przodu.
		 */
		MOVE_RIGHT_FORWARD,

		/**
		 * Ruch w lewo do tyłu inicjowany przez AndroMote.
		 */
		MOVE_LEFT_BACKWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu w lewo do tyłu.
		 */
		MOVE_LEFT_BACKWARD,

		/**
		 * Wykonany skręt w lewo bez zatrzymania
		 */
		MOVE_LEFT_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu w lewo. W zależności od modelu moze być to
		 * skręcenie kół lub ciągły skręt w lewo.
		 */
		MOVE_LEFT,

		/**
		 * Wykonane zatrzymanie węzła
		 */
		STOP_RESPONSE,

		/**
		 * Zlecenie zatrzymania węzła.
		 */
		STOP,

		/**
		 * Wykonany skręt w prawo
		 */
		MOVE_RIGHT_RESPONSE,

		/**
		 * Polecenie skrętu w prawo. W zależności od modelu moze być to
		 * skręcenie kół lub ciągły skręt w prawo.
		 */
		MOVE_RIGHT,

		/**
		 * Wykonany skręt w prawo w tył
		 */
		MOVE_RIGHT_BACKWARD_RESPONSE,

		/**
		 * Zlecenie wykonania ruchu w prawo do tyłu.
		 */
		MOVE_RIGHT_BACKWARD,
		/**
		 * Zlecenie awaryjnego zatrzymania
		 */
		HALT
	}

	/**
	 * Enumeracja typów pakietów do komunikacji z serwisem BT.
	 * 
	 * @author Maciej Gzik
	 * 
	 */
	public enum Bluetooth implements IPacketType {
		/**
		 * Start BT discovery.
		 */
		BT_START_DISCOVERY,

		/**
		 * Stop BT discovery.
		 */
		BT_STOP_DISCOVERY,

		/**
		 * Ustawienie BT w trybie discoverable.
		 */
		BT_SET_DISCOVERABLE,

		/**
		 * Start wątku nasłuchującego połączeń od innych urządzeń BT.
		 */
		BT_START_SERVER_THREAD,

		/**
		 * Stop wątku nasłuchującego połączeń od innych urządzeń BT.
		 */
		BT_STOP_SERVER_THREAD,

		/**
		 * Zlecenie połączenia z urządzeniem. Pakiet zawiera:
		 * 
		 * - String deviceName - nazwa urządzenia, z którym następuje
		 * połączenie.
		 */
		BT_CONNECT_DEVICE,

		/**
		 * Zlecenie rozłączenia aktywnego połączenia BT. Możliwe tylko w stanie
		 * CONNECTED.
		 */
		BT_DISCONNECT_DEVICE,

		/**
		 * Pakiet informujący o odbiorze pakietu od połączonego urządzenia BT.
		 * Zawartość pakietu:
		 * 
		 * - Packet packet - odebrany pakiet
		 * 
		 * 
		 * - String deviceName - urządzenie przesyłające pakiet
		 */
		BT_MESSAGE_RECEIVED,

		/**
		 * Pakiet informujący o podłączeniu urządzenia BT. Zawartość pakietu:
		 * 
		 * - String deviceName - podłączone urządzenie.
		 */
		BT_DEVICE_CONNECTED,

		/**
		 * Pakiet zapytanie dla serwisu BT o sparowane urządzenia.
		 */
		BT_PAIRED_DEVICES_REQUES,

		/**
		 * Pakiet zapytanie dla serwisu BT o sparowane urządzenia.
		 */
		BT_PAIRED_DEVICES_REQUEST,
		/**
		 * Pakiet zawiera listę nazw sparowanych urzadzeń.
		 * 
		 * ArrayList<String> pairedDevices.
		 */
		BT_PAIRED_DEVICES_RESPONSE,

		/**
		 * Pakiet zapytanie o aktualną konfigurację węzła mobilnego: tryb ruchu,
		 * sterowania, uruchomionych wątków, listenerów, itd. Węzeł zwraca wiele
		 * pakietów z informacjami.
		 */
		BT_SERVICE_STATUS_REQUEST,

		/**
		 * Odpowiedź serwera z informacją o statusie połączenia BT.
		 */
		BT_SERVICE_STATUS_RESPONSE,

		/**
		 * Pakiet z informacją o zmianie stanu połączenia BT. Pola:
		 * 
		 * - int oldState; -stary status
		 * 
		 * -int newState; - nowy status
		 */
		BT_CONNECTION_STATUS_CHANGED,

		/**
		 * Pakiet z informacją o rozłączeniu z urządzeniem BT.
		 */
		BT_DEVICE_DSICONNECTED,

		/**
		 * Informacja o restarcie modułu BT - w przypadku błędów z połączeniem.
		 */
		BT_RESTARTING_MODULE,

		/**
		 * Pakiet zawierający pakiet, który ma zostać przesłany do podłączonego
		 * węzła - tylko w stanie BT_SERVER
		 * 
		 * - Packet packet - pakiet, który ma zostać przesłany do sąsiedniego
		 * węzła.
		 */
		BT_SEND_PACKET_TO_CONNECTED_DEVICE,

		/**
		 * Pakiet z informacją o nieudanej próbie połączenia z urządzeniem BT
		 */
		BT_CONNECTION_ERROR,

		/**
		 * Pakiet z informacją o zeskanownaym nowym urządzeniu. Zawiera:
		 * 
		 * 
		 */
		BT_DEVICE_DISCOVERED,

		/**
		 * Pakiet informujący o rozpoczęciu skanowania dostępnych urządzeń BT.
		 */
		BT_DISCOVERY_STARTED,

		/**
		 * Pakiet informujacy o zakończeniu skanowania.
		 */
		BT_DISCOVERY_STOPPED,

		/**
		 * Pakiet informujacy o znalezieniu urządzenia BT podczas skanowania.
		 * Zawartość pakietu:
		 * 
		 * - String deviceName - nazwa znalexionego urządzenia BT
		 */
		BT_DEVICE_FOUND,

		/**
		 * Pakiet parowania urządzenia BT. Zawartość pakietu:
		 * 
		 * - String deviceName - nazwa parowanego urządzenia BT
		 */
		BT_PAIR_DEVICE,

	}

	/**
	 * Enumeracja typów pakietow GPS.
	 * 
	 * @author Maciej Gzik
	 * 
	 */
	public enum GPS implements IPacketType {
		/**
		 * Zmiana lokalizacji GPS.
		 */
		GPS_STATUS_UPDATE,
		/**
		 * Zmiana lokalizacji GPS. REQUEST
		 */
		GPS_STATUS_UPDATE_REQUEST
	}

	/**
	 * Enumeracja typów pakietów obsługujących połączenie pomiędzy dwoma
	 * urządzeniami Android poprzez serwer.
	 * 
	 * @author Maciej Gzik
	 * 
	 */
	public enum ANDRO_ANDRO implements IPacketType {

		/**
		 * Pakiet zlecający połączenie ze zdalnym urządzeniem jako child. Pakiet
		 * jest wysyłany przez AndroMote po wyborze węzła z listy w aplikacji.
		 * Pakiet jest wysyłany do serwera, który następnie przesyła do
		 * wywoływanego węzła pakiet: CONNECT_AS_ROOT_SERVER_REQ. Po wysłaniu
		 * tego pakietu stan węzła zmienia się na
		 * CONTROLL_MODE_ANDRO_ANDRO_CONNECTING do momentu otrzymania odpowiedzi
		 * z serwera (od podłączanego węzła). Zawartość pakietu pakietu:
		 * 
		 * - String deviceName - nazwa urządzenia, który ma zosatć rootem
		 */
		CONNECT_AS_CHILD_REQUEST,

		/**
		 * Pakiet zlecający połączenie ze zdalnym urządzeniem jako root. Pakiet
		 * jest wysyłany przez AndroMote po wyborze węzła z listy w aplikacji
		 * mobilnej. Pakiet jest wysyłany do serwera, który następnie przesyła
		 * do wywoływanego węzła pakiet: CONNECT_AS_CHILD_SERVER_REQ. Po
		 * wysłaniu tego pakietu stan węzła zmienia się na
		 * CONTROLL_MODE_ANDRO_ANDRO_CONNECTING do momentu otrzymania odpowiedzi
		 * z serwera (od podłączanego węzła). Zawartość pakietu pakietu:
		 * 
		 * - String deviceName - nazwa urządzenia, który ma zostać child
		 */
		CONNECT_AS_ROOT_REQUEST,

		/**
		 * Pakiet wysyłany przez serwer jako zlecenie połączenia urzadzenia jako
		 * root. Po wysłaniu podanego zapytania węzeł musi odpowiedzieć:
		 * CONNECT_AS_ROOT_POSITIVE_RESPONSE (pozytywnie) lub
		 * CONNECT_AS_ROOT_NEGATIVE_RESPONSE (negatywnie).
		 */
		CONNECT_AS_ROOT_SERVER_REQUEST,

		/**
		 * Pakiet wysyłany przez serwer jako zlecenie połączenia urzadzenia jako
		 * child. Po wysłaniu podanego zapytania węzeł musi odpowiedzieć:
		 * CONNECT_AS_CHILD_POSITIVE_RESPONSE (pozytywnie) lub
		 * CONNECT_AS_CHILD_NEGATIVE_RESPONSE (negatywnie).
		 */
		CONNECT_AS_CHILD_SERVER_REQUEST,

		/**
		 * Pakiet przysłany z węzła do serwera z potwierdzeniem połączenia jako
		 * root. Akceptacja może być zlecona użytkownikowi, ale nie musi - wtedy
		 * sterowanie poprzez interfejs serwera. Do wywołującego child jest
		 * wtedy wysyłany pakiet: CONNECT_AS_CHILD_ACCEPTED.
		 */
		CONNECT_AS_ROOT_POSITIVE_RESPONSE,

		/**
		 * Pakiet przysłany z węzła do serwera z odrzuceniem połączenia jako
		 * root. Do wywołującego child jest wtedy wysyłany pakiet:
		 * CONNECT_AS_CHILD_REJECTED.
		 */
		CONNECT_AS_ROOT_NEGATIVE_RESPONSE,

		/**
		 * Pakiet przysłany z węzła do serwera z potwierdzeniem połączenia jako
		 * child. Akceptacja może być zlecona użytkownikowi, ale nie musi -
		 * wtedy sterowanie poprzez interfejs serwera. Do wywołującego child
		 * jest wtedy wysyłany pakiet: CONNECT_AS_ROOT_ACCEPTED.
		 */
		CONNECT_AS_CHILD_POSITIVE_RESPONSE,

		/**
		 * Pakiet przysłany z węzła do serwera z odrzuceniem połączenia jako
		 * child. Do wywołującego węzła root jest wtedy wysyłany pakiet:
		 * CONNECT_AS_ROOT_REJECTED.
		 */
		CONNECT_AS_CHILD_NEGATIVE_RESPONSE,

		/**
		 * Pakiet wysyłany przez serwer do węzła, który zestawia połączenie jako
		 * child lub serwer zestawia za niego połączenie jako child po
		 * pozytywnej odpowiedzi drugiego węzła. Po tym pakiecie następuje
		 * zmiana stanu węzła na CONTROLL_MODE_ANDRO_ANDRO_CHILD.
		 */
		CONNECT_AS_CHILD_ACCEPTED,

		/**
		 * Pakiet wysyłany przez serwer do węzła, który zestawia połączenie jako
		 * child lub serwer zestawia za niego połączenie jako child po
		 * negatywnej odpowiedzi drugiego węzła. Po tym pakiecie następuje
		 * zmiana stanu z CONTROLL_MODE_ANDRO_ANDRO_CONNECTING na
		 * CONTROLL_MODE_PC_CONTROLL.
		 */
		CONNECT_AS_CHILD_REJECTED,

		/**
		 * Pakiet wysyłany przez serwer do węzła, który zestawia połączenie jako
		 * root lub serwer zestawia za niego połączenie jako root po pozytywnej
		 * odpowiedzi drugiego węzła. Po tym pakiecie następuje zmiana stanu
		 * węzła na CONTROLL_MODE_ANDRO_ANDRO_ROOT.
		 */
		CONNECT_AS_ROOT_ACCEPTED,

		/**
		 * Pakiet wysyłany przez serwer do węzła, który zestawia połączenie jako
		 * root lub serwer zestawia za niego połączenie jako root po negatywnej
		 * odpowiedzi drugiego węzła. Po tym pakiecie następuje zmiana stanu z
		 * CONTROLL_MODE_ANDRO_ANDRO_CONNECTING na CONTROLL_MODE_PC_CONTROLL.
		 */
		CONNECT_AS_ROOT_REJECTED,

		/**
		 * Pakiet zawierający listę dostępnych do sparowania urządzeń. Zawartość
		 * pakietu:
		 * 
		 * - ArrayList<String> pairedDevices - lista możliwych do podłączenia
		 * węzłów.
		 */
		AVAILABLE_DEVICES_UPDATE,

		/**
		 * Request ponownego przesłania listy dostępnych węzłów ANDRO_ANDRO
		 */
		AVAILABLE_DEVICES_UPDATE_REQUEST,

		/**
		 * Pakiet wysylany po utracie połączenia z podłączonym węzłem.
		 */
		CONNECTION_LOST,

		/**
		 * Pakiet wysyłany przez węzeł do serwera w celu przerwania połączenia z
		 * podłączonym węzłem.
		 */
		DISCONNECT_REQUEST,

		/**
		 * Pakiet wysyłany przez serwer do węzła w celu przerwania połączenia
		 * ANDRO_ANDRO. akiet jest wysyłany po odebraniu pakietu
		 * DISCONNECT_REQUEST z podłączonego węzła lub po wyborze rozłączenia z
		 * intefejsu serwera.
		 */
		DISCONNECT_SERVER_REQUEST,
	}

	/**
	 * Interfejs typów pakietów pozwala na odbiór dowolnego typu enum i
	 * nastepnie interpretowanie jego zawartości przy wywołaniu polimorficznym.
	 *
	 * @author Maciej Gzik
	 *
	 */
	public interface IPacketType extends Serializable {

	}

}
