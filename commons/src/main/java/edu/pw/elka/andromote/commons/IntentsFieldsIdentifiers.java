package edu.pw.elka.andromote.commons;

import android.content.Intent;

/**
 * Klasa zawierająca nazwy pól przesyłanych w obiektach Intent wewnątrz
 * aplikacji.
 * 
 * Przykład pobrania pakietu z obiektu Intent: Packet inputPacket = (Packet)
 * intent.getSerializableExtra(IntentsFieldsIdentifiers.EXTRA_PACKET);
 * 
 * Dodawanie pakietu do obiektu Intent:
 * exampleIntent.putExtra(IntentsFieldsIdentifiers.EXTRA_PACKET, ((Serializable)
 * new Packet()));
 * 
 * @author Maciej Gzik
 * 
 */
public class IntentsFieldsIdentifiers {
	/**
	 * Identyfikator pola pakietu. Typ obiektu: andro_mote.commons.Packet.
	 * Obiekt Packet jest pobierany z Inent poprzez wywołania funkcji:
	 * getSerializable. Dokładny opis sposobu pobierania i przesyłania obiektów
	 * w wiadomościach Intent: {@link Intent}
	 */
	public static String EXTRA_PACKET = "packet";

}
