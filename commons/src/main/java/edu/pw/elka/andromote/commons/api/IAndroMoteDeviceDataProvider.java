package edu.pw.elka.andromote.commons.api;

/**
 * Interfejs obiektu odpowiedzialnego za przechowywanie danych pochodzących z
 * urzadzenia sterującego. Dane z obiektu są poberane za pomocą funkcji getData
 * z parametrem dataDescriptorPacket będącym kontenerem identyfikującym
 * oczekiwaną wartosć.
 * 
 * Przykłądem może być pobranie prędkości węzła mobilnego poprzez transmisję pakietu
 * 
 * @author Maciej Gzik
 * 
 */
public interface IAndroMoteDeviceDataProvider {

	public IPacket getData(IPacket dataDescriptorPacket);

}
