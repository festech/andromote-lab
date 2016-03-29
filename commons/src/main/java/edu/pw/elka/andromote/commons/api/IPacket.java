package edu.pw.elka.andromote.commons.api;


import edu.pw.elka.andromote.commons.PacketType;

/**
 * Interfejs kontenera wiadomości przesyłanych pomiędzy modułami aplikacji oraz
 * dwoma dowolnymi urządzeniami w projekcie AndroMote (serwer->telefon,
 * telefon->telefon). Każdy typ pakietu powinien zostać udokumentowany w klasie
 * {@link PacketType} dzięki czemu mozliwe będzie jego prawidłowe użycie i
 * pobranie przesyłanych danych. 
 * 
 * @author Maciej Gzik
 * 
 */
public interface IPacket {

	/**
	 * Pobranie typu wiadomości.
	 * @return Typ wiadomości.
	 */
	public PacketType.IPacketType getPacketType();

}
